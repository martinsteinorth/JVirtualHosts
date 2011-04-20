package com.github.jvirtualhosts.tool;

/**
 * Created by IntelliJ IDEA.
 * User: mario
 * Date: 19.04.11
 * Time: 21:15
 * To change this template use File | Settings | File Templates.
 */
public interface ShellCommandExecutor {

    boolean executeShellCommand(final String command);

    String getLastOutput();
}
