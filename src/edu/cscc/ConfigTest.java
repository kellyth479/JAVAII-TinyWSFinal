package edu.cscc;
import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.*;

//Arrange Act Assert

/**
 * Config Test creates tests to validate that the config class is reading the config.xml file properly.
 */
public class ConfigTest {

    /**
     * test that the object be craeted properly
     */
    @Test public void test1(){
        Config config1 = new Config();
        try{
            config1.readProperties();
        }catch(IOException e){
            e.printStackTrace();
        }

        assertFalse("Object not created", config1 == null);
    }

    @Test public void test2(){
        Config config1 = new Config();
        try{
            config1.readProperties();
        }catch(IOException e){
            e.printStackTrace();
        }
        String port = config1.getProperty(config1.PORT);
        String defaultPage = config1.getProperty(config1.DEFAULTPAGE);
        String defaultFolder = config1.getProperty(config1.DEFAULTFOLDER);

        assertTrue("Port is incorrect", "80".equals(port));
        assertTrue("Default Page is incorrect","index.html".equals(defaultPage));
        assertTrue("Default Folder is incorrect","./html".equals(defaultFolder));

    }




}