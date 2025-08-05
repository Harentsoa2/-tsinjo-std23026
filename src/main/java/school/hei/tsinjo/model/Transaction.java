package school.hei.tsinjo.model;

import java.time.LocalDateTime;

public interface Transaction {
    LocalDateTime getDateTime();
    String getDescription();
    Double getAmount();
    String getType();
}