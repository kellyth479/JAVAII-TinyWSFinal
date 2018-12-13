package edu.cscc;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

import edu.cscc.HTTPRequest;
import edu.cscc.ResponseHandler;

/**
 * RequestHandler class processes the request made, manipulates the data, and returns a response.
 * @author Thomas Kelly - Tyler Warren
 */
public class RequestHandler {

    private Socket connection;

    /**
     * Establishes a socket connection.
     */
    public RequestHandler(Socket connection) throws SocketException, IOException{
        this.connection = connection;
    }

    /**
     * Processes received HTTP requests
     */

    public void processRequest() {
        try {
            System.out.println("Got a request");
            String requestString = this.readRequest();
            System.out.println(requestString);
            HTTPRequest httpRequest = new HTTPRequest(requestString);
            ResponseHandler responseHandler = new ResponseHandler(httpRequest);
            responseHandler.sendResponse(connection);
        }catch(IOException ex){
            System.out.println("Error handling request " + ex);
        }
        finally {
            try {
                connection.close();
            }catch(IOException ex){
                System.out.println("Error when closing connection " + ex);

            }
        }

    }

    /**
     *Reads a HTTP request from the socket and returns it as a string
     */

    private String readRequest() throws SocketException, IOException {
        // Set socket timeout to 500 milliseconds
        connection.setSoTimeout(500);
        InputStream in = connection.getInputStream();

        BufferedReader reader = new BufferedReader((new InputStreamReader(in)));
        StringBuilder result = new StringBuilder();
        String line;


        while (!(line = reader.readLine()).equals("")) {

            result.append(line);
        }
        return result.toString();

    }
}




