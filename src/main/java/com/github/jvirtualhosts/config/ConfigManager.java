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
package com.github.jvirtualhosts.config;

import com.github.jvirtualhosts.storage.orient.ConnectionFactory;
import com.orientechnologies.orient.core.db.object.ODatabaseObjectTx;

import java.io.IOException;

/**
 * Configuration Manager for handling all the configuration
 * for JVH. This class incorporates the OrientDB Connection Factory
 * for accessing the configuration store.
 *
 * @author Mario Mueller<mario.mueller.work@gmail.com>
 */
public class ConfigManager {

    /**
     * Singleton instance
     */
    private static ConfigManager instance;

    /**
     * Reference to the storage connection
     */
    private ODatabaseObjectTx db;

    /**
     * Prevent default bean constructor
     */
    private ConfigManager() {}

    /**
     * Initializes the config. This extra method is for catching the
     * IOException and handling it. Doing a rethrow is not the final
     * solution, but must be sufficient for now.
     */
    public void initConfig() {
        try {
            ODatabaseObjectTx db = ConnectionFactory.factory();
            this.db = db;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Singleton getter for the ConfigManager
     * @return initialized config
     */
    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
            instance.initConfig();
        }
        return instance;
    }
}
