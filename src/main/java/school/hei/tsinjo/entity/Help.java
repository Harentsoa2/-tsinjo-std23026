package school.hei.tsinjo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import school.hei.tsinjo.model.Transaction;

import java.time.LocalDateTime;

@Entity
@Table(name = "help")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Help implements Transaction {
    @Id
    private String id;
    
    @Column(nullable = false)
    private Double amount;
    
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;
    
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficiary_id")
    private Beneficiary beneficiary;
    
    @Override
    public String getDescription() {
        return String.format("Aide à %s - %s", 
            beneficiary != null ? beneficiary.getName() : "Inconnu",
            description != null ? description : "");
    }
    
    @Override
    public String getType() {
        return "AIDE";
    }
    
    @Override
    public Double getAmount() {
        // Retourne un montant négatif pour l'affichage
        return -Math.abs(amount);
    }
}