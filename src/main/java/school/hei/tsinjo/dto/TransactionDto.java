package school.hei.tsinjo.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TransactionDto {
    private LocalDateTime dateTime;
    private String description;
    private Double amount;
    private String type;
    private String status;
    
    public String getFormattedAmount() {
        if (amount == null) {
            return "En attente";
        }
        return String.format("%.0f Ar", amount);
    }
}