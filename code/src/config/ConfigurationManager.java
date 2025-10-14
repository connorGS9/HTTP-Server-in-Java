package config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import util.Json;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class ConfigurationManager {

    private static ConfigurationManager myConfigManager;
    private static Configuration myCurrentConfiguration;

    private ConfigurationManager() {

    }

    public static ConfigurationManager getInstance() { //Initialize myConfigManager if it hasn't been created yet
        if (myConfigManager == null) {
            myConfigManager = new ConfigurationManager();
        }
        return myConfigManager;
    }

    //Used to load a config file by the path provided
    public void loadConfigFile(String filePath) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath); //Read file
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }
        StringBuffer sb = new StringBuffer();
        int i;
        try {
            while ( (i = fileReader.read()) != -1) { //While there are still lines to read
                sb.append((char)i); //Append the character
            }
        } catch (IOException e) {
            throw new HttpConfigurationException(e);
        }
        JsonNode conf = null; //Initialize the JsonNode
        try {
            conf = Json.parse(sb.toString()); //Parse (object Mapper) the Json request into a Json Node (conf)
        } catch (IOException e) {
                throw new HttpConfigurationException("Error parsing configuration file", e);
        }
        try {
            myCurrentConfiguration = Json.fromJson(conf, Configuration.class); //Parse the JsonNode into a config file
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error parsing the conifguration file, internal", e);
        }
    }

    //Returns current load configuration
    public Configuration getConfiguration() {
        if (myCurrentConfiguration == null) {
            throw new HttpConfigurationException("No current configuration set");
        }
        return myCurrentConfiguration;
    }

}
