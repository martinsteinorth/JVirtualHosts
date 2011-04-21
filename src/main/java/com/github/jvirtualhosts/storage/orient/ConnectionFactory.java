package com.github.jvirtualhosts.storage.orient;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.db.object.ODatabaseObjectTx;

import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: mario
 * Date: 21.04.11
 * Time: 15:03
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionFactory {

    private static ODatabaseObjectTx instance;

    public static ODatabaseObjectTx factory() throws IOException {
        final String pathToDb = System.getProperty("user.home") + "/.jvh/database";

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
}
