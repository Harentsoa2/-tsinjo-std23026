package school.hei.tsinjo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.hei.tsinjo.entity.Payment;
import school.hei.tsinjo.repository.PaymentRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {
    
    private final PaymentRepository paymentRepository;
    
    public Payment findOrCreatePayment(String paymentType) {
        return paymentRepository.findByPaymentType(paymentType)
                .orElseGet(() -> {
                    Payment newPayment = Payment.builder()
                            .id(UUID.randomUUID().toString())
                            .paymentType(paymentType)
                            .build();
                    return paymentRepository.save(newPayment);
                });
    }
}