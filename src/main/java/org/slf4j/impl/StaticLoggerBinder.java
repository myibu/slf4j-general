package org.slf4j.impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;
/**
 * @author myibu
 */
public class StaticLoggerBinder implements LoggerFactoryBinder {

    private static final StaticLoggerBinder SINGLETON = new StaticLoggerBinder();

    public static final StaticLoggerBinder getSingleton() {
        return SINGLETON;
    }

    private static final String loggerFactoryClassStr = GeneralLoggerFactory.class.getName();

    private final ILoggerFactory loggerFactory;

    private StaticLoggerBinder() {
        loggerFactory = new GeneralLoggerFactory();
    }

    @Override
    public ILoggerFactory getLoggerFactory() {
        return loggerFactory;
    }

    @Override
    public String getLoggerFactoryClassStr() {
        return loggerFactoryClassStr;
    }
}
