package edu.cscc;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Determines the proper response based on the request received from the web browser
 * @author Thomas Kelly - Tyler Warren
 */
public class ResponseHandler {
    private static final String NOT_FOUND_RESPONSE =
            "HTTP/1.0 404 NotFound\n" +
                    "Server: TinyWS\n" +
                    "Content-type: text/plain\n\n" +
                    "404 - Requested file not found.";

    private static final String FORBIDDEN_RESPONSE =
            "HTTP/1.0 403 Forbidden\n" +
                    "Server: TinyWS\n" +
                    "Content-type: text/plain\n\n" +
                    "403 - Requested action is forbidden.  So there!";

    private static final String HTTP_OK_HEADER =
            "HTTP/1.0 200 OK\n" +
                    "Server: TinyWS\n" +
                    "Content-type: ";

    private HTTPRequest request;
    private int responseCode;


    public ResponseHandler(HTTPRequest request) {
        this.request = request;
        responseCode = 404;
    }

    /**
     * Uses the getFile method to determine which response to send to the browser
     * @param connection connection socket
     * @throws IOException
     */
    public void sendResponse(Socket connection) throws IOException {

        byte[] response = null;
        int sendbufsize = connection.getSendBufferSize();
        BufferedOutputStream out = new BufferedOutputStream(
                connection.getOutputStream(), sendbufsize);
       // TODO code here


        if(request.isValidRequest()) {

            response = getFile(request.getPath());

            if(response == null) {
                if(responseCode == 403 ) {
                    byte[] byteResp = FORBIDDEN_RESPONSE.getBytes();

                    out.write(byteResp);
                    out.flush();
                    out.close();
                }
                if(responseCode == 404){
                    byte[] byteResp = NOT_FOUND_RESPONSE.getBytes();
                    out.write(byteResp);
                    out.flush();
                    out.close();
                }
            } else {
                out.write(response);
                out.flush();
                out.close();
            }

        }

        byte[] byteResp = FORBIDDEN_RESPONSE.getBytes();
        out.write(byteResp);
        out.flush();
        out.close();

//        i.	If the HTTPRequest is a valid request call the private getFile() method you’ve implemented to get the byte array.
//        ii.	If the response is null (either it returned null or getFile() was never called because the request is invalid), test the return code. If it’s 403 set the response to FORBIDDEN_RESPONSE, otherwise set it to NOT_FOUND_RESPONSE. You’ll need to use the built-in String getBytes() method to convert a String to a byte array.
//        iii.	Output the response, flush, and close the connection.


    }

    // Find requested file, assume Document Root is in html folder in project directory

    /**
     * Reads a file using the String path and returns a byte array. This method calls readFile method, sets the appropriate response code
     * @param path location on the file to be retrieved
     * @return byte array
     */
    private byte[] getFile(String path) {

        Config config = new Config();

        responseCode = 200;

        byte[] byteArray = null;

        if(path == null) {
            responseCode = 404;
        }

        if(path.contains("..")) {
            responseCode = 403;
        }

        if(path.startsWith(File.separator)) {
            path = config.getProperty(Config.DEFAULTFOLDER) + path;
        } else {
            path = config.getProperty(Config.DEFAULTFOLDER) + File.separator + path;
        }

        File f = new File(path);
        if(f.isDirectory()) {

            if(path.endsWith(File.separator)) {
                path = path + config.getProperty(Config.DEFAULTPAGE);
            } else {
                path = path + File.separator + config.getProperty(Config.DEFAULTPAGE);
            }

            f = new File(path);

        }

        if(!f.canRead() || (f.length() == 0)) {

            responseCode = 404;
            return null;
        }

        byteArray = readFile(f);

        if(byteArray == null) {
            responseCode = 404;
        }

        if(responseCode != 200) {
            return null;
        }

        String httpHeaders = HTTP_OK_HEADER +  getMimeType(path)   + "\n\n";

        byte[] headerBytes = httpHeaders.getBytes(StandardCharsets.UTF_8);

        byte[] combinedBytes = new byte[headerBytes.length +  byteArray.length];

        System.arraycopy(headerBytes,0,combinedBytes,0         ,headerBytes.length);
        System.arraycopy(byteArray,0,combinedBytes, headerBytes.length, byteArray.length);

        return(combinedBytes);
    }

    /**
     *Reads the file passed in and returns it in a byte array
     * @param f File to be read into a byte array
     * @return byte array of the parsed file
     */
    // Read file, return byte array (null if error)
    private byte[] readFile(File f)  {

        byte[] array = null;

        try {
            array = Files.readAllBytes(f.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

        return(array);
    }

    /**
     * Parses the MimeType from the path
     * @param path location of the file to be parsed for its MIME type
     * @return String representing the MIME Type
     */
    // Return mimetype based on file suffix (or null if error)
    private String getMimeType(String path) {
        String mimeType = null;

        if(path.endsWith(".html")) {
            mimeType = "text/html";
        }
        if(path.endsWith(".txt")) {
            mimeType =  "text/html";
        }
        if(path.endsWith(".gif")) {
            mimeType = "image/gif";
        }
        if(path.endsWith(".jpg")) {
            mimeType =  "image/jpeg";
        }
        if(path.endsWith(".ico")) {
            mimeType =  "image/x-icon";
        }
        return mimeType;
    }
}
