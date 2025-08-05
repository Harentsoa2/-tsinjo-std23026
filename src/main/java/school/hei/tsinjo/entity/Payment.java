package school.hei.tsinjo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "payment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    private String id;
    
    @Column(name = "payment_type", nullable = false)
    private String paymentType;
    
    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
    private List<Donation> donations;
}