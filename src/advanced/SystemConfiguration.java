package advanced;

import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

/**
 * Configuration Management - External settings instead of hardcoded values
 */
public class SystemConfiguration {
    private static SystemConfiguration instance;
    private final Properties properties;
    private final SimpleLogger logger;
    
    // Default values (fallback if config file missing)
    private static final int DEFAULT_PAPER_LEVEL = 100;
    private static final int DEFAULT_TONER_LEVEL = 60;
    private static final int DEFAULT_PASSENGER_POOL_SIZE = 4;
    private static final int DEFAULT_TECHNICIAN_POOL_SIZE = 2;
    private static final long DEFAULT_MONITORING_INTERVAL = 5000; // 5 seconds
    
    private SystemConfiguration() {
        this.properties = new Properties();
        this.logger = new SimpleLogger("Config");
        loadConfiguration();
    }
    
    public static synchronized SystemConfiguration getInstance() {
        if (instance == null) {
            instance = new SystemConfiguration();
        }
        return instance;
    }
    
    private void loadConfiguration() {
        try {
            // Try to load from classpath
            InputStream input = getClass().getResourceAsStream("/config.properties");
            if (input != null) {
                properties.load(input);
                logger.info("Configuration loaded from config.properties");
            } else {
                logger.info("No config.properties found, using defaults");
                setDefaults();
            }
        } catch (IOException e) {
            logger.warning("Failed to load configuration: " + e.getMessage());
            setDefaults();
        }
    }
    
    private void setDefaults() {
        properties.setProperty("ticket.machine.paper.initial", String.valueOf(DEFAULT_PAPER_LEVEL));
        properties.setProperty("ticket.machine.toner.initial", String.valueOf(DEFAULT_TONER_LEVEL));
        properties.setProperty("thread.pool.passengers.size", String.valueOf(DEFAULT_PASSENGER_POOL_SIZE));
        properties.setProperty("thread.pool.technicians.size", String.valueOf(DEFAULT_TECHNICIAN_POOL_SIZE));
        properties.setProperty("monitoring.interval.ms", String.valueOf(DEFAULT_MONITORING_INTERVAL));
        properties.setProperty("circuit.breaker.failure.threshold", "3");
        properties.setProperty("circuit.breaker.success.threshold", "2");
        properties.setProperty("circuit.breaker.timeout.ms", "30000");
    }
    
    public int getInitialPaperLevel() {
        return getIntProperty("ticket.machine.paper.initial", DEFAULT_PAPER_LEVEL);
    }
    
    public int getInitialTonerLevel() {
        return getIntProperty("ticket.machine.toner.initial", DEFAULT_TONER_LEVEL);
    }
    
    public int getPassengerPoolSize() {
        return getIntProperty("thread.pool.passengers.size", DEFAULT_PASSENGER_POOL_SIZE);
    }
    
    public int getTechnicianPoolSize() {
        return getIntProperty("thread.pool.technicians.size", DEFAULT_TECHNICIAN_POOL_SIZE);
    }
    
    public long getMonitoringInterval() {
        return getLongProperty("monitoring.interval.ms", DEFAULT_MONITORING_INTERVAL);
    }
    
    public int getCircuitBreakerFailureThreshold() {
        return getIntProperty("circuit.breaker.failure.threshold", 3);
    }
    
    public int getCircuitBreakerSuccessThreshold() {
        return getIntProperty("circuit.breaker.success.threshold", 2);
    }
    
    public long getCircuitBreakerTimeout() {
        return getLongProperty("circuit.breaker.timeout.ms", 30000L);
    }
    
    private int getIntProperty(String key, int defaultValue) {
        try {
            return Integer.parseInt(properties.getProperty(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            logger.warning("Invalid integer value for " + key + ", using default: " + defaultValue);
            return defaultValue;
        }
    }
    
    private long getLongProperty(String key, long defaultValue) {
        try {
            return Long.parseLong(properties.getProperty(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            logger.warning("Invalid long value for " + key + ", using default: " + defaultValue);
            return defaultValue;
        }
    }
    
    public void printConfiguration() {
        logger.info("=== SYSTEM CONFIGURATION ===");
        logger.info("Initial Paper Level: " + getInitialPaperLevel());
        logger.info("Initial Toner Level: " + getInitialTonerLevel());
        logger.info("Passenger Pool Size: " + getPassengerPoolSize());
        logger.info("Technician Pool Size: " + getTechnicianPoolSize());
        logger.info("Monitoring Interval: " + getMonitoringInterval() + "ms");
        logger.info("Circuit Breaker - Failure Threshold: " + getCircuitBreakerFailureThreshold());
        logger.info("Circuit Breaker - Success Threshold: " + getCircuitBreakerSuccessThreshold());
        logger.info("Circuit Breaker - Timeout: " + getCircuitBreakerTimeout() + "ms");
    }
}
