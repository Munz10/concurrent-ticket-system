package advanced;

import java.util.concurrent.atomic.AtomicInteger;

public class Ticket {
    private static final AtomicInteger globalTicketCounter = new AtomicInteger(0);
    private int ticketNumber = 0;
    private final double ticketPrice;
    private final TicketType ticketType;
    private final PassengerPriority passengerPriority;

    // Constructor for basic tickets (backward compatibility)
    public Ticket(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
        this.ticketType = TicketType.ECONOMY; // Default to economy
        this.passengerPriority = PassengerPriority.ECONOMY; // Default priority
    }

    // Constructor for advanced tickets with type and priority
    public Ticket(TicketType ticketType, PassengerPriority passengerPriority) {
        this.ticketType = ticketType;
        this.passengerPriority = passengerPriority;
        this.ticketPrice = ticketType.calculatePrice(passengerPriority);
    }

    public void setTicketNumber() {
        ticketNumber = globalTicketCounter.incrementAndGet();
    }

    public String getTicketNumber() {
        int ticketNum = ticketNumber;
        String padded = String.format("%04d", ticketNum);
        return padded;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public PassengerPriority getPassengerPriority() {
        return passengerPriority;
    }

    public String toString() {
        return "Ticket No: " + getTicketNumber() +
                " | Type: " + ticketType.getDisplayName() +
                " | Priority: " + passengerPriority +
                " | Price: $" + String.format("%.2f", ticketPrice);
    }
}
