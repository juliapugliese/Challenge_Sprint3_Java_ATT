package org.example.repositories;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface _Logger<T> {
    final Logger LOGGER = LogManager.getLogger(_Logger.class);
    public default void logInfo(String message){
        LOGGER.info(message);
    }
    public default void logWarn(String message){
        LOGGER.warn(message);
    }
    public default void logError(String message){
        LOGGER.error(message);
    }
}
