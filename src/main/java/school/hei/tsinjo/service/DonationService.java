package school.hei.tsinjo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.hei.tsinjo.dto.DonationRequestDto;
import school.hei.tsinjo.entity.Donation;
import school.hei.tsinjo.entity.Donor;
import school.hei.tsinjo.entity.Payment;
import school.hei.tsinjo.mapper.DonationMapper;
import school.hei.tsinjo.model.enums.PaymentStatus;
import school.hei.tsinjo.repository.DonationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DonationService {
    
    private final DonationRepository donationRepository;
    private final DonorService donorService;
    private final PaymentService paymentService;
    private final DonationMapper donationMapper;
    
    public Donation createDonation(DonationRequestDto donationRequest) {
        log.info("Création d'une nouvelle donation pour: {}", donationRequest.getEmail());
        
        Donor donor = donorService.findOrCreateDonor(donationRequest);
        Payment payment = paymentService.findOrCreatePayment(donationRequest.getPaymentType());
        
        Donation donation = donationMapper.toEntity(donationRequest, donor, payment);
        
        return donationRepository.save(donation);
    }
    
    @Transactional(readOnly = true)
    public List<Donation> getSuccessfulDonations() {
        return donationRepository.findSuccessfulDonationsOrderByDateDesc();
    }
    
    @Transactional(readOnly = true)
    public List<Donation> getPendingDonations() {
        return donationRepository.findByStatus(PaymentStatus.VERIFYING);
    }
    
    public void updateDonationStatus(String donationId, PaymentStatus status) {
        donationRepository.findById(donationId)
                .ifPresent(donation -> {
                    donation.setStatus(status);
                    donationRepository.save(donation);
                    log.info("Statut de la donation {} mis à jour: {}", donationId, status);
                });
    }

    @Transactional(readOnly = true)
    public List<Donation> getAllDonationsOrderByDate() {
        // Utilise la méthode par défaut de JpaRepository et trie par date
        return donationRepository.findAll(Sort.by(Sort.Direction.DESC, "dateTime"));
    }
    
    public void updateDonation(Donation donation) {
        donationRepository.save(donation);
    }
}