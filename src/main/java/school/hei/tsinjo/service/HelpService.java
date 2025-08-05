package school.hei.tsinjo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.hei.tsinjo.entity.Help;
import school.hei.tsinjo.repository.HelpRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HelpService {
    
    private final HelpRepository helpRepository;
    
    public List<Help> getAllHelpsOrderByDate() {
        return helpRepository.findAllOrderByDateDesc();
    }
}