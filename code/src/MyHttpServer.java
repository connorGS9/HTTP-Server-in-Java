import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
public class MyHttpServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.printf("Server socket started listening on port 8080.%n");
        while(true) { //Continue alive until process is killed :: Ctrl + c
            Socket clientSocket = serverSocket.accept();
            System.out.printf("Client Socket connected! ");

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String line;
            line = in.readLine(); //First Line, Method type (GET, POST) and the desired path /...  need to parse these
            System.out.println("Request: " + line);
            String[] tokens = line.split(" "); //Split the request at each space
            String method = tokens[0];
            String path = tokens[1];
            System.out.println("Method:" + method + " Path: " + path);
        }
        //End Process

    }

}
