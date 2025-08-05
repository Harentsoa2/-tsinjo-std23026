package school.hei.tsinjo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import school.hei.tsinjo.entity.Donation;
import school.hei.tsinjo.model.enums.PaymentStatus;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, String> {
    List<Donation> findByStatus(PaymentStatus status);
    
    @Query("SELECT d FROM Donation d WHERE d.status = 'SUCCEEDED' ORDER BY d.dateTime DESC")
    List<Donation> findSuccessfulDonationsOrderByDateDesc();
}