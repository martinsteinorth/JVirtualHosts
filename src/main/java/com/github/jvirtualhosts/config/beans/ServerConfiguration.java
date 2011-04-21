package com.github.jvirtualhosts.config.beans;

import com.github.jvirtualhosts.tool.ConnectionType;

import javax.persistence.Id;

/**
 * Created by IntelliJ IDEA.
 * User: mario
 * Date: 21.04.11
 * Time: 21:56
 * To change this template use File | Settings | File Templates.
 */
public class ServerConfiguration {

    @Id
    private Object id;

    private String hostname;

    private Integer port;

    private ConnectionType type;

    private String username;

    private String keyFilePath;

    private String password;

    private String pathToHostsFile;

    private String pathHostsAvailable;

    private String pathHostsEnabled;

    private boolean useApacheTools;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public ConnectionType getType() {
        return type;
    }

    public void setType(ConnectionType type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKeyFilePath() {
        return keyFilePath;
    }

    public void setKeyFilePath(String keyFilePath) {
        this.keyFilePath = keyFilePath;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPathToHostsFile() {
        return pathToHostsFile;
    }

    public void setPathToHostsFile(String pathToHostsFile) {
        this.pathToHostsFile = pathToHostsFile;
    }

    public String getPathHostsAvailable() {
        return pathHostsAvailable;
    }

    public void setPathHostsAvailable(String pathHostsAvailable) {
        this.pathHostsAvailable = pathHostsAvailable;
    }

    public String getPathHostsEnabled() {
        return pathHostsEnabled;
    }

    public void setPathHostsEnabled(String pathHostsEnabled) {
        this.pathHostsEnabled = pathHostsEnabled;
    }

    public boolean isUseApacheTools() {
        return useApacheTools;
    }

    public void setUseApacheTools(boolean useApacheTools) {
        this.useApacheTools = useApacheTools;
    }
}
