package net.launchpad.jvirtualhosts.tool;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: mario
 * Date: 19.04.11
 * Time: 21:15
 * To change this template use File | Settings | File Templates.
 */
public interface ShellCommandExecutor {

    boolean executeShellCommand(final String command);
}
