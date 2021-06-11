package com.github.myibu.logging;

import org.slf4j.helpers.Util;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Properties;
/**
 * @author myibu
 */
public class GeneralLoggerConfiguration {

    private static final String CONFIGURATION_FILE = "generallogger.properties";

    private static final String DATE_TIME_FORMAT_STR_DEFAULT = "yyyy-MM-dd HH:mm:ss,SSS";
    SimpleDateFormat dateFormatter = null;

    private static final String PATTERN_DEFAULT = "{dateTime} {logLevel} {threadId} --- [{threadName}] {logName} : {logMessage}";
    String pattern = null;

    private final Properties properties = new Properties();

    public void init() {
        loadProperties();

        String dateTimeFormatStr = getStringProperty("com.github.myibu.logging.GeneralLoggerConfiguration.dateTimeFormatStr", DATE_TIME_FORMAT_STR_DEFAULT);
        pattern = getStringProperty("com.github.myibu.logging.GeneralLoggerConfiguration.pattern", PATTERN_DEFAULT);

        if (dateTimeFormatStr != null) {
            try {
                dateFormatter = new SimpleDateFormat(dateTimeFormatStr);
            } catch (IllegalArgumentException e) {
                Util.report("Bad date format in " + CONFIGURATION_FILE + "; will output relative time", e);
            }
        }
    }

    private void loadProperties() {
        InputStream in;
        ClassLoader threadCL = Thread.currentThread().getContextClassLoader();
        if (threadCL != null) {
            in = threadCL.getResourceAsStream(CONFIGURATION_FILE);
        } else {
            in = ClassLoader.getSystemResourceAsStream(CONFIGURATION_FILE);
        }
        if (null != in) {
            try {
                properties.load(in);
            } catch (java.io.IOException e) {
                // ignored
            } finally {
                try {
                    in.close();
                } catch (java.io.IOException e) {
                    // ignored
                }
            }
        }
    }

    String getStringProperty(String name, String defaultValue) {
        String prop = getStringProperty(name);
        return (prop == null) ? defaultValue : prop;
    }

    boolean getBooleanProperty(String name, boolean defaultValue) {
        String prop = getStringProperty(name);
        return (prop == null) ? defaultValue : "true".equalsIgnoreCase(prop);
    }

    String getStringProperty(String name) {
        String prop = null;
        try {
            prop = System.getProperty(name);
        } catch (SecurityException e) {
            ; // Ignore
        }
        return (prop == null) ? properties.getProperty(name) : prop;
    }
}
