package net.launchpad.jvirtualhosts.management.apache;

import net.launchpad.jvirtualhosts.tool.ApacheUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Virtual Host Manager
 *
 * @author Mario Mueller<mario.mueller.work@gmail.com>
 */
public class VirtualHostManager {

	/**
	 * The prefix for all files to be managed by JVH
	 */
	private static final String FILE_PREFIX = "jvh_";

	/**
	 * Fallback directory for ubuntu/debian linux machines.
	 * @TODO This might desirably a configuration parameter
	 */
	private static final String APACHE_CONF_PATH = "/etc/apache2/sites-available";

	/**
	 * Fallback directory for ubuntu/debian linux machines.
	 * @TODO This might desirably a configuration parameter
	 */
	private static final String APACHE_ENABLED_PATH = "/etc/apache2/sites-enabled";

	/**
	 * Settable path for unittest
	 * @TODO This might desirably a configuration parameter
	 */
	private String overrideApacheConfPath;

	/**
	 * Settable path for unittest
	 * @TODO This might desirably a configuration parameter
	 */
	private String overrideApacheEnabledPath;

	/**
	 * The list of managed entries
	 */
	private final List<VirtualHostEntry> hostList = new ArrayList<VirtualHostEntry>();

    /**
     * Singleton Instance
     */
    private static VirtualHostManager instance = null;

    /**
     * We need one Manager per Application, so make this a singleton.
     * This prevents accidentally parsing the contents twice.
     * @return Singleton instance of VirtualHostManager
     */
    public static final VirtualHostManager getInstance() {
        if (instance == null) {
            instance = new VirtualHostManager();
        }
        return instance;
    }

    /**
     * Denied default constructor, use the singleton.
     */
    private VirtualHostManager() {}

	/**
	 * Gets all files in the directory by using a private inner class as filter for
	 * the directory contents.
	 * @see net.launchpad.jvirtualhosts.management.apache.VirtualHostManager.JVHDirectoryFiler
	 * @param directory The directory to read
	 * @throws IOException
	 */
	public void parseDirectoryContents(final String directory) throws IOException {
		Logger log = Logger.getLogger("VhostDirctoryParser");

		log.info("Using directory " + directory);
		final File dir = new File(directory);

		if (!dir.isDirectory() || !dir.canRead()) {
			log.error("Given path does not point to a directory or is not readable at all");
			throw new IOException("Not a directory or not readable");
		}

		log.info("Filtering directories");
		final String[] files = dir.list(new JVHDirectoryFiler());
		log.info("Finished filtering, found " + files.length + " entries");

		for (int i = 0; i < files.length; i++) {
			final String fullPath = directory + "/" + files[i];
			log.debug("Adding " + fullPath + " to host stack");
			hostList.add(VirtualHostParser.parse(fullPath));
		}
	}

