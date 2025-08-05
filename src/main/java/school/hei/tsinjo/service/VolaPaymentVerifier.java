package school.hei.tsinjo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import school.hei.tsinjo.dto.vola.VolaPaymentDto;
import school.hei.tsinjo.entity.Donation;
import school.hei.tsinjo.model.enums.PaymentStatus;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VolaPaymentVerifier {
    
    private final DonationService donationService;
    private final RestTemplate restTemplate = new RestTemplate();
    
    @Value("${vola.api.url:https://42cwka3n4ifcp7ufheyrpmph240iuaxo.lambda-url.eu-west-3.on.aws}")
    private String volaApiUrl;
    
    @Value("${vola.api.key:13e46640-889f-4d59-b45b-62f4e9dd3830}")
    private String volaApiKey;
    
    @Scheduled(fixedDelay = 60000)
    @Transactional// Toutes les minutes
    public void verifyPendingPayments() {
        log.info("Début de la vérification des paiements en attente");
        
        List<Donation> pendingDonations = donationService.getPendingDonations();
        log.info("Nombre de donations à vérifier: {}", pendingDonations.size());
        
        for (Donation donation : pendingDonations) {
            try {
                verifyPayment(donation);
            } catch (Exception e) {
                log.error("Erreur lors de la vérification du paiement {}: {}", 
                    donation.getId(), e.getMessage());
            }
        }
        
        log.info("Fin de la vérification des paiements en attente");
    }
    
    private void verifyPayment(Donation donation) {
        try {
            String url = String.format("%s/payment?apiKey=%s&payerEmail=%s&pspType=ORANGE_MONEY&pspPaymentId=%s",
                volaApiUrl, volaApiKey, donation.getDonor().getEmail(), donation.getPspPaymentId());
            
            log.info("Vérification du paiement {} via Vola", donation.getId());
            
            VolaPaymentDto volaResponse = restTemplate.getForObject(url, VolaPaymentDto.class);
            
            if (volaResponse != null) {
                PaymentStatus newStatus = mapVolaStatusToPaymentStatus(volaResponse.getVerificationStatus());
                
                // Mettre à jour le montant depuis la réponse Vola
                if (volaResponse.getPspPayment() != null && volaResponse.getPspPayment().getAmount() != null) {
                    donation.setAmount(volaResponse.getPspPayment().getAmount().doubleValue());
                    donationService.updateDonation(donation);
                }
                
                if (newStatus != PaymentStatus.VERIFYING) {
                    donationService.updateDonationStatus(donation.getId(), newStatus);
                    log.info("Paiement {} mis à jour avec le statut: {}", donation.getId(), newStatus);
                }
            }
        } catch (Exception e) {
            log.error("Erreur lors de l'appel à l'API Vola pour la donation {}: {}", 
                donation.getId(), e.getMessage());
            
            // Après plusieurs tentatives, marquer comme échoué
            // (logique à implémenter selon les besoins)
        }
    }
    
    private PaymentStatus mapVolaStatusToPaymentStatus(String volaStatus) {
        if (volaStatus == null) {
            return PaymentStatus.VERIFYING;
        }
        
        return switch (volaStatus.toUpperCase()) {
            case "SUCCEEDED" -> PaymentStatus.SUCCEEDED;
            case "FAILED" -> PaymentStatus.FAILED;
            default -> PaymentStatus.VERIFYING;
        };
    }
}