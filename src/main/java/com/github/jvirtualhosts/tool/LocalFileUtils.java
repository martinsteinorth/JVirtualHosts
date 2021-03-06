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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * File Util class.
 *
 * This class intends to be a helper collection dealing with
 * different, recurring file operations.
 *
 * @author Mario Mueller<mario.mueller.work@gmail.com>
 */
public class LocalFileUtils implements FileOperator {

	/**
	 * Reads a full file to a single string. This reader is
	 * not multibyte-aware (as we do not need it for configuration files)
	 *
	 * @param filePath full qualified path to the file
	 * @return The content of the file as string.
	 * @throws java.io.IOException
	 */
	public final String readFileAsString(String filePath)
			throws java.io.IOException {
		StringBuffer fileData = new StringBuffer(1000);
		BufferedReader reader = new BufferedReader(
				new FileReader(filePath));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		reader.close();
		return fileData.toString();
	}

    @Override
    public boolean saveContentToFile(String content, String path) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean cacheFile(String path) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public File getCacheFile(String path) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
