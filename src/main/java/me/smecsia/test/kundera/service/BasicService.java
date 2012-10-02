package me.smecsia.test.kundera.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ilya Sadykov
 *         Date: 27.09.12
 *         Time: 18:51
 */
public abstract class BasicService {

    protected Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * Log and throw the exception next
     *
     * @param e exception
     */
    public void logAndThrow(Throwable e) {
        List<String> stackTrace = new ArrayList<String>();
        stackTrace.add(e.getClass().getName() + ": " + e.getMessage());
        for (StackTraceElement stE : e.getStackTrace()) {
            String lineNumStr = (stE.getLineNumber() >= 0) ? ":" + stE.getLineNumber() : "";
            stackTrace.add("\t at " + stE.getClassName() + ".<" + stE.getMethodName() + ">(" + stE.getFileName() +
                    lineNumStr + ")");
        }
        logger.error(StringUtils.join(stackTrace, "\r\n"));
        throw new RuntimeException(e);
    }

    public void logAndThrow(String message) {
        throw new RuntimeException(message);
    }
}
