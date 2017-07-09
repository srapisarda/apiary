package io.apiary.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Salvatore Rapisarda
 *
 */
class ApplicationConf {
    private static Logger logger =  LoggerFactory.getLogger(ApplicationConf.class);
    private final String environment;
    private final String apiUrl;
    private final String username;
    private final String apikey;
    private static ApplicationConf ourInstance = new ApplicationConf();

    static ApplicationConf getInstance() {
        return ourInstance;
    }

    private ApplicationConf() {
        Properties prop = new Properties();
        try {
            prop.load(Main.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e){
            logger.error("An error occurred during the application properties loading");
        }

        environment = prop.getProperty("apiary.env");
        logger.info("environment: " +  environment);

        apiUrl= prop.getProperty( environment + ".apiary.url");
        logger.info("api url: " + apiUrl);

        username= prop.getProperty( environment + ".apiary.username");
        logger.debug("username: " + username);

        apikey= prop.getProperty( environment + ".apiary.key");
        logger.debug("key: " + apikey);
    }

    String getEnvironment() {
        return environment;
    }

    String getApiUrl() {
        return apiUrl;
    }

    String getUsername() {
        return username;
    }

    String getApikey() {
        return apikey;
    }

}
