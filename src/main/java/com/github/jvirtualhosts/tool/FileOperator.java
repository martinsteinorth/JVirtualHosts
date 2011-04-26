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

import java.io.File;
import java.io.IOException;

/**
 * FileOperator Interface.
 *
 * Interface for all file operators. At the time of writing there
 * are two known implementations: LocalFileUtils and SshRemoteFileUtils.
 * Get them by using the FileFactory.
 *
 * @author Mario Mueller<mario.mueller.work@gmail.com>
 */
public interface FileOperator {

    /**
     * The implementation of this method should read a file into a string, keeping the newline chars.
     * @param path
     * @return The whole file as a single string
     * @throws IOException
     */
    String readFileAsString(String path) throws IOException;

    /**
     * The implementation of this method should write the given content to a file, keeping the newline chars.
     * @param content the file's content
     * @param path the path of the file to be written by the implementation, locally or remote.
     * @return true on a successful save, false on any error. The error should be logged.
     */
    boolean saveContentToFile(String content, String path);

    boolean cacheFile(String path);

    File getCacheFile(String path);
}
