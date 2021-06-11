package org.slf4j.impl;

import com.github.myibu.logging.GeneralLogger;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
/**
 * @author myibu
 */
public class GeneralLoggerFactory implements ILoggerFactory {
    ConcurrentMap<String, Logger> loggerMap;

    public GeneralLoggerFactory() {
        loggerMap = new ConcurrentHashMap<String, Logger>();
        GeneralLogger.init();
    }

    @Override
    public Logger getLogger(String name) {
        Logger generalLogger = loggerMap.get(name);
        if (generalLogger != null) {
            return generalLogger;
        } else {
            Logger newInstance = new GeneralLogger(name);
            Logger oldInstance = loggerMap.putIfAbsent(name, newInstance);
            return oldInstance == null ? newInstance : oldInstance;
        }
    }
}
