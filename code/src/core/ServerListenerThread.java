package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerThread extends Thread {
    //The server listener thread constantly awaits new connections to be made from clients and creates a thread for them
    private int port;
    private String webroot;
    private ServerSocket serverSocket;
    private Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);

    public ServerListenerThread(int port, String webroot) throws IOException { //Constructor takes the port # and webroot path
        this.port = port;
        this.webroot = webroot;
        this.serverSocket = new ServerSocket(this.port); //Initialize the serverSocket
    }

    @Override
    public void run() {
        try {
            while (serverSocket.isBound() && !serverSocket.isClosed()) { //While the connection is open and connected
                Socket socket = serverSocket.accept(); //socket is the new client socket
                LOGGER.info(" * Connection made" + socket.getInetAddress());
                HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket); //Create a workerThread for each new connection
                workerThread.start(); //Start the new thread
            }
        } catch (IOException e) {
            LOGGER.error("Problem with setting socket", e);
        } finally {
            if(serverSocket != null) { //No more requests
                try {
                    serverSocket.close(); //Close the socket
                } catch (IOException e) {}
            }
        }
    }
}
