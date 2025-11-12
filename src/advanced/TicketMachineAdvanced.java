import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Advanced Ticket Machine with circuit breaker, timeouts, and dynamic resource costs
 */
public class TicketMachineAdvanced implements ServiceTicketMachine {
    private int ticketsPrinted = 0;
    private int tonerLevel;
    private int paperLevel;
    private ThreadGroup passengers;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition refillToner = lock.newCondition();
    private final Condition refillPaper = lock.newCondition();
    private final Condition resourcesLacking = lock.newCondition();
    private final SimpleLogger logger;
    
    // Advanced features
    private final CircuitBreaker printingCircuitBreaker;
    private final OperationTimeout operationTimeout;
    private final SystemConfiguration config;
    
    public TicketMachineAdvanced(int paperLevel, int tonerLevel, ThreadGroup passengers) {
        this.tonerLevel = tonerLevel;
        this.paperLevel = paperLevel;
        this.passengers = passengers;
        this.logger = new SimpleLogger("TicketMachine");
        this.config = SystemConfiguration.getInstance();
        
        // Initialize circuit breaker
        this.printingCircuitBreaker = new CircuitBreaker("Printing", 
            config.getCircuitBreakerFailureThreshold(), 
            config.getCircuitBreakerSuccessThreshold(), 
            config.getCircuitBreakerTimeout());
            
        // Initialize timeout handler (5 second timeout, 3 retries)
        this.operationTimeout = new OperationTimeout(5000, 3, "TicketMachine");
        
        logger.info("Initialized - Paper: " + paperLevel + ", Toner: " + tonerLevel);
        logger.info("Circuit breaker enabled with " + config.getCircuitBreakerFailureThreshold() + " failure threshold");
    }
    
    @Override
    public void refillToner() {
        try {
            lock.lock();
            while (tonerLevel >= MINIMUM_TONER_LEVEL) {
                if (passengers.activeCount() > 0) {
                    logger.debug("Toner sufficient (" + tonerLevel + "), waiting...");
                    refillToner.await(3000, TimeUnit.MILLISECONDS);
                } else {
                    logger.info("No passengers in queue, toner refill unnecessary");
                    break;
                }
            }
            if (tonerLevel <= MINIMUM_TONER_LEVEL) {
                int oldLevel = tonerLevel;
                tonerLevel = FULL_TONER_LEVEL;
                logger.success("Toner refilled: " + oldLevel + " → " + tonerLevel);
                resourcesLacking.signalAll();
            }
        } catch (InterruptedException e) {
            logger.error("Toner refill interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
    
    @Override
    public void refillPaper() {
        try {
            lock.lock();
            while (paperLevel + SHEETS_PER_PACK > FULL_PAPER_TRAY) {
                logger.debug("Paper tray full (" + paperLevel + "/" + FULL_PAPER_TRAY + "), waiting...");
                refillPaper.await(3000, TimeUnit.MILLISECONDS);
            }
            int oldLevel = paperLevel;
            paperLevel += SHEETS_PER_PACK;
            logger.success("Paper refilled: " + oldLevel + " to " + paperLevel + " sheets");
            resourcesLacking.signalAll();
        } catch (InterruptedException e) {
            logger.error("Paper refill interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
    
    @Override
    public void printTicket(Ticket ticket) {
        try {
            // Use circuit breaker to prevent cascading failures
            printingCircuitBreaker.execute(() -> {
                try {
                    // Use timeout for the operation
                    operationTimeout.executeWithTimeout(() -> {
                        printTicketInternal(ticket);
                    }, "PrintTicket");
                } catch (TimeoutException e) {
                    logger.error("Ticket printing timed out: " + e.getMessage());
                    throw new RuntimeException("Print operation timed out", e);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Print operation interrupted", e);
                }
            });
        } catch (CircuitBreaker.CircuitBreakerException e) {
            logger.error("Circuit breaker blocked ticket printing: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Failed to print ticket: " + e.getMessage());
            throw new RuntimeException("Ticket printing failed", e);
        }
    }
    
    private void printTicketInternal(Ticket ticket) {
        try {
            lock.lock();
            
            // Get dynamic resource costs based on ticket type
            TicketType ticketType = ticket.getTicketType();
            int requiredToner = ticketType.getTonerCost();
            int requiredPaper = ticketType.getPaperCost();
            
            // Wait for sufficient resources
            while (!ticketType.canPrint(tonerLevel, paperLevel)) {
                logger.warning(String.format("Insufficient resources for %s - Need: Toner=%d, Paper=%d | Available: Toner=%d, Paper=%d", 
                    ticketType.getDisplayName(), requiredToner, requiredPaper, tonerLevel, paperLevel));
                resourcesLacking.await();
            }
            
            // Consume resources based on ticket type
            tonerLevel -= requiredToner;
            paperLevel -= requiredPaper;
            ticketsPrinted++;
            ticket.setTicketNumber();
            
            logger.success(String.format("Printed %s - Consumed: Toner=%d, Paper=%d | Remaining: Toner=%d, Paper=%d", 
                ticketType.getDisplayName(), requiredToner, requiredPaper, tonerLevel, paperLevel));
            
            // Signal technicians if resources are getting low
            if (tonerLevel <= MINIMUM_TONER_LEVEL) {
                refillToner.signalAll();
            }
            if (paperLevel <= FULL_PAPER_TRAY - SHEETS_PER_PACK) {
                refillPaper.signalAll();
            }
        } catch (InterruptedException e) {
            logger.error("Ticket printing interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
            throw new RuntimeException("Print operation interrupted", e);
        } finally {
            lock.unlock();
        }
    }
    
    public int getTonerLevel() {
        return tonerLevel;
    }
    
    public int getPaperLevel() {
        return paperLevel;
    }
    
    public int getTicketsPrinted() {
        return ticketsPrinted;
    }
    
    @Override
    public String toString() {
        return "\n*** TICKET MACHINE FINAL STATUS ***\n" +
                "========================================\n" +
                "  Tickets Printed: " + ticketsPrinted + "\n" +
                "  Toner Level: " + tonerLevel + " units\n" +
                "  Paper Level: " + paperLevel + " sheets\n" +
                "========================================";
    }
}
