package net.launchpad.jvirtualhosts.tool;

import java.io.File;

/**
 * User: mmueller
 * Date: 08.04.11
 * Time: 10:47
 */
public abstract class ApacheUtils {

    public static boolean disableHost(String pathToEnabledSites, String filename) {

        String cmd;
        if (areApacheToolsAvailable()) {

            cmd = "a2dissite " + filename;
        } else {
            cmd = "rm -f " + pathToEnabledSites + "/" + filename;
        }
        ShellCommandExecutor sce = ShellFactory.getShell(ShellFactory.ShellType.LOCAL);
        return sce.executeShellCommand(cmd);
    }

    public static boolean enableHost(String pathToAvailableSites, String pathToEnabledSites, String filename) {

        String cmd;
        if (areApacheToolsAvailable()) {

            cmd = "a2ensites " + filename;
        } else {
            cmd = "ln -s " + pathToAvailableSites + "/" + filename + " " + pathToEnabledSites;
        }
        ShellCommandExecutor sce = ShellFactory.getShell(ShellFactory.ShellType.LOCAL);
        return sce.executeShellCommand(cmd);
    }

    private static boolean areApacheToolsAvailable() {
        final String defaultToolPosition = "/usr/sbin/a2ensite";
        File checkFile = new File(defaultToolPosition);
        return checkFile.exists();
    }

    public static void reloadApacheConfig() {

    }
}
