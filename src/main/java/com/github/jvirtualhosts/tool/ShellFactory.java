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
 * ShellFactory.
 *
 * This class intends to be a factory for ShellCommandExecutor children.
 *
 * @author Mario Mueller<mario.mueller.work@gmail.com>
 */
public abstract class ShellFactory {

    /**
     * Returns the shell utils for the given connection type
     * @param type the type you want to use
     * @return a shell utils instance depending on the type
     */
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
