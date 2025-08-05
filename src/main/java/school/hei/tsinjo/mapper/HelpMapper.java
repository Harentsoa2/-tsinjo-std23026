package school.hei.tsinjo.mapper;

import org.springframework.stereotype.Component;
import school.hei.tsinjo.dto.TransactionDto;
import school.hei.tsinjo.entity.Help;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HelpMapper {
    
    public TransactionDto toTransactionDto(Help help) {
        return TransactionDto.builder()
                .dateTime(help.getDateTime())
                .description(help.getDescription())
                .amount(help.getAmount())
                .type(help.getType())
                .status("Complété")
                .build();
    }
    
    public List<TransactionDto> toTransactionDtos(List<Help> helps) {
        return helps.stream()
                .map(this::toTransactionDto)
                .collect(Collectors.toList());
    }
}