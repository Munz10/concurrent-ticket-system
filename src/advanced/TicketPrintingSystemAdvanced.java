package advanced;

import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;

public class TicketPrintingSystemAdvanced {
    public static void main(String[] args) {
        // Load configuration
        SystemConfiguration config = SystemConfiguration.getInstance();
        config.printConfiguration();

        // Thread groups for monitoring
        ThreadGroup passengers = new ThreadGroup("Passengers");

        // Initialize ticket machine with configuration
        TicketMachineAdvanced ticketMachine = new TicketMachineAdvanced(
                config.getInitialPaperLevel(),
                config.getInitialTonerLevel(),
                passengers);
        TicketSystemMonitor monitor = new TicketSystemMonitor();

        // Create ExecutorServices using configuration
        ExecutorService passengerExecutor = Executors.newFixedThreadPool(
                config.getPassengerPoolSize(), new NamedThreadFactory("Passenger"));
        ExecutorService technicianExecutor = Executors.newFixedThreadPool(
                config.getTechnicianPoolSize(), new NamedThreadFactory("Technician"));
        ScheduledExecutorService monitorExecutor = Executors.newScheduledThreadPool(1,
                new NamedThreadFactory("Monitor"));

        // Start monitoring with configurable interval
        monitorExecutor.scheduleAtFixedRate(
                monitor::printStatistics,
                config.getMonitoringInterval() / 1000,
                config.getMonitoringInterval() / 1000,
                TimeUnit.SECONDS);

        // Create advanced tickets with different types and priorities
        Ticket ticket1 = new Ticket(TicketType.ECONOMY, PassengerPriority.ECONOMY);
        Ticket ticket2 = new Ticket(TicketType.BUSINESS, PassengerPriority.BUSINESS);
        Ticket ticket3 = new Ticket(TicketType.FIRST_CLASS, PassengerPriority.VIP);
        Ticket ticket4 = new Ticket(TicketType.VIP_PREMIUM, PassengerPriority.VIP);

        // Create passengers with different priorities (1=VIP, 2=Business, 3=Economy)
        PassengerAdvanced passenger1 = new PassengerAdvanced("Passenger-Economy-1", ticketMachine, ticket1, 3,
                PassengerPriority.ECONOMY, monitor);
        PassengerAdvanced passenger2 = new PassengerAdvanced("Passenger-Business-2", ticketMachine, ticket2, 4,
                PassengerPriority.BUSINESS, monitor);
        PassengerAdvanced passenger3 = new PassengerAdvanced("Passenger-VIP-3", ticketMachine, ticket3, 2,
                PassengerPriority.VIP, monitor);
        PassengerAdvanced passenger4 = new PassengerAdvanced("Passenger-Economy-4", ticketMachine, ticket4, 1,
                PassengerPriority.ECONOMY, monitor);

        // Create technicians
        TonerTechnicianAdvanced tonerTechnician = new TonerTechnicianAdvanced("Toner-Tech", ticketMachine);
        PaperTechnicianAdvanced paperTechnician = new PaperTechnicianAdvanced("Paper-Tech", ticketMachine);

        // Submit tasks to executors
        List<Future<?>> passengerFutures = new ArrayList<>();
        passengerFutures.add(passengerExecutor.submit(passenger1));
        passengerFutures.add(passengerExecutor.submit(passenger2));
        passengerFutures.add(passengerExecutor.submit(passenger3));
        passengerFutures.add(passengerExecutor.submit(passenger4));

        technicianExecutor.submit(tonerTechnician);
        technicianExecutor.submit(paperTechnician);

        // Shutdown executors gracefully
        passengerExecutor.shutdown();
        technicianExecutor.shutdown();
        monitorExecutor.shutdown();

        try {
            // Wait for all passengers to complete (max 2 minutes)
            if (!passengerExecutor.awaitTermination(2, TimeUnit.MINUTES)) {
                System.err.println("[WARNING] Passenger threads timed out");
                passengerExecutor.shutdownNow();
            }

            // Wait for technicians to complete (max 30 seconds)
            if (!technicianExecutor.awaitTermination(30, TimeUnit.SECONDS)) {
                System.err.println("[WARNING] Technician threads timed out");
                technicianExecutor.shutdownNow();
            }

            // Print final statistics
            System.out.println("\n" + "=".repeat(60));
            monitor.printFinalReport();
            System.out.println(ticketMachine);
            System.out.println("=".repeat(60));

        } catch (InterruptedException e) {
            System.err.println("[ERROR] Main thread interrupted");
            passengerExecutor.shutdownNow();
            technicianExecutor.shutdownNow();
            monitorExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        } finally {
            // Ensure all executors are shutdown
            if (!monitorExecutor.isShutdown()) {
                monitorExecutor.shutdownNow();
            }
        }
    }
}

/**
 * Custom ThreadFactory for meaningful thread names
 */
class NamedThreadFactory implements ThreadFactory {
    private final String namePrefix;
    private int counter = 1;

    public NamedThreadFactory(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, namePrefix + "-" + counter++);
        thread.setUncaughtExceptionHandler(
                (t, e) -> System.err.println("[ERROR] Uncaught exception in " + t.getName() + ": " + e.getMessage()));
        return thread;
    }
}
