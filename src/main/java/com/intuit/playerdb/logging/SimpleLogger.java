package com.intuit.playerdb.logging;

import org.slf4j.Logger;

public class SimpleLogger {
    private Logger logger;


    public SimpleLogger(Logger logger)
    {
        this.logger = logger;
    }

    public void debug(String format, Object... args)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug(String.format(format, args));
        }
    }

    public void log(String format, Object... args)
    {
        logger.info(String.format(format, args));
    }
}
