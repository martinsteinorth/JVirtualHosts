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
package com.github.jvirtualhosts.storage.orient;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.db.object.ODatabaseObjectTx;

import java.io.File;
import java.io.IOException;

/**
 * OrientDB connection factory. This is the persistent storage
 * for any kind of configuration you want or need to store within
 * the JVH environment. Create a new bean beside ServerConfiguration if
 * you need to store an new kind of entity.
 *
 * @author Mario Mueller<mario.mueller.work@gmail.com>
 */
public class ConnectionFactory {

    /**
     * Singleton instance
     */
    private static ODatabaseObjectTx instance;

    /**
     * Factory for testing purposes. You should not use this method for the real
     * code work, but for unit tests.
     * @param pathToDb
     * @return instance of ODatabaseObjectTx typed for the given path
     * @throws IOException
     */
    public static ODatabaseObjectTx factory(String pathToDb) throws IOException {
        File f = new File(pathToDb);
        File p = new File(f.getParent());

        if (!p.exists()) {
            p.createNewFile();
        }

        if (instance == null) {
            ODatabaseObjectTx db = new ODatabaseObjectTx("local:" + pathToDb);

            if (!f.exists()) {
                db.create();
            } else {
                db.open("admin", "admin");
            }

            instance = db;
            instance.getEntityManager().registerEntityClasses("com.github.jvirtualhosts.config.beans");
        }
        return instance;
    }

    /**
     * Default factory method for getting an ODatabaseObjectTx instance.
     * The default location for the database is ~/.jvh/database
     * @return the instance of ODatabaseObjectTx, typed for the default path
     * @throws IOException
     */
    public static ODatabaseObjectTx factory() throws IOException {
        final String pathToDb = System.getProperty("user.home") + "/.jvh/database";

        return factory(pathToDb);
    }
}
