import config.Configuration;
import config.ConfigurationManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MyHttpServer {

    public static void main(String[] args) throws IOException {
        System.out.println("Server starting ...");

        ConfigurationManager.getInstance().loadConfigFile("code/src/http.json");
        Configuration conf = ConfigurationManager.getInstance().getConfiguration();

        System.out.println("Using port: " + conf.getPort());
        System.out.println("Using webroot: " + conf.getWebroot());

        try {
            ServerSocket serverSocket = new ServerSocket(conf.getPort());
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            //Read
            String html = "<html><head><title>Simple Java HTTP Server</title></head><body><h1>This page is served using my Simple Http server</h1></body></html>";
            String CRLF = "\n\r"; //13, 10
            String response = "HTTP/1.1 200 OK"
                                 + "Content-Length: " + html.getBytes().length + CRLF + //Header
                                    CRLF + html + CRLF + CRLF;
            outputStream.write(response.getBytes());
            //Write

            //Close
            inputStream.close();
            outputStream.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {

        }
    }

}
