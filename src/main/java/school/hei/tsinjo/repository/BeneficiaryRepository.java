package school.hei.tsinjo.repository;


import org.springframework.stereotype.Repository;
import school.hei.tsinjo.entity.Beneficiary;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, String> {
}