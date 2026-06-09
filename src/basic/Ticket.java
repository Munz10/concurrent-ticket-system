package basic;

import java.util.concurrent.atomic.AtomicInteger;

public class Ticket {
    private static final AtomicInteger globalTicketCounter = new AtomicInteger(0);
    private int ticketNumber = 0;
    private final double ticketPrice;

    public Ticket(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public void setTicketNumber() {
        ticketNumber = globalTicketCounter.incrementAndGet();
    }

    public String getTicketNumber() {
        int ticketNum = ticketNumber;
        String padded = String.format("%04d", ticketNum);
        return padded;
    }

    public String toString() {
        return "Ticket No : " + getTicketNumber() + " , " + "Ticket Price : " + ticketPrice;
    }
}
