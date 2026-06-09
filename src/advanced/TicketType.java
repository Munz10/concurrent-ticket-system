package advanced;

/**
 * Different ticket types with varying resource costs
 * Demonstrates advanced resource management
 */
public enum TicketType {
    ECONOMY("Economy Class", 5, 1, 50.0),
    BUSINESS("Business Class", 10, 2, 150.0),
    FIRST_CLASS("First Class", 15, 3, 300.0),
    VIP_PREMIUM("VIP Premium", 20, 4, 500.0);
    
    private final String displayName;
    private final int tonerCost;
    private final int paperCost;
    private final double basePrice;
    
    TicketType(String displayName, int tonerCost, int paperCost, double basePrice) {
        this.displayName = displayName;
        this.tonerCost = tonerCost;
        this.paperCost = paperCost;
        this.basePrice = basePrice;
    }
    
    public String getDisplayName() { return displayName; }
    public int getTonerCost() { return tonerCost; }
    public int getPaperCost() { return paperCost; }
    public double getBasePrice() { return basePrice; }
    
    /**
     * Calculate final ticket price with passenger priority multiplier
     */
    public double calculatePrice(PassengerPriority passengerPriority) {
        double multiplier;
        switch (passengerPriority) {
            case VIP:
                multiplier = 1.5;        // VIP gets 50% premium service
                break;
            case BUSINESS:
                multiplier = 1.2;   // Business gets 20% premium
                break;
            case ECONOMY:
                multiplier = 1.0;    // Standard pricing
                break;
            default:
                multiplier = 1.0;    // Default to standard pricing
                break;
        }
        return basePrice * multiplier;
    }
    
    /**
     * Check if machine has enough resources for this ticket type
     */
    public boolean canPrint(int availableToner, int availablePaper) {
        return availableToner >= tonerCost && availablePaper >= paperCost;
    }
    
    @Override
    public String toString() {
        return String.format("%s (Toner: %d, Paper: %d, Base: $%.2f)", 
            displayName, tonerCost, paperCost, basePrice);
    }
}
