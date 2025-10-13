package config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import config.util.Json;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class ConfigurationManager {

    private static ConfigurationManager myConfigManager;
    private static Configuration myCurrentConfiguration;

    private ConfigurationManager() {

    }

    public static ConfigurationManager getInstance() {
        if (myConfigManager == null) {
            myConfigManager = new ConfigurationManager();
        }
        return myConfigManager;
    }

    //Used to load a config file by the path provided
    public void loadConfigFile(String filePath) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }
        StringBuffer sb = new StringBuffer();
        int i;
        try {
            while ( (i = fileReader.read()) != -1) {
                sb.append((char)i);
            }
        } catch (IOException e) {
            throw new HttpConfigurationException(e);
        }
        JsonNode conf = null;
        try {
            conf = Json.parse(sb.toString());
        } catch (IOException e) {
                throw new HttpConfigurationException("Error parsing configuration file", e);
        }
        try {
            myCurrentConfiguration = Json.fromJson(conf, Configuration.class);
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
