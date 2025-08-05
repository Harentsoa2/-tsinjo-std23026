package school.hei.tsinjo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DonationRequestDto {
    @NotBlank(message = "L'email est requis")
    @Email(message = "Format d'email invalide")
    private String email;
    
    @NotBlank(message = "Le nom est requis")
    private String name;
    
    @NotBlank(message = "Le moyen de paiement est requis")
    private String paymentType;
    
    @NotBlank(message = "L'ID de paiement PSP est requis")
    private String pspPaymentId;
}