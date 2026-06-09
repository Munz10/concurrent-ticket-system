package advanced;

import java.util.concurrent.*;

/**
 * Timeout and Retry Logic for individual operations
 * Prevents operations from hanging indefinitely
 */
public class OperationTimeout {
    private final long timeoutMs;
    private final int maxRetries;
    private final SimpleLogger logger;

    public OperationTimeout(long timeoutMs, int maxRetries, String context) {
        this.timeoutMs = timeoutMs;
        this.maxRetries = maxRetries;
        this.logger = new SimpleLogger("Timeout-" + context);
    }

    /**
     * Execute operation with timeout and retry logic
     */
    public <T> T executeWithTimeout(Callable<T> operation, String operationName)
            throws TimeoutException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        try {
            for (int attempt = 1; attempt <= maxRetries; attempt++) {
                try {
                    logger.debug("Executing " + operationName + " (attempt " + attempt + "/" + maxRetries + ")");

                    Future<T> future = executor.submit(operation);
                    T result = future.get(timeoutMs, TimeUnit.MILLISECONDS);

                    if (attempt > 1) {
                        logger.info(operationName + " succeeded on retry attempt " + attempt);
                    }

                    return result;

                } catch (TimeoutException e) {
                    logger.warning(
                            operationName + " timed out on attempt " + attempt + " (timeout: " + timeoutMs + "ms)");
                    if (attempt == maxRetries) {
                        throw new TimeoutException(
                                "Operation '" + operationName + "' failed after " + maxRetries + " attempts");
                    }

                    // Brief delay before retry
                    Thread.sleep(100 * attempt); // Exponential backoff

                } catch (ExecutionException e) {
                    Throwable cause = e.getCause();
                    if (cause instanceof RuntimeException) {
                        throw (RuntimeException) cause;
                    }
                    throw new RuntimeException("Operation failed: " + operationName, cause);
                }
            }
            throw new TimeoutException("Operation '" + operationName + "' exhausted all retry attempts");
        } finally {
            executor.shutdownNow();
        }
    }

    /**
     * Execute void operation with timeout
     */
    public void executeWithTimeout(Runnable operation, String operationName)
            throws TimeoutException, InterruptedException {
        executeWithTimeout(() -> {
            operation.run();
            return null;
        }, operationName);
    }
}
