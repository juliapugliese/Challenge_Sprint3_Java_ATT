package org.example.testeDesabilitar.utils;

import java.util.List;

public interface Logger<T> {
    void logCreate(T entity);
    void logRead(T entity);
    void logReadAll(List<T> entites);
    void logUpdate(T entity);
    void logDelete(T entity);
}
