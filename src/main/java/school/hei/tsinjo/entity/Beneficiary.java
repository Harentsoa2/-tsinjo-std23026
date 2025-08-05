package school.hei.tsinjo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "beneficiary")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Beneficiary {
    @Id
    private String id;
    
    @Column(nullable = false)
    private String name;
    
    private String email;
    
    @OneToMany(mappedBy = "beneficiary", cascade = CascadeType.ALL)
    private List<Help> helps;
}