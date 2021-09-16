package cache;

import com.mediamarktsaturn.MediaMarktSaturn.commons.LocalCacheObject;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocalCacheObjectTest {

    @Test
    public void testGet() {
        final LocalCacheObject<User> cache = new LocalCacheObject<>(1000 * 10);
        assertFalse(cache.get().isPresent());
        cache.set(new User("testuser"));
        assertTrue(cache.get().isPresent());
    }

    @Test
    public void resetGetCache() throws InterruptedException {
        final LocalCacheObject<User> cache = new LocalCacheObject<>(10 * 1);
        assertFalse(cache.get().isPresent());
        cache.set(new User("testuser"));
        Thread.sleep(30);
        assertFalse(cache.get().isPresent());
    }

    @Test
    public void testGetAndSet() {
        final LocalCacheObject<User> cache = new LocalCacheObject<>(1000 * 10);
        assertFalse(cache.get().isPresent());
        assertTrue(cache.getAndSet(() -> new User("testuser")).isPresent());
    }

    class User {
        private String username;

        public User(final String username) {
            this.username = username;
        }

        public String getUsername() {
            return this.username;
        }
    }
}
