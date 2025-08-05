package school.hei.tsinjo.dto.vola;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VolaPaymentDto {
    private String id;
    
    @JsonProperty("pspPayment")
    private PspPaymentDto pspPayment;
    
    @JsonProperty("creationInstant")
    private LocalDateTime creationInstant;
    
    @JsonProperty("lastPspVerificationInstant")
    private LocalDateTime lastPspVerificationInstant;
    
    @JsonProperty("verificationAttemptNb")
    private Integer verificationAttemptNb;
    
    private UserDto payer;
    private ApplicationDto application;
    
    @JsonProperty("verificationStatus")
    private String verificationStatus;
}