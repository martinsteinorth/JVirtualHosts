package net.launchpad.jvirtualhosts.management.apache;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: mario
 * Date: 30.03.11
 * Time: 10:58
 * To change this template use File | Settings | File Templates.
 */
public class VirtualHostManager {

	private static final String FILE_PREFIX = "jvh_";
	private static final String APACHE_CONF_PATH = "/etc/apache2/sites-available";
	private static final String APACHE_ENABLED_PATH = "/etc/apache2/sites-enabled";

	private String overrideApacheConfPath;
	private String overrideApacheEnabledPath;

	private final List<VirtualHostEntry> hostList = new ArrayList<VirtualHostEntry>();

	public void parseDirectoryContents(final String directory) throws IOException {
		final File dir = new File(directory);

		if (!dir.isDirectory() || !dir.canRead()) {
			throw new IOException("Not a directory or not readable");
		}

		final String[] files = dir.list(new JVHDirectoryFiler());

		for (int i = 0; i <= files.length; i++) {
			final String fullPath = directory + files[i];
			hostList.add(VirtualHostParser.parse(fullPath));
		}
	}

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

	public final List<VirtualHostEntry> getHostList() {
		return hostList;
	}

	public final boolean enableVirtualHost(final VirtualHostEntry vhost) {

		if (isVirtualHostEnabled(vhost)) {
			return true;
		}

		String cmd = "a2ensite " + getVirtualHostFileName(vhost);
		return executeShellCommand(cmd);
	}

	public final boolean disableVirtualHost(final VirtualHostEntry vhost) {
		if (!isVirtualHostEnabled(vhost)) {
			return true;
		}
		String cmd = "a2dissite " + getVirtualHostFileName(vhost);
		return executeShellCommand(cmd);
	}

	private boolean executeShellCommand(final String command) {
		Runtime run = Runtime.getRuntime();
		Process pr = null;

		try {
			pr = run.exec(command);
			pr.waitFor();
			return true;

		} catch (IOException e) {
			return false;
		} catch (InterruptedException e) {
			return false;
		}
	}

	public final boolean isVirtualHostInitiallySaved(final VirtualHostEntry vhost) {
		File hostFile = new File(getPathToVirtualHostConfig(vhost));
		return hostFile.exists();
	}

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

	private final String getPathToVirtualHostConfig(final VirtualHostEntry vhost) {
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

	private String getVirtualHostFileName(final VirtualHostEntry vhost) {
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

	private class JVHDirectoryFiler implements FilenameFilter {
		@Override
		public boolean accept(File file, String s) {
			return s.startsWith(FILE_PREFIX);
		}
	}
}
