package io.apiary.client;

import static  org.junit.Assert.*;
import org.junit.Test;

/**
 * Created by rapissal on 09/07/2017.
 */
public class ConfigurationTest {

    @Test
    public void checkProperties(){
       ApplicationConf conf =  ApplicationConf.getInstance();

       assertNotNull( conf.getEnvironment());
       assertNotNull( conf.getApiUrl());
       assertNotNull( conf.getUsername());
       assertNotNull( conf.getApikey() );
    }

}
