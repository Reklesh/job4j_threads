package ru.job4j.cash;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return Objects.isNull(memory.putIfAbsent(model.id(), model));
    }

    public boolean update(Base model) {
        return Objects.nonNull(memory.computeIfPresent(model.id(), (k, v) -> {
            if (v.version() != model.version()) {
                throw new OptimisticException("Versions are not equal");
            }
            return new Base(k, model.name(), model.version() + 1);
        }));
    }

    public void delete(int id) {
        memory.remove(id);
    }

    public Optional<Base> findById(int id) {
        return Stream.of(memory.get(id))
                .filter(Objects::nonNull)
                .findFirst();
    }
}
