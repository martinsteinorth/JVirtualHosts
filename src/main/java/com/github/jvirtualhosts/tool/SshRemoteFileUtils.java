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

/**
 * Created by IntelliJ IDEA.
 * User: mario
 * Date: 20.04.11
 * Time: 11:59
 * To change this template use File | Settings | File Templates.
 */
public class SshRemoteFileUtils implements FileOperator {
    @Override
    public String readFileAsString(String path) {
        return null;
    }

    @Override
    public boolean saveContentToFile(String content, String path) {
        return false;
    }

    @Override
    public boolean cacheFile(String path) {
        return false;
    }

    @Override
    public File getCacheFile(String path) {
        return null;
    }
}
