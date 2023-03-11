package log4jAppender;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4JAppender {

    static Logger logger = LogManager.getLogger(Log4JAppender.class);
    public static void main(String[] args) {

        System.out.println("Learning Log4j");
        logger.trace("Logging with trace");
        logger.debug("Logging with debug");
        logger.info("Logging with info");
        logger.warn("Logging with warn");
        logger.error("Logging with error");
        logger.fatal("Logging with fatal");
    }
}
