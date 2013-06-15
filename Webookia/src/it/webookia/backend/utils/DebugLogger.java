package it.webookia.backend.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to perform logging during debug.
 * 
 */
public class DebugLogger {

    private Logger logger;

    /**
     * Creates a new logger for a component.
     * 
     * @param componentName
     *            - the component name.
     */
    public DebugLogger(String componentName) {
        this.logger = Logger.getLogger(componentName);
    }

    /**
     * Logs a message if in debug mode.
     * 
     * @param level
     *            - the message level
     * @param message
     *            - the message
     */
    public void log(Level level, String message) {
        if (Settings.DEBUG_MODE) {
            logger.log(level, message);
        }
    }

    /**
     * Logs a message with WARNING level.
     * 
     * @param message
     *            - the message.
     */
    public void log(String message) {
        log(Level.WARNING, message);
    }

}
