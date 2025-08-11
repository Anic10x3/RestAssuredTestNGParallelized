package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;
import models.AuthResponse;
import utils.AuthUtils;

public class TokenManager {
    private static final Logger logger = LoggerFactory.getLogger(TokenManager.class);
    private static final AtomicReference<String> tokenRef = new AtomicReference<>(null);
    private static final AtomicReference<Instant> expiry = new AtomicReference<>(Instant.EPOCH);

    private TokenManager() {}

    public static String getToken() {
        String current = tokenRef.get();
        if (current == null || Instant.now().isAfter(expiry.get())) {
            synchronized (TokenManager.class) {
                // double-checked locking
                current = tokenRef.get();
                if (current == null || Instant.now().isAfter(expiry.get())) {
                    logger.info("Generating new auth token by thread: {}", Thread.currentThread().getName());
                    var resp = AuthUtils.requestAuthToken();
                    tokenRef.set(resp.getToken());
                    expiry.set(Instant.now().plusSeconds(resp.getExpiresIn() - 30)); // refresh 30s early
                }
            }
        }
        return tokenRef.get();
    }

    // For tests: ability to force expire token
    public static void invalidate() { expiry.set(Instant.EPOCH); tokenRef.set(null); }

}
