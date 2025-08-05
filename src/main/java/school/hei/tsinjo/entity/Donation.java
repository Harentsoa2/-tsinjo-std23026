package school.hei.tsinjo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import school.hei.tsinjo.model.Transaction;
import school.hei.tsinjo.model.enums.PaymentStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "donation")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Donation implements Transaction {
    @Id
    private String id;
    
    @Column(nullable = true) // Le montant sera récupéré depuis Vola
    private Double amount;
    
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;
    
    @Column(name = "psp_payment_id")
    private String pspPaymentId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donor_id")
    private Donor donor;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;
    
    @Override
    public String getDescription() {
        return String.format("Don de %s via %s", 
            donor != null ? donor.getName() : "Anonyme",
            payment != null ? payment.getPaymentType() : "Inconnu");
    }
    
    @Override
    public String getType() {
        return "DON";
    }
}