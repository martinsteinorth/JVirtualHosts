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
package com.github.jvirtualhosts.tool;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: mario
 * Date: 19.04.11
 * Time: 21:19
 * To change this template use File | Settings | File Templates.
 */
public class SshRemoteShellUtils implements ShellCommandExecutor {

    private String host;

    private String user;

    private String password;

    private String keyfile;

    private StringBuilder stdOut;

    private StringBuilder stdErr;

    private String lastOutput;

    @Override
    public boolean executeShellCommand(String command) {
        Logger log = Logger.getLogger("Remote SSH Executor");
        JSch jsch = new JSch();
        try {
            jsch.setKnownHosts("/home/" + System.getProperty("user.name") + "/.ssh/known_hosts");

            Session session=jsch.getSession(user, host, 22);

        } catch (JSchException e) {

            log.error(e.getMessage(), e);
            return false;
        }
        return false;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKeyfile() {
        return keyfile;
    }

    public void setKeyfile(String keyfile) {
        this.keyfile = keyfile;
    }

    @Override
    public String getLastOutput() {
        return lastOutput;
    }
}
