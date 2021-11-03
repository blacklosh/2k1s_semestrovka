package ru.itis.repository.base;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, K> {
    Optional<T> findById(K id);
    List<T> findAll();
    T save(T item);
    void update(K id, T item);
    void delete(K id);
}