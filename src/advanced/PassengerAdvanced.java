import java.util.Random;

/**
 * Advanced Passenger with priority support and monitoring
 */
public class PassengerAdvanced implements Runnable, Comparable<PassengerAdvanced> {
    private final TicketMachineAdvanced ticketMachine;
    private final String name;
    private final Ticket ticket;
    private final int noOfTickets;
    private final PassengerPriority priority;
    private final TicketSystemMonitor monitor;
    private final SimpleLogger logger;
    
    public PassengerAdvanced(String name, TicketMachineAdvanced ticketMachine, Ticket ticket, 
                            int noOfTickets, PassengerPriority priority, TicketSystemMonitor monitor) {
        this.name = name;
        this.ticketMachine = ticketMachine;
        this.ticket = ticket;
        this.noOfTickets = noOfTickets;
        this.priority = priority;
        this.monitor = monitor;
        this.logger = new SimpleLogger(name);
    }
    
    public String getName() {
        return name;
    }
    
    public PassengerPriority getPriority() {
        return priority;
    }
    
    @Override
    public void run() {
        logger.info("Started - Priority: " + priority.getDisplayName() + ", Tickets to print: " + noOfTickets);
        
        for (int i = 0; i < noOfTickets; i++) {
            long startTime = System.currentTimeMillis();
            
            // Print ticket
            ticketMachine.printTicket(ticket);
            
            long waitTime = System.currentTimeMillis() - startTime;
            
            // Record statistics
            monitor.recordTicketPrinted(priority, waitTime);
            
            // Log ticket printing
            logger.success(String.format("Ticket printed (%d/%d) - Wait time: %.2fs - %s", 
                i + 1, noOfTickets, waitTime / 1000.0, ticket));
            
            // Random delay between tickets (simulate passenger behavior)
            if (i < noOfTickets - 1) {
                try {
                    Random random = new Random();
                    int delaySeconds = random.nextInt(10) + 1;
                    logger.debug("Waiting " + delaySeconds + "s before next ticket...");
                    Thread.sleep(delaySeconds * 1000L);
                } catch (InterruptedException e) {
                    logger.error("Interrupted while waiting: " + e.getMessage());
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
        
        logger.info("Completed all " + noOfTickets + " tickets");
    }
    
    /**
     * Compare passengers by priority for priority queue
     * Lower priority number = Higher priority
     */
    @Override
    public int compareTo(PassengerAdvanced other) {
        return Integer.compare(this.priority.getLevel(), other.priority.getLevel());
    }
}
