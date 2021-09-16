package com.mediamarktsaturn.MediaMarktSaturn.commons;

import java.io.Serializable;
import java.time.Instant;
import java.util.Optional;
import java.util.function.Supplier;

public class LocalCacheObject<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private T obj;
    private long storageTime;
    private final long ttl;

    public LocalCacheObject(final long ttl) {
        this.ttl = ttl;
    }


    public synchronized Optional<T> get() {
        if (this.obj == null) {
            return Optional.empty();
        }

        final long now = Instant.now().toEpochMilli();
        if (now - this.storageTime > this.ttl) {
            this.obj = null;
            this.storageTime = 0;
            return Optional.empty();
        }
        return Optional.of(this.obj);
    }

    public synchronized void set(final T obj) {
        this.obj = obj;
        this.storageTime = Instant.now().toEpochMilli();
    }

    public synchronized Optional<T> getAndSet(final Supplier<T> objSupplier) {
        final Optional<T> obj = this.get();
        if (obj.isPresent()) return obj;

        this.set(objSupplier.get());

        return this.get();
    }
}
