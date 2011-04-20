package com.github.jvirtualhosts.tool;

/**
 * Created by IntelliJ IDEA.
 * User: mario
 * Date: 20.04.11
 * Time: 11:52
 * To change this template use File | Settings | File Templates.
 */
public abstract class FileFactory {
    public static FileOperator factory(ConnectionType type) {

        switch (type) {
            case LOCAL:
                return new LocalFileUtils();
            case REMOTE:
                return new SshRemoteFileUtils();
        }
        return null;
    }
}
