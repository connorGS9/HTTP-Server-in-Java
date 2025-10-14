package config;

public class Configuration {
    //Defines what a config file will look like (what we want) is a port number and a webroot location
    private int port;

    public String getWebroot() {
        return webroot;
    }

    public void setWebroot(String webroot) {
        this.webroot = webroot;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    private String webroot;
}
