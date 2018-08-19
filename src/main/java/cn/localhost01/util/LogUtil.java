package cn.localhost01.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author Ran.chunlin
 * @Date: Created in 1:48 2018-08-07
 */
public class LogUtil {

    private static Map<Class, Logger> loggerFactory = new HashMap<>(5);

    public static Logger getLogger(Class clazz) {
        if (loggerFactory.containsKey(clazz)) {
            return loggerFactory.get(clazz);
        } else {
            Logger logger = LoggerFactory.getLogger(clazz);
            loggerFactory.put(clazz, logger);
            return logger;
        }
    }

}
