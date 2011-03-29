package net.launchpad.jvirtualhosts.management;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manager Class for the Linux hosts file.
 *
 * @author Mario Mueller <mario.mueller.mac@me.com>
 */
public class LinuxHostsFileManager {

	/**
	 * Path to the hostfile on the linxu system
	 */
	public static final String PATH_TO_HOSTFILE = "/etc/hosts";

	/**
	 *  Start marker for the section that is controlled by JVH
	 */
	private static final String START_SECTION = "### JVirtualHosts ###\n";

	/**
	 *  Marker for the end of the section that is controlled by JVH
	 */
	private static final String END_SECTION = "### /JVirtualHosts ###\n";

	/**
	 *  Template for a single entry in the hosts file
	 */
	private static final String ENTRY_TEMPLATE = "127.0.0.1	$hostname$\n";

	/**
	 *  List of hosts that are already present in the hosts file
	 */
	private List<String> presentHostnames = new ArrayList<String>();

	/**
	 *  Java File Instance of the hosts file
	 */
	private File hostsFile;

	private List<String> hostFileLineBuffer = new ArrayList<String>();


	public File getHostsfile(final String path) {

		return new File(path);
	}

	/**
	 *  Reads the hosts from the file.
	 * @param file the file instance to read the hosts from
	 * @return -1 for not installed, 0 or higher for the number of hosts found.
	 * @author Mario Mueller <mario.mueller.mac@me.com>
	 */
	public int readHostsFileData(final File file) {
		this.hostsFile = file;

		try {
			FileInputStream fileInputStream = new FileInputStream(hostsFile);
			DataInputStream dataInputStream = new DataInputStream(fileInputStream);
			InputStreamReader inputStreamReader = new InputStreamReader(dataInputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String lineValue;
			Boolean openMarkerFound = false;
			Boolean closeMarkerFound = false;
			while((lineValue = bufferedReader.readLine()) != null) {
				if (lineValue.trim().equals(START_SECTION)) {
					openMarkerFound = true;
					continue;
				}
				if (lineValue.trim().equals(END_SECTION)) {
					closeMarkerFound = true;
					continue;
				}

				// Buffer everything before and after our markers for writing it later.
				if (!openMarkerFound || (openMarkerFound && closeMarkerFound)) {
					hostFileLineBuffer.add(lineValue.trim());
				}


				if (openMarkerFound && !closeMarkerFound) {
					final String hostname = lineValue.substring(9);
					if (!hostname.equals("")) {
						presentHostnames.add(hostname);
					}
				}
			}

			return presentHostnames.size();

		} catch (IOException ioException) {
			// TODO do something useful
			return -1;
		}
	}

	public void saveHostFile() {
		final StringBuffer stringBuffer = new StringBuffer();
		for (final String line : hostFileLineBuffer) {
			stringBuffer.append(line);
			stringBuffer.append("\n");
		}

		stringBuffer.append(START_SECTION);
		for (final String hostname : presentHostnames) {
			stringBuffer.append(ENTRY_TEMPLATE.replace("$hostname$", hostname));
			stringBuffer.append("\n");
		}
		stringBuffer.append(END_SECTION);

		try {
			FileOutputStream fileOutputStream = new FileOutputStream(hostsFile);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
			outputStreamWriter.write(stringBuffer.toString());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace(); 
		}
	}



	public void addNewHostname(final String hostname) {
		if (!presentHostnames.contains(hostname)) {
			presentHostnames.add(hostname);
		}
	}

	public void removeHostname(final String hostname) {
		if (presentHostnames.contains(hostname)) {
			presentHostnames.remove(hostname);
		}
	}

	public List<String> getPresentHostnames() {
		return presentHostnames;
	}

	public File getHostsFile() {
		return hostsFile;
	}
}