	/**
	 * Saves the managed virtual host to his respective file.
	 * @param vhost the vhost entry
	 * @return true on successful save
	 * @throws IOException
	 */
	public boolean saveVirtualHost(final VirtualHostEntry vhost) throws IOException {
		String content = vhost.toString();
		File file = new File(getPathToVirtualHostConfig(vhost));

		if (file.exists()) {
			file.delete();
			file.createNewFile();
		}

		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
			outputStreamWriter.write(content);
			outputStreamWriter.close();
			fileOutputStream.close();

			return true;
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		} 
	}

    /**
     * returns all hosts parsed from the directory
     * @return a(n) (empty) list of virtualhosts but never null
     */
	public final List<VirtualHostEntry> getHostList() {
		return hostList;
	}

    /**
     * Returns a single VirtualHostEntry by its hostname
     * @return virtual host entry corresponding to the hostname or null
     */
    public final VirtualHostEntry getVirtualHostEntry(String hostname) {
        for (VirtualHostEntry entry : getHostList()) {
            if (entry.getHostname().equals(hostname)) {
                return entry;
            }
        }
        return null;
    }

	/**
	 * Enables a virtual host using the default apache2 a2ensite tools.
	 * This must change as we want to support more than just the ubuntu or debian setup.
	 *
	 * @param vhost The vhost entry to enable
	 * @return true on success, false on failure
	 */
	public final boolean enableVirtualHost(final VirtualHostEntry vhost) {

		if (isVirtualHostEnabled(vhost)) {
			return true;
		}

		return ApacheUtils.enableHost(getOverrideApacheConfPath(), getOverrideApacheEnabledPath(), getVirtualHostFileName(vhost));
	}

	/**
	 * Disables a virtual host using the default apache2 a2dissite tools.
	 * This must change as we want to support more than just the ubuntu or debian setup.
	 *
	 * @param vhost The vhost entry to enable
	 * @return true on success, false on failure
	 */
	public final boolean disableVirtualHost(final VirtualHostEntry vhost) {
		if (!isVirtualHostEnabled(vhost)) {
			return true;
		}
		return ApacheUtils.disableHost(getOverrideApacheEnabledPath(), getVirtualHostFileName(vhost));
	}

	/**
	 * Checks if the host file does already exist.
	 * @param vhost The vhost to check for
	 * @return true if the vhosts file does exist
	 */
	public final boolean isVirtualHostInitiallySaved(final VirtualHostEntry vhost) {
		File hostFile = new File(getPathToVirtualHostConfig(vhost));
		return hostFile.exists();
	}

	/**
	 * Checks if the vhost is enabled
	 * @param vhost The vhost to check for
	 * @return true for enabled
	 */
	public final boolean isVirtualHostEnabled(final VirtualHostEntry vhost) {
		String path;
		if (overrideApacheEnabledPath != null) {
			path = overrideApacheEnabledPath;
		} else {
			path = APACHE_ENABLED_PATH;
		}
		File enabledHost = new File(path + "/" + getVirtualHostFileName(vhost));
		return enabledHost.exists();
	}

	/**
	 * Builds the path to the vhost config file. This demands on the override*Path members.
	 * @param vhost The vhost to assemble the path for
	 * @return the full path to the file
	 */
	public final String getPathToVirtualHostConfig(final VirtualHostEntry vhost) {
		String path;
		if (overrideApacheConfPath != null) {
			path = overrideApacheConfPath;
		} else {
			path = APACHE_CONF_PATH;
		}
		StringBuffer stringBuffer = new StringBuffer(path);
		stringBuffer.append("/");
		stringBuffer.append(getVirtualHostFileName(vhost));
		return stringBuffer.toString();
	}

	/**
	 * Builds the filename of the vhost file.
	 * @param vhost The vhost to build the filename for
	 * @return the assembled filename
	 */
	public final String getVirtualHostFileName(final VirtualHostEntry vhost) {
		StringBuffer stringBuffer = new StringBuffer(FILE_PREFIX);
		stringBuffer.append(vhost.getHostname());
		stringBuffer.append("-");
		stringBuffer.append(vhost.getPort());
		return stringBuffer.toString();
	}

	public String getOverrideApacheConfPath() {
		return overrideApacheConfPath;
	}

	public void setOverrideApacheConfPath(String overrideApacheConfPath) {
		this.overrideApacheConfPath = overrideApacheConfPath;
	}

	public String getOverrideApacheEnabledPath() {
		return overrideApacheEnabledPath;
	}

	public void setOverrideApacheEnabledPath(String overrideApacheEnabledPath) {
		this.overrideApacheEnabledPath = overrideApacheEnabledPath;
	}

	/**
	 * Inner class for the directory filter.
	 * It assures that only the prefixed files are taken to the parser step.
	 */
	private class JVHDirectoryFiler implements FilenameFilter {
		@Override
		public boolean accept(File file, String s) {
			return s.startsWith(FILE_PREFIX);
		}
	}
}
