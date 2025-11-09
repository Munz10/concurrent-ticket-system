import java.util.Random;

/**
 * Advanced Paper Technician with logging
 */
public class PaperTechnicianAdvanced implements Runnable {
    private final TicketMachineAdvanced ticketMachine;
    private final String name;
    private final int refillAttempts = 3;
    private final SimpleLogger logger;
    
    public PaperTechnicianAdvanced(String name, TicketMachineAdvanced ticketMachine) {
        this.ticketMachine = ticketMachine;
        this.name = name;
        this.logger = new SimpleLogger(name);
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public void run() {
        logger.info("Started paper maintenance duty");
        
        for (int i = 1; i <= refillAttempts; i++) {
            logger.info("Refill attempt " + i + "/" + refillAttempts);
            ticketMachine.refillPaper();
            
            try {
                Random random = new Random();
                int delaySeconds = random.nextInt(10) + 1;
                logger.debug("Next check in " + delaySeconds + "s...");
                Thread.sleep(delaySeconds * 1000L);
            } catch (InterruptedException e) {
                logger.error("Interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        logger.info("Completed maintenance duty");
    }
}
