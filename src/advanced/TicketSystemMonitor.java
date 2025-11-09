import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.time.Duration;
import java.time.Instant;

/**
 * Real-time monitoring system for ticket printing statistics
 * Tracks performance metrics and system health
 */
public class TicketSystemMonitor {
    private final TicketMachineAdvanced ticketMachine;
    
    // Performance metrics
    private final AtomicInteger totalTicketsPrinted = new AtomicInteger(0);
    private final AtomicLong totalWaitTimeMs = new AtomicLong(0);
    private final AtomicInteger passengersServed = new AtomicInteger(0);
    
    // Priority-based metrics
    private final AtomicInteger vipTickets = new AtomicInteger(0);
    private final AtomicInteger businessTickets = new AtomicInteger(0);
    private final AtomicInteger economyTickets = new AtomicInteger(0);
    
    // Resource tracking
    private final AtomicInteger tonerRefills = new AtomicInteger(0);
    private final AtomicInteger paperRefills = new AtomicInteger(0);
    private final AtomicInteger resourceWaitEvents = new AtomicInteger(0);
    
    // System start time
    private final Instant startTime;
    
    public TicketSystemMonitor(TicketMachineAdvanced ticketMachine) {
        this.ticketMachine = ticketMachine;
        this.startTime = Instant.now();
    }
    
    // Record ticket printing
    public void recordTicketPrinted(PassengerPriority priority, long waitTimeMs) {
        totalTicketsPrinted.incrementAndGet();
        totalWaitTimeMs.addAndGet(waitTimeMs);
        passengersServed.incrementAndGet();
        
        switch (priority) {
            case VIP:
                vipTickets.incrementAndGet();
                break;
            case BUSINESS:
                businessTickets.incrementAndGet();
                break;
            case ECONOMY:
                economyTickets.incrementAndGet();
                break;
        }
    }
    
    public void recordTonerRefill() {
        tonerRefills.incrementAndGet();
    }
    
    public void recordPaperRefill() {
        paperRefills.incrementAndGet();
    }
    
    public void recordResourceWait() {
        resourceWaitEvents.incrementAndGet();
    }
    
    // Real-time statistics display
    public void printStatistics() {
        long elapsedSeconds = Duration.between(startTime, Instant.now()).getSeconds();
        if (elapsedSeconds == 0) return; // Avoid division by zero
        
        double ticketsPerSecond = totalTicketsPrinted.get() / (double) elapsedSeconds;
        double avgWaitTime = passengersServed.get() > 0 
            ? totalWaitTimeMs.get() / (double) passengersServed.get() / 1000.0
            : 0;
        
        System.out.println("\n" + "+----------------------------------------------------------+");
        System.out.println("| *** REAL-TIME SYSTEM STATISTICS ***                      |");
        System.out.println("+----------------------------------------------------------+");
        System.out.printf("| Runtime: %ds | Throughput: %.2f tickets/sec              |%n", 
            elapsedSeconds, ticketsPerSecond);
        System.out.printf("| Total Tickets: %d | Avg Wait: %.2fs                      |%n", 
            totalTicketsPrinted.get(), avgWaitTime);
        System.out.println("+----------------------------------------------------------+");
        System.out.printf("| VIP: %d | Business: %d | Economy: %d                      |%n", 
            vipTickets.get(), businessTickets.get(), economyTickets.get());
        System.out.println("+----------------------------------------------------------+");
        System.out.printf("| Toner Refills: %d | Paper Refills: %d                    |%n", 
            tonerRefills.get(), paperRefills.get());
        System.out.printf("| Resource Wait Events: %d                                  |%n", 
            resourceWaitEvents.get());
        System.out.println("+----------------------------------------------------------+");
    }
    
    // Final report
    public void printFinalReport() {
        long totalSeconds = Duration.between(startTime, Instant.now()).getSeconds();
        
        System.out.println("\n*** FINAL SYSTEM REPORT ***");
        System.out.println("============================================================");
        System.out.printf("Total Runtime: %d seconds%n", totalSeconds);
        System.out.printf("Total Tickets Printed: %d%n", totalTicketsPrinted.get());
        System.out.printf("Passengers Served: %d%n", passengersServed.get());
        
        if (totalSeconds > 0) {
            System.out.printf("Average Throughput: %.2f tickets/second%n", 
                totalTicketsPrinted.get() / (double) totalSeconds);
        }
        
        if (passengersServed.get() > 0) {
            System.out.printf("Average Wait Time: %.2f seconds%n", 
                totalWaitTimeMs.get() / (double) passengersServed.get() / 1000.0);
        }
        
        System.out.println("\n*** Ticket Distribution:");
        System.out.printf("  VIP Tickets: %d (%.1f%%)%n", 
            vipTickets.get(), 
            100.0 * vipTickets.get() / Math.max(1, totalTicketsPrinted.get()));
        System.out.printf("  Business Tickets: %d (%.1f%%)%n", 
            businessTickets.get(),
            100.0 * businessTickets.get() / Math.max(1, totalTicketsPrinted.get()));
        System.out.printf("  Economy Tickets: %d (%.1f%%)%n", 
            economyTickets.get(),
            100.0 * economyTickets.get() / Math.max(1, totalTicketsPrinted.get()));
        
        System.out.println("\n*** Resource Management:");
        System.out.printf("  Toner Refills: %d%n", tonerRefills.get());
        System.out.printf("  Paper Refills: %d%n", paperRefills.get());
        System.out.printf("  Resource Wait Events: %d%n", resourceWaitEvents.get());
    }
}

