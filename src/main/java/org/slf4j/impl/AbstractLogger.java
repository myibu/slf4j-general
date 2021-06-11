package org.slf4j.impl;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.spi.LocationAwareLogger;
/**
 * @author myibu
 */
public abstract class AbstractLogger  extends MarkerIgnoringBase {

    public AbstractLogger(String name) {
        this.name = name;
    }

    @Override
    public boolean isTraceEnabled() {
        return isLevelEnabled(LocationAwareLogger.TRACE_INT);
    }

    @Override
    public void trace(String msg) {
        log(LocationAwareLogger.TRACE_INT, msg, null);
    }

    @Override
    public void trace(String format, Object arg) {
        formatAndLog(LocationAwareLogger.TRACE_INT, format, arg, null);
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        formatAndLog(LocationAwareLogger.TRACE_INT, format, arg1, arg2);
    }

    @Override
    public void trace(String format, Object... arguments) {
        formatAndLog(LocationAwareLogger.TRACE_INT, format, arguments);
    }

    @Override
    public void trace(String msg, Throwable t) {
        log(LocationAwareLogger.TRACE_INT, msg, t);
    }

    @Override
    public boolean isDebugEnabled() {
        return isLevelEnabled(LocationAwareLogger.DEBUG_INT);
    }

    @Override
    public void debug(String msg) {
        log(LocationAwareLogger.DEBUG_INT, msg, null);
    }

    @Override
    public void debug(String format, Object arg) {
        formatAndLog(LocationAwareLogger.DEBUG_INT, format, arg, null);
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        formatAndLog(LocationAwareLogger.DEBUG_INT, format, arg1, arg2);
    }

    @Override
    public void debug(String format, Object... arguments) {
        formatAndLog(LocationAwareLogger.DEBUG_INT, format, arguments);
    }

    @Override
    public void debug(String msg, Throwable t) {
        log(LocationAwareLogger.DEBUG_INT, msg, t);
    }

    @Override
    public boolean isInfoEnabled() {
        return isLevelEnabled(LocationAwareLogger.INFO_INT);
    }

    @Override
    public void info(String msg) {
        log(LocationAwareLogger.INFO_INT, msg, null);
    }

    @Override
    public void info(String format, Object arg) {
        formatAndLog(LocationAwareLogger.INFO_INT, format, arg, null);
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        formatAndLog(LocationAwareLogger.INFO_INT, format, arg1, arg2);
    }

    @Override
    public void info(String format, Object... arguments) {
        formatAndLog(LocationAwareLogger.INFO_INT, format, arguments);
    }

    @Override
    public void info(String msg, Throwable t) {
        log(LocationAwareLogger.INFO_INT, msg, t);
    }

    @Override
    public boolean isWarnEnabled() {
        return isLevelEnabled(LocationAwareLogger.WARN_INT);
    }

    @Override
    public void warn(String msg) {
        log(LocationAwareLogger.WARN_INT, msg, null);
    }

    @Override
    public void warn(String format, Object arg) {
        formatAndLog(LocationAwareLogger.WARN_INT, format, arg, null);
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        formatAndLog(LocationAwareLogger.WARN_INT, format, arg1, arg2);
    }

    @Override
    public void warn(String format, Object... arguments) {
        formatAndLog(LocationAwareLogger.WARN_INT, format, arguments);
    }

    @Override
    public void warn(String msg, Throwable t) {
        log(LocationAwareLogger.WARN_INT, msg, t);
    }

    @Override
    public boolean isErrorEnabled() {
        return isLevelEnabled(LocationAwareLogger.ERROR_INT);
    }

    @Override
    public void error(String msg) {
        log(LocationAwareLogger.ERROR_INT, msg, null);
    }

    @Override
    public void error(String format, Object arg) {
        formatAndLog(LocationAwareLogger.ERROR_INT, format, arg, null);
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        formatAndLog(LocationAwareLogger.ERROR_INT, format, arg1, arg2);
    }

    @Override
    public void error(String format, Object... arguments) {
        formatAndLog(LocationAwareLogger.ERROR_INT, format, arguments);
    }

    @Override
    public void error(String msg, Throwable t) {
        log(LocationAwareLogger.ERROR_INT, msg, t);
    }

    protected int currentLogLevel = LocationAwareLogger.INFO_INT;

    protected boolean isLevelEnabled(int logLevel) {
        return (logLevel >= currentLogLevel);
    }

    private void formatAndLog(int level, String format, Object arg1, Object arg2) {
        if (!isLevelEnabled(level)) {
            return;
        }
        FormattingTuple tp = MessageFormatter.format(format, arg1, arg2);
        log(level, tp.getMessage(), tp.getThrowable());
    }

    private void formatAndLog(int level, String format, Object... arguments) {
        if (!isLevelEnabled(level)) {
            return;
        }
        FormattingTuple tp = MessageFormatter.arrayFormat(format, arguments);
        log(level, tp.getMessage(), tp.getThrowable());
    }

    public abstract void log(int level, String message, Throwable t);
}

