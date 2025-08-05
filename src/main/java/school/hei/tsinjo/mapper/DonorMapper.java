package school.hei.tsinjo.mapper;

import org.springframework.stereotype.Component;
import school.hei.tsinjo.dto.DonationRequestDto;
import school.hei.tsinjo.entity.Donor;

import java.util.UUID;

@Component
public class DonorMapper {
    
    public Donor toEntity(DonationRequestDto dto) {
        return Donor.builder()
                .id(UUID.randomUUID().toString())
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }
}