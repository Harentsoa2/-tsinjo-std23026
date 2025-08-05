package school.hei.tsinjo.dto.vola;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PspPaymentDto {
    @JsonProperty("pspType")
    private String pspType;
    
    private String id;
    private Integer amount;
    
    @JsonProperty("creationInstant")
    private LocalDateTime creationInstant;
}