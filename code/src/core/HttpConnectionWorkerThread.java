package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread{
    //This class is a thread class which is created for each new connection the serverSocket accepts
    private Socket socket;
    private Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);
    public HttpConnectionWorkerThread(Socket socket) { //Constructor takes the client socket
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = socket.getInputStream(); //Read from client
            outputStream = socket.getOutputStream(); //Write to client

            //Static html page served to clients
            String html = "<html><head><title>Simple Java HTTP Server</title></head><body><h1>This page is served using my Simple Http server</h1></body></html>";
            String CRLF = "\n\r"; //13, 10
            String response = "HTTP/1.1 200 OK"
                    + "Content-Length: " + html.getBytes().length + CRLF + //Header
                    CRLF + html + CRLF + CRLF;
            outputStream.write(response.getBytes()); //Write the response to the socket outputStream (in bytes)

            //Close
            LOGGER.info(" * Connection processing finished");
        } catch (IOException e) {
            LOGGER.error("Error with communication", e);
        } finally {
            if (inputStream != null) { //Try and close all open streams and sockets
                try {
                    inputStream.close();
                } catch (IOException e) {}
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {}
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {}
            }
        }
    }

}
