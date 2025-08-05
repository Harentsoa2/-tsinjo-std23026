package school.hei.tsinjo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.hei.tsinjo.dto.DonationRequestDto;
import school.hei.tsinjo.entity.Donor;
import school.hei.tsinjo.mapper.DonorMapper;
import school.hei.tsinjo.repository.DonorRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class DonorService {
    
    private final DonorRepository donorRepository;
    private final DonorMapper donorMapper;
    
    public Donor findOrCreateDonor(DonationRequestDto donationRequest) {
        return donorRepository.findByEmail(donationRequest.getEmail())
                .orElseGet(() -> {
                    Donor newDonor = donorMapper.toEntity(donationRequest);
                    return donorRepository.save(newDonor);
                });
    }
}