package school.hei.tsinjo.model.enums;

public enum PaymentStatus {
    VERIFYING("En cours de vérification"),
    SUCCEEDED("Réussi"),
    FAILED("Échoué");
    
    private final String description;
    
    PaymentStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}