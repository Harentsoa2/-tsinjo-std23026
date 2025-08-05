package school.hei.tsinjo.mapper;

import org.springframework.stereotype.Component;
import school.hei.tsinjo.dto.DonationRequestDto;
import school.hei.tsinjo.dto.TransactionDto;
import school.hei.tsinjo.entity.Donation;
import school.hei.tsinjo.entity.Donor;
import school.hei.tsinjo.entity.Payment;
import school.hei.tsinjo.model.enums.PaymentStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class DonationMapper {
    
    public Donation toEntity(DonationRequestDto dto, Donor donor, Payment payment) {
        return Donation.builder()
                .id(UUID.randomUUID().toString())
                .amount(null) // Le montant sera mis à jour après vérification Vola
                .dateTime(LocalDateTime.now())
                .status(PaymentStatus.VERIFYING)
                .pspPaymentId(dto.getPspPaymentId())
                .donor(donor)
                .payment(payment)
                .build();
    }
    
    public TransactionDto toTransactionDto(Donation donation) {
        return TransactionDto.builder()
                .dateTime(donation.getDateTime())
                .description(donation.getDescription())
                .amount(donation.getAmount())
                .type(donation.getType())
                .status(donation.getStatus().getDescription())
                .build();
    }
    
    public List<TransactionDto> toTransactionDtos(List<Donation> donations) {
        return donations.stream()
                .map(this::toTransactionDto)
                .collect(Collectors.toList());
    }
}