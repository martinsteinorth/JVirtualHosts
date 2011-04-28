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

/**
 * Created by IntelliJ IDEA.
 * User: mario
 * Date: 19.04.11
 * Time: 21:15
 * To change this template use File | Settings | File Templates.
 */
public interface ShellCommandExecutor {

    /**
     * Executes a shell command.
     * @param command
     * @return true if nothing was send to stdErr
     */
    boolean executeShellCommand(final String command);

    /**
     * Returns the last output, whether it came from stdErr or stdOut
     * @return a string containing the output of the last command executed by executeShellCommand()
     */
    String getLastOutput();
}
