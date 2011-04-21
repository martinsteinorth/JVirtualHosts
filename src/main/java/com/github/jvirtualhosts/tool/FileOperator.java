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
 * Created by IntelliJ IDEA.
 * User: mario
 * Date: 20.04.11
 * Time: 11:52
 * To change this template use File | Settings | File Templates.
 */
public interface FileOperator {

    String readFileAsString(String path) throws IOException;

    boolean saveContentToFile(String content, String path);

    boolean cacheFile(String path);

    File getCacheFile(String path);
}
