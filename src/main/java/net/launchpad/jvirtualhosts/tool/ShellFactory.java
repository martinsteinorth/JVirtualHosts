package net.launchpad.jvirtualhosts.tool;

import static net.launchpad.jvirtualhosts.tool.ShellFactory.ShellType.*;

/**
 * Created by IntelliJ IDEA.
 * User: mario
 * Date: 19.04.11
 * Time: 21:14
 * To change this template use File | Settings | File Templates.
 */
public abstract class ShellFactory {

    public enum ShellType {
        LOCAL,
        REMOTE
    };

    public static ShellCommandExecutor getShell(ShellType type) {

        switch (type) {
            case LOCAL:
                return new LocalShellUtils();

            case REMOTE:
                return new SshRemoteShellUtils();
            
        }

        return null;
    }
}
