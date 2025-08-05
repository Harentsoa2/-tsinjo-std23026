package school.hei.tsinjo.endpoint.rest.controller.health;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import school.hei.tsinjo.dto.DonationRequestDto;
import school.hei.tsinjo.dto.TransactionDto;
import school.hei.tsinjo.entity.Donation;
import school.hei.tsinjo.service.DonationService;
import school.hei.tsinjo.service.TransactionService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    
    private final DonationService donationService;
    private final TransactionService transactionService;
    
    @GetMapping("/")
    public String home(Model model) {
        // Récupérer toutes les transactions (dons + aides)
        List<TransactionDto> transactions = transactionService.getAllTransactionsSortedByDate();
        double totalBalance = transactionService.calculateTotalBalance();
        
        model.addAttribute("transactions", transactions);
        model.addAttribute("totalBalance", totalBalance);
        model.addAttribute("donationRequest", new DonationRequestDto());
        
        return "index";
    }

    // Dans votre fichier HomeController.java

    @PostMapping("/donations")
    public String createDonation(
            @Valid @ModelAttribute("donationRequest") DonationRequestDto donationRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            // --- Début du bloc de gestion des erreurs ---

            // Recharger les données nécessaires pour la page
            List<TransactionDto> transactions = transactionService.getAllTransactionsSortedByDate();
            double totalBalance = transactionService.calculateTotalBalance();

            model.addAttribute("transactions", transactions);
            model.addAttribute("totalBalance", totalBalance);
            model.addAttribute("donationRequest", donationRequest);


            return "index"; // On retourne à la vue pour afficher les erreurs
        }

        // Si on arrive ici, il n'y a pas d'erreur de validation
        try {
            Donation donation = donationService.createDonation(donationRequest);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Votre don a été enregistré et est en cours de vérification. Le montant sera confirmé après validation du paiement.");

            log.info("Nouvelle donation créée: {} pour {}", donation.getId(), donation.getAmount());

        } catch (Exception e) {
            log.error("Erreur lors de la création de la donation", e);
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Une erreur s'est produite lors de l'enregistrement de votre don. Veuillez réessayer.");
        }

        return "redirect:/";
    }
}