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

import org.apache.log4j.Logger;

import java.io.File;

/**
 * Apache Util class.
 *
 * This class intends to be a helper collection dealing with
 * apache configuration ops.
 *
 * @author Mario Mueller<mario.mueller.work@gmail.com>
 */
public class ApacheUtils {

    /**
     * Chosen type of operation
     */
    private ConnectionType type;

    /**
     * The typed shell
     */
    private ShellCommandExecutor shell;

    /**
     * Factory for local or remote version of the Utils
     * @param type remote or local type
     * @return a locally or remotely typed util instance.
     */
    public static ApacheUtils factory(final ConnectionType type) {
        ApacheUtils au = new ApacheUtils();
        au.type = type;
        au.shell = ShellFactory.getShell(type);
        return au;
    }

    /**
     * Returns the shell which the util class works with. Call
     * getLastOutput() on it to receive the output of the last command
     * that has been executed.
     * @return a typed shell instance
     */
    public ShellCommandExecutor getShell() {
        return shell;
    }

    /**
     * Disables a host
     * @param pathToEnabledSites
     * @param filename
     * @return the shell command
     */
    public boolean disableHost(String pathToEnabledSites, String filename) {
        Logger log = Logger.getLogger("ApacheUtils");

        String cmd;
        if (areApacheToolsAvailable() && pathToEnabledSites == null) {
            log.debug("Apache tools are available for disabling host.");
            cmd = "a2dissite " + filename;
        } else {
            log.debug("Apache tools are not available for disabling host. Using fallback.");
            cmd = "rm -f " + pathToEnabledSites + "/" + filename;
        }
        log.info("Executing shell command for disableHost: " + cmd);
        boolean result = shell.executeShellCommand(cmd);
        log.info("Shell output: " + shell.getLastOutput());
        return result;
    }

    public boolean enableHost(String pathToAvailableSites, String pathToEnabledSites, String filename) {
        Logger log = Logger.getLogger("ApacheUtils");

        String cmd;
        if (areApacheToolsAvailable() && pathToEnabledSites == null && pathToAvailableSites == null) {
            log.debug("Apache tools are available for enabling host.");
            cmd = "a2ensites " + filename;
        } else {
            log.debug("Apache tools are not available for enabling host. Using fallback.");
            cmd = "ln -s " + pathToAvailableSites + "/" + filename + " " + pathToEnabledSites;
        }
        log.info("Executing shell command for enableHost: " + cmd);
        boolean result = shell.executeShellCommand(cmd);
        log.info("Shell output: " + shell.getLastOutput());
        return result;
    }

    private boolean areApacheToolsAvailable() {

        Logger log = Logger.getLogger("ApacheUtils");

        if (type.equals(ConnectionType.LOCAL)) {
            final String defaultToolPosition = "/usr/sbin/a2ensite";
            File checkFile = new File(defaultToolPosition);
            return checkFile.exists();
        } else {
            final String testCommand = "if [ -e \"/usr/sbin/a2ensite\" ]; then echo 1; else echo 0; fi;";
            shell.executeShellCommand(testCommand);
            String output = shell.getLastOutput();

            if (output.equals("1")) {
                return true;
            }
            else {
                return false;
            }
        }
    }

    /**
     * Reloads the apache config.
     *
     * @TODO This method must check for upstart or sysv
     */
    public boolean reloadApacheConfig() {
        String command = "sudo /etc/init.d/apache2 reload";
        return shell.executeShellCommand(command);
    }
}
