package org.example.repositories;

import org.example.entities._BaseEntity;

import java.util.List;

public interface _BaseRepository<T extends _BaseEntity> {
    void create(T obj);
    List<T> read();
    void update(T obj);
    void delete(int id);
}
