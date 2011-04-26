/**
 * Copyright 2011 Martin Steinorth <martin.steinorth@gmail.com>, Mario Mueller <mario.mueller.work@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jvirtualhosts.config.beans;

import com.github.jvirtualhosts.tool.ConnectionType;

import javax.persistence.Id;

/**
 * Represents a single server configuration entry
 *
 * @author Mario Mueller<mario.mueller.work@gmail.com>
 */
public class ServerConfiguration {

    @Id
    private Object id;

    /**
     * Hostname of a remote server.
     * Leave this on a null value, if you do not
     * need a remote connection.
     */
    private String hostname;

    /**
     * SSH port of a remote server.
     * Leave this on a null value, if you do not
     * need a remote connection.
     */
    private Integer port;

    /**
     * The type of connection that the "server"
     * uses.
     */
    private ConnectionType type;

    /**
     * Username for SSH connection
     * Leave this on a null value, if you do not
     * need a remote connection.
     */
    private String username;

    /**
     * Keyfile for SSH key-based auth.
     * Leave this on a null value, if you do not
     * need a remote connection.
     */
    private String keyFilePath;

    /**
     * Password for SSH auth. Please note, that this is
     * a non-encrypted storage! If you set a keyFilePath
     * the password will be erased. The keyfile is always the
     * preferred kind of authentication!
     */
    private String password;

    private String pathToHostsFile = "/etc/hosts";

    private String pathHostsAvailable = "/etc/apache2/sites-available";

    private String pathHostsEnabled = "/etc/apache2/sites-enabled";

    private boolean useApacheTools = true;

    public String getHostname() {
        return hostname;
    }

    /**
     * Hostname of a remote server.
     * Leave this on a null value, if you do not
     * need a remote connection.
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    /**
     * SSH port of a remote server.
     * Leave this on a null value, if you do not
     * need a remote connection.
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    public ConnectionType getType() {
        return type;
    }

    /**
     * The type of connection that the "server"
     * uses.
     */
    public void setType(ConnectionType type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    /**
     * Username for SSH connection
     * Leave this on a null value, if you do not
     * need a remote connection.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public String getKeyFilePath() {
        return keyFilePath;
    }

    /**
     * Keyfile for SSH key-based auth.
     * Leave this on a null value, if you do not
     * need a remote connection.
     */
    public void setKeyFilePath(String keyFilePath) {
        this.keyFilePath = keyFilePath;
        if (password != null) {
            password = null;
        }
    }

    public String getPassword() {
        return password;
    }

    /**
     * Password for SSH auth. Please note, that this is
     * a non-encrypted storage! If you set a keyFilePath
     * the password will be erased. The keyfile is always the
     * preferred kind of authentication!
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPathToHostsFile() {
        return pathToHostsFile;
    }

    /**
     * Defaults to /etc/hosts
     * @param pathToHostsFile
     */
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
