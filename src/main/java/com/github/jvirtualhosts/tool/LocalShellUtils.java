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

import java.io.*;

/**
 * Local Shell Utils.
 *
 * Uses the default runtime to execute commands locally.
 *
 * @author Mario Mueller<mario.mueller.work@gmail.com>
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
