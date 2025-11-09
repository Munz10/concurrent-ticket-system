import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Advanced Ticket Machine with logging and better resource management
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
    
    public TicketMachineAdvanced(int paperLevel, int tonerLevel, ThreadGroup passengers) {
        this.tonerLevel = tonerLevel;
        this.paperLevel = paperLevel;
        this.passengers = passengers;
        this.logger = new SimpleLogger("TicketMachine");
        logger.info("Initialized - Paper: " + paperLevel + ", Toner: " + tonerLevel);
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
            logger.success("Paper refilled: " + oldLevel + " → " + paperLevel + " sheets");
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
            lock.lock();
            while (tonerLevel < MINIMUM_TONER_LEVEL || paperLevel < 1) {
                logger.warning("Insufficient resources - Toner: " + tonerLevel + ", Paper: " + paperLevel);
                resourcesLacking.await();
            }
            
            // Print the ticket
            tonerLevel -= 5;
            paperLevel--;
            ticketsPrinted++;
            ticket.setTicketNumber();
            
            logger.debug("Ticket printed - Resources remaining: Toner=" + tonerLevel + ", Paper=" + paperLevel);
            
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
