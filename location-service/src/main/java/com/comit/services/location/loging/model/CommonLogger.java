package com.comit.services.location.loging.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommonLogger {
    private static final String CUSTOM_LOGGER = "CUSTOM_LOGGER";
    private static final Logger customLogger = LogManager.getLogger(CUSTOM_LOGGER);

    private static final String ERROR_LOGGER = "ERROR_LOGGER";
    private static final Logger errorLogger = LogManager.getLogger(ERROR_LOGGER);

    public static void info(CustomMessage message) {
        customLogger.info(message);
    }

    protected static void info(String message) {
        customLogger.info(message);
    }

    public static void error(Exception ex) {
        errorLogger.error(ex);
    }

    public static void error(String message) {
        errorLogger.error(message);
    }

    public static void error(String message, Exception ex) {
        errorLogger.error(message, ex);
    }
}
