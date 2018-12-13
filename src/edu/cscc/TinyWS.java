package edu.cscc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import edu.cscc.RequestHandler;


/**
 * TinyWS a simplistic Tiny Web Server
 * @author Thomas Kelly - Tyler Warren
 */


public class TinyWS {

    private static int port;
    private static String defaultFolder;
    private static String defaultPage;

    /**
     The main class instantiates a TinyWS object
     */
    public static void main(String[] args) throws IOException{
        TinyWS tiny = new TinyWS();
        tiny.listen();
    }

    /**
     Gets the port number
     */

    public TinyWS() throws IOException{
       Config config = new Config();
       port = Integer.parseInt(config.getProperty(Config.PORT));
    }

    /**
     Gets the source of the HTTP request from the port found. Doesn't run until a port number is received.
     */

    public void listen() throws IOException {


        ServerSocket serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(0);

        while(true){
            log("TinyWS is listening on port " + port);
            Socket socket = serverSocket.accept();
            log("Source of HTTP request: " + socket.getInetAddress().getCanonicalHostName());
            RequestHandler requestHandler = new RequestHandler(socket);
            requestHandler.processRequest();
        }
    }

    /**
     * Log web server requests
     * @param s - message to log
     */
    public static void log(String s) {
        System.out.println(s);
    }

    /**
     * Handle fatal error - print info and die
     */
    public static void fatalError(String s) {
        handleError(s, null, true);
    }

    /**
     * Handle fatal error - print info and die
     */
    public static void fatalError(Exception e) {
        handleError(null, e, true);
    }

    /**
     * Handle fatal / non-fatal errors
     */
    public static void handleError(String s, Exception e, boolean isFatal) {
        if (s != null) {
            System.out.println(s);
        }
        if (e != null) {
            e.printStackTrace();
        }
        if (isFatal) System.exit(-1);
    }

    /**
     * Get port configuration value
     * @return port number
     */
    public static int getPort() {
        return port;
    }

    /**
     * Get default HTML folder
     * @return folder
     */
    public static String getDefaultFolder() {
        return defaultFolder;
    }

    /**
     * Get name of default web page
     * @return default page
     */
    public static String getDefaultPage() {
        return defaultPage;
    }
}
