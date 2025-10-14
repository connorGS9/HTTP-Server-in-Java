import config.Configuration;
import config.ConfigurationManager;
import core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;

public class MyHttpServer {
    private static Logger LOGGER = LoggerFactory.getLogger(MyHttpServer.class);

    public static void main(String[] args) throws IOException {
        LOGGER.info("Server starting...");
        ConfigurationManager.getInstance().loadConfigFile("code/src/http.json"); //Grab the config file of the request and read it
        Configuration conf = ConfigurationManager.getInstance().getConfiguration(); //Returns the configuration

        LOGGER.info("Using port: " + conf.getPort());
        LOGGER.info("Using webroot: " + conf.getWebroot());

        try {
            ServerListenerThread slt = new ServerListenerThread(conf.getPort(), conf.getWebroot()); //Create the listener thread
            slt.start();
        } catch (IOException e) {
            //Handle later
            throw new RuntimeException(e);
        }
    }
}
