package net.launchpad.jvirtualhosts.tool;

import java.io.IOException;

/**
 * User: mmueller
 * Date: 08.04.11
 * Time: 09:26
 */
public abstract class ShellUtils {

    private ShellUtils() {}

    /**
	 * executes a shell command - used for disableVirtualHost and enableVirtualHost.
	 * @param command The command to be executed
	 * @return true for success, false for failure
	 */
	public static final boolean executeShellCommand(final String command) {
		Runtime run = Runtime.getRuntime();
		Process pr;

		try {
			pr = run.exec(command);
			pr.waitFor();
			return true;

		} catch (IOException e) {
			return false;
		} catch (InterruptedException e) {
			return false;
		}
	}
}
