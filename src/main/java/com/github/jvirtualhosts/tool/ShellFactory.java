package com.github.jvirtualhosts.tool;

/**
 * Created by IntelliJ IDEA.
 * User: mario
 * Date: 19.04.11
 * Time: 21:14
 * To change this template use File | Settings | File Templates.
 */
public abstract class ShellFactory {

    public static ShellCommandExecutor getShell(ConnectionType type) {

        switch (type) {
            case LOCAL:
                return new LocalShellUtils();

            case REMOTE:
                return new SshRemoteShellUtils();
            
        }

        return null;
    }
}
