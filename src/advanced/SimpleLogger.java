import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Simple logging framework with different log levels
 * Provides structured, formatted logging with timestamps
 */
public class SimpleLogger {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    private final String context;
    private static LogLevel currentLevel = LogLevel.INFO;
    
    public enum LogLevel {
        DEBUG(0, "[DEBUG]"),
        INFO(1, "[INFO]"),
        SUCCESS(2, "[SUCCESS]"),
        WARNING(3, "[WARNING]"),
        ERROR(4, "[ERROR]");
        
        private final int priority;
        private final String icon;
        
        LogLevel(int priority, String icon) {
            this.priority = priority;
            this.icon = icon;
        }
        
        public int getPriority() {
            return priority;
        }
        
        public String getIcon() {
            return icon;
        }
    }
    
    public SimpleLogger(String context) {
        this.context = context;
    }
    
    public static void setLogLevel(LogLevel level) {
        currentLevel = level;
    }
    
    private void log(LogLevel level, String message) {
        if (level.getPriority() >= currentLevel.getPriority()) {
            String timestamp = LocalTime.now().format(TIME_FORMATTER);
            String threadName = Thread.currentThread().getName();
            System.out.printf("[%s] %s [%s] [%s] <%s> %s%n", 
                timestamp, level.getIcon(), level.name(), threadName, context, message);
        }
    }
    
    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }
    
    public void info(String message) {
        log(LogLevel.INFO, message);
    }
    
    public void success(String message) {
        log(LogLevel.SUCCESS, message);
    }
    
    public void warning(String message) {
        log(LogLevel.WARNING, message);
    }
    
    public void error(String message) {
        log(LogLevel.ERROR, message);
    }
}

