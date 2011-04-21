package com.github.jvirtualhosts.config;

import com.github.jvirtualhosts.storage.orient.ConnectionFactory;
import com.orientechnologies.orient.core.db.object.ODatabaseObjectTx;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: mario
 * Date: 21.04.11
 * Time: 22:02
 * To change this template use File | Settings | File Templates.
 */
public class ConfigManager {

    private static ConfigManager instance;

    private ODatabaseObjectTx db;

    private ConfigManager() {}

    public void initConfig() {
        try {
            ODatabaseObjectTx db = ConnectionFactory.factory();

            this.db = db;


        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
            instance.initConfig();
        }
        return instance;
    }

}
