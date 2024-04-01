package org.example.testeDesabilitar.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Log4jLogger<T> implements org.example.testeDesabilitar.utils.Logger<T> {
    private final Logger logger;
    public Log4jLogger(Class<T> clazz){
        this.logger = LogManager.getLogger(clazz);
    }

    @Override
    public void logCreate(T entity){
        logger.info("Create: " + entity);
    }
    @Override
    public void logRead(T entity){
        logger.info("Read: " + entity);
    }
    @Override
    public void logReadAll(List<T> entities){
        logger.info("Read: " + entities);
    }
    @Override
    public void logUpdate(T entity){
        logger.info("Update:" + entity);
    }
    @Override
    public void logDelete(T entity){
        logger.info("Delete:" + entity);
    }
}
