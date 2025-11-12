import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Circuit Breaker Pattern - Prevents cascading failures
 * States: CLOSED (normal) -> OPEN (failing) -> HALF_OPEN (testing)
 */
public class CircuitBreaker {
    public enum State { CLOSED, OPEN, HALF_OPEN }
    
    private volatile State state = State.CLOSED;
    private final AtomicInteger failureCount = new AtomicInteger(0);
    private final AtomicInteger successCount = new AtomicInteger(0);
    private final AtomicLong lastFailureTime = new AtomicLong(0);
    
    // Configuration
    private final int failureThreshold;
    private final int successThreshold;
    private final long timeoutMillis;
    private final SimpleLogger logger;
    
    public CircuitBreaker(String name, int failureThreshold, int successThreshold, long timeoutMillis) {
        this.failureThreshold = failureThreshold;
        this.successThreshold = successThreshold;
        this.timeoutMillis = timeoutMillis;
        this.logger = new SimpleLogger("CircuitBreaker-" + name);
    }
    
    /**
     * Execute operation with circuit breaker protection
     */
    public boolean execute(Runnable operation) throws CircuitBreakerException {
        if (state == State.OPEN) {
            if (System.currentTimeMillis() - lastFailureTime.get() > timeoutMillis) {
                state = State.HALF_OPEN;
                logger.info("Circuit breaker moving to HALF_OPEN state - testing...");
                successCount.set(0);
            } else {
                throw new CircuitBreakerException("Circuit breaker OPEN - operation blocked");
            }
        }
        
        try {
            operation.run();
            onSuccess();
            return true;
        } catch (Exception e) {
            onFailure();
            throw e;
        }
    }
    
    private void onSuccess() {
        if (state == State.HALF_OPEN) {
            int successes = successCount.incrementAndGet();
            if (successes >= successThreshold) {
                state = State.CLOSED;
                failureCount.set(0);
                logger.info("Circuit breaker CLOSED - service recovered!");
            }
        } else if (state == State.CLOSED) {
            failureCount.set(0);
        }
    }
    
    private void onFailure() {
        failureCount.incrementAndGet();
        lastFailureTime.set(System.currentTimeMillis());
        
        if (state == State.CLOSED && failureCount.get() >= failureThreshold) {
            state = State.OPEN;
            logger.warning("Circuit breaker OPEN - too many failures (" + failureCount.get() + ")");
        } else if (state == State.HALF_OPEN) {
            state = State.OPEN;
            logger.warning("Circuit breaker back to OPEN - test failed");
        }
    }
    
    public State getState() { return state; }
    public int getFailureCount() { return failureCount.get(); }
    public int getSuccessCount() { return successCount.get(); }
    
    public static class CircuitBreakerException extends RuntimeException {
        public CircuitBreakerException(String message) {
            super(message);
        }
    }
}
