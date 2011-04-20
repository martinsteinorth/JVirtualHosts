package com.github.jvirtualhosts.tool;

import java.io.*;

/**
 * User: mmueller
 * Date: 08.04.11
 * Time: 09:26
 */
public class LocalShellUtils implements ShellCommandExecutor {

    private StringBuilder stdOut;

    private StringBuilder stdErr;

    private String lastOutput;

    /**
	 * executes a shell command - used for disableVirtualHost and enableVirtualHost.
	 * @param command The command to be executed
	 * @return true for success, false for failure
	 */
	public final boolean executeShellCommand(final String command) {
		Runtime run = Runtime.getRuntime();
		Process pr;
        String s;

        stdOut = new StringBuilder();
        stdErr = new StringBuilder();
        lastOutput = "";

		try {
			pr = run.exec(command);
			pr.waitFor();

            BufferedReader stdInput = new BufferedReader(new
                 InputStreamReader(pr.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                 InputStreamReader(pr.getErrorStream()));

            // read the output from the command
            while ((s = stdInput.readLine()) != null) {
                stdOut.append(s);
            }

            // read any errors from the attempted command
            while ((s = stdError.readLine()) != null) {
                stdErr.append(s);
            }

            // No output means no error - hopefully
            if (stdOut.toString().equals("") && stdErr.toString().equals("")) {
                lastOutput = "";
                return true;
            }

            // stdOut only, success!
            if (!(stdOut.toString().equals("")) && stdErr.toString().equals("")) {
                lastOutput = stdOut.toString();
                return true;
            }

            // stdErr only, bad thing
            if ((stdOut.toString().equals("")) && !(stdErr.toString().equals(""))) {
                lastOutput = stdErr.toString();
                return false;
            }

            return false;

		} catch (IOException e) {
			return false;
		} catch (InterruptedException e) {
			return false;
		}
	}

    /**
     * Get the output of the last command as string.
     * @return string representation of the last output.
     */
    @Override
    public String getLastOutput() {
        return lastOutput;
    }
}
