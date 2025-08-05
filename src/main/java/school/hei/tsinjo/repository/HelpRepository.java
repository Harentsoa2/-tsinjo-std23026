package school.hei.tsinjo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import school.hei.tsinjo.entity.Help;

import java.util.List;

@Repository
public interface HelpRepository extends JpaRepository<Help, String> {
    @Query("SELECT h FROM Help h ORDER BY h.dateTime DESC")
    List<Help> findAllOrderByDateDesc();
}