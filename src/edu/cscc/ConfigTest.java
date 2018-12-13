package edu.cscc;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

//Arrange Act Assert

public class ConfigTest {

//    @Before public void setUp(){
//        //Test One
//        Config config1 = new Config();
//        try{
//            config1.readProperties();
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//    }

    /**
     * test that the object can read the properties
     */
    @Test public void test1(){
        Config config1 = new Config();
        try{
            config1.readProperties();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Test public void test2(){
        Config config1 = new Config();
        try{
            config1.readProperties();
        }catch(IOException e){
            e.printStackTrace();
        }
        String port = config1.getProperty(config1.PORT);
        System.out.println("PORT: " + port);
        String defaultpage = config1.getProperty(config1.DEFAULTPAGE);
        System.out.println("DEFAULTPAGE: " + defaultpage);
        String defaultfolder = config1.getProperty(config1.DEFAULTFOLDER);
        System.out.println("DEFAULTFOLDER: " + defaultfolder);
    }




}