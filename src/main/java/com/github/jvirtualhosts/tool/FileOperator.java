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
