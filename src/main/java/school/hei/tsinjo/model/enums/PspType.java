package school.hei.tsinjo.model.enums;

public enum PspType {
    ORANGE_MONEY("Orange Money");
    
    private final String displayName;
    
    PspType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}