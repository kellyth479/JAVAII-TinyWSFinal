package edu.cscc;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Thomas Kelly - Tyler Warren
 * HTTPRequestTest validates the the HTTPRequest class is properly validating the syntax of the request
 */
public class HTTPRequestTest {

    @Test public void HTTPRequestTest1(){
        HTTPRequest httpRequestTest = new HTTPRequest("GET /path/filename <additional request info");
        assertTrue("Could not process Path: + GET /path/filename <additional request info", httpRequestTest.isValidRequest());
    }

    @Test public void HTTPRequestTest2(){
        HTTPRequest httpRequestTest = new HTTPRequest("GET /index.html");
        assertTrue("Could not process Path: + GET /index.html", httpRequestTest.isValidRequest());
    }

    @Test public void HTTPRequestTest3(){
        HTTPRequest httpRequestTest = new HTTPRequest("GET /images/somepix.jpg");
        assertTrue("Could not process Path: + GET /images/somepix.jpg", httpRequestTest.isValidRequest());
    }

    @Test public void HTTPRequestTest4(){
        HTTPRequest httpRequestTest = new HTTPRequest("GET /");
        assertTrue("GET /", httpRequestTest.isValidRequest());
    }

    @Test public void HTTPRequestTest5(){
        HTTPRequest httpRequestTest = new HTTPRequest("GET /index.html?name1=value1&name2=value2");
        assertTrue("GET /index.html?name1=value1&name2=value2", httpRequestTest.isValidRequest());
    }

    @Test public void HTTPRequestTest6(){
        HTTPRequest httpRequestTest = new HTTPRequest("POST /index.html?name1=value1&name2=value2");
        assertFalse("POST /index.html?name1=value1&name2=value2", httpRequestTest.isValidRequest());
    }

}