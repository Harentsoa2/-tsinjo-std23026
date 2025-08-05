package school.hei.tsinjo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.hei.tsinjo.entity.Donor;

import java.util.Optional;

@Repository
public interface DonorRepository extends JpaRepository<Donor, String> {
    Optional<Donor> findByEmail(String email);
}