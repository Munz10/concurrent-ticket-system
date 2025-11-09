/**
 * Passenger priority levels for queue management
 * Lower numeric value = Higher priority
 */
public enum PassengerPriority {
    VIP(1, "[VIP]"),
    BUSINESS(2, "[BUSINESS]"),
    ECONOMY(3, "[ECONOMY]");
    
    private final int level;
    private final String displayName;
    
    PassengerPriority(int level, String displayName) {
        this.level = level;
        this.displayName = displayName;
    }
    
    public int getLevel() {
        return level;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}

