package com.github.myibu.logging;

import org.slf4j.impl.AbstractLogger;
import org.slf4j.spi.LocationAwareLogger;

/**
 * @author myibu
 */
public class GeneralLogger extends AbstractLogger {
    public GeneralLogger(String name) {
        super(name);
    }

    static GeneralLoggerConfiguration CONFIG_PARAMS = null;

    public static void init() {
        CONFIG_PARAMS = new GeneralLoggerConfiguration();
        CONFIG_PARAMS.init();
    }

    final public static String PARAM_DATE_TIME = "{dateTime}";
    final public static String PARAM_LOG_LEVEL = "{logLevel}";
    final public static String PARAM_THREAD_ID = "{threadId}";
    final public static String PARAM_THREAD_NAME = "{threadName}";
    final public static String PARAM_LOG_NAME = "{logName}";
    final public static String PARAM_LOG_MESSAGE = "{logMessage}";

    final public static String REGEX_DATE_TIME = "\\{dateTime}";
    final public static String REGEX_LOG_LEVEL = "\\{logLevel}";
    final public static String REGEX_THREAD_ID = "\\{threadId}";
    final public static String REGEX_THREAD_NAME = "\\{threadName}";
    final public static String REGEX_LOG_NAME = "\\{logName}";
    final public static String REGEX_LOG_MESSAGE = "\\{logMessage}";

    @Override
    public void log(int level, String message, Throwable t) {
        if (!isLevelEnabled(level)) {
            return;
        }
        String logInfo = CONFIG_PARAMS.pattern;

        if (CONFIG_PARAMS.pattern.contains(PARAM_DATE_TIME)) {
            logInfo = logInfo.replaceAll(REGEX_DATE_TIME, CONFIG_PARAMS.dateFormatter.format(System.currentTimeMillis()));
        }
        if (CONFIG_PARAMS.pattern.contains(PARAM_LOG_LEVEL)) {
            logInfo = logInfo.replaceAll(REGEX_LOG_LEVEL, substring(level2String(level), 6, true));
        }
        if (CONFIG_PARAMS.pattern.contains(PARAM_THREAD_ID)) {
            logInfo = logInfo.replaceAll(REGEX_THREAD_ID, substring(Thread.currentThread().getId()+"", 5, true));
        }
        if (CONFIG_PARAMS.pattern.contains(PARAM_THREAD_NAME)) {
            logInfo = logInfo.replaceAll(REGEX_THREAD_NAME, substring(Thread.currentThread().getName(), 10, true));
        }

        if (CONFIG_PARAMS.pattern.contains(PARAM_LOG_NAME)) {
            String loggerName = name;
            if (loggerName.contains(".")) {
                String[] names = loggerName.split("[.]");
                if (names.length > 1) {
                    StringBuilder tmp = new StringBuilder();
                    for (int i = 0; i < names.length; i++) {
                        if (names[i].length() > 0) {
                            tmp.append((i == names.length - 1) ? names[i] : names[i].charAt(0) + ".");
                        }
                    }
                    loggerName = tmp.toString();
                }
            }
            logInfo = logInfo.replaceAll(REGEX_LOG_NAME, substring(loggerName, 40, false));
        }
        if (CONFIG_PARAMS.pattern.contains(PARAM_LOG_MESSAGE)) {
            logInfo = logInfo.replaceAll(REGEX_LOG_MESSAGE, message);
        }
        if (!logInfo.endsWith(System.lineSeparator())) {
            logInfo = logInfo + System.lineSeparator();
        }
        System.out.print(logInfo);
        if (t != null) {
            t.printStackTrace(System.out);
        }
    }

    String level2String(int level) {
        switch (level) {
            case LocationAwareLogger.TRACE_INT: return "TRACE";
            case LocationAwareLogger.DEBUG_INT: return "DEBUG";
            case LocationAwareLogger.WARN_INT: return "WARN";
            case LocationAwareLogger.ERROR_INT: return "ERROR";
            case LocationAwareLogger.INFO_INT:
            default: return "INFO";
        }
    }

    String substring(String str, int maxLength, boolean alignRight) {
        String format = "%"+ (alignRight ? "" : "-") + maxLength + "s";
        if (str.length() > maxLength) {
            str = str.substring(str.length()-maxLength);
        }
        return String.format(format, str);
    }
}
