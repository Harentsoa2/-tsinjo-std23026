package school.hei.tsinjo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.hei.tsinjo.dto.TransactionDto;
import school.hei.tsinjo.entity.Donation;
import school.hei.tsinjo.entity.Help;
import school.hei.tsinjo.mapper.DonationMapper;
import school.hei.tsinjo.mapper.HelpMapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionService {
    
    private final DonationService donationService;
    private final HelpService helpService;
    private final DonationMapper donationMapper;
    private final HelpMapper helpMapper;

    public List<TransactionDto> getAllTransactionsSortedByDate() {
        List<TransactionDto> allTransactions = new ArrayList<>();

        // ======================= CORRECTION APPLIQUÉE =======================
        // On appelle la nouvelle méthode qui récupère TOUTES les donations
        List<Donation> allDonations = donationService.getAllDonationsOrderByDate();
        allTransactions.addAll(donationMapper.toTransactionDtos(allDonations));
        // ===================================================================

        // Récupérer toutes les aides
        List<Help> helps = helpService.getAllHelpsOrderByDate();
        allTransactions.addAll(helpMapper.toTransactionDtos(helps));

        // Trier la liste DTO combinée par date décroissante
        // Ce tri est important car les dons et les aides sont récupérés séparément
        allTransactions.sort(Comparator.comparing(TransactionDto::getDateTime).reversed());

        return allTransactions;
    }


    public double calculateTotalBalance() {
        List<TransactionDto> transactions = getAllTransactionsSortedByDate();
        return transactions.stream()
                .filter(t -> t.getAmount() != null) // Ignorer les transactions sans montant confirmé
                .mapToDouble(TransactionDto::getAmount)
                .sum();
    }
}