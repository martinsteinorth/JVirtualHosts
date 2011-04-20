package com.github.jvirtualhosts.management.dns;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manager Class for the Linux hosts-at-end file.
 * The manager uses markers for finding entries in the
 * present host file. This process can only succeed, if the
 * user does not interfere the process by manually editing the
 * host file.
 *
 * @author Mario Mueller<mario.mueller.work@gmail.com>
 */
public class LinuxHostsFileManager {

	/**
	 *  Start marker for the section that is controlled by JVH
	 */
	private static final String START_SECTION = "### JVirtualHosts ###\n";

	/**
	 *  Marker for the end of the section that is controlled by JVH
	 */
	private static final String END_SECTION = "### /JVirtualHosts ###\n";

	/**
	 *  Template for a single entry in the hosts-at-end file
	 */
	private static final String ENTRY_TEMPLATE = "127.0.0.1	$hostname$\n";

	/**
	 *  List of hosts-at-end that are already present in the hosts-at-end file
	 */
	private List<String> presentHostnames = new ArrayList<String>();

	/**
	 *  Java File Instance of the hosts-at-end file
	 */
	private File hostsFile;

	/**
	 *  Buffer for the data that is not affected by our edits. We read the whole file. We write the whole file.
	 */
	private List<String> hostFileLineBuffer = new ArrayList<String>();


	/**
	 * @return returns the reference to the hostfile instance, given in readHostsFileData()
	 */
	public File getHostsfile() {

		return hostsFile;
	}

	/**
	 * Reads the hosts-at-end from the file.
	 * @param file the file instance to read the hosts-at-end from
	 * @return -2 on error,-1 for not installed, 0 or higher for the number of hosts-at-end found.
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
				if (lineValue.trim().equals(START_SECTION.trim())) {
					openMarkerFound = true;
					continue;
				}
				if (lineValue.trim().equals(END_SECTION.trim())) {
					closeMarkerFound = true;
					continue;
				}

				// Buffer everything before and after our markers for writing it later.
				if (!openMarkerFound || (openMarkerFound && closeMarkerFound)) {
					hostFileLineBuffer.add(lineValue.trim());
				}


				if (openMarkerFound && !closeMarkerFound && !lineValue.trim().equals("")) {
					final String hostname = lineValue.substring(9).trim();
					if (!hostname.equals("")) {
						presentHostnames.add(hostname);
					}
				}
			}

			bufferedReader.close();
			inputStreamReader.close();
			dataInputStream.close();
			fileInputStream.close();

			// Report not installed
			if (!openMarkerFound && !closeMarkerFound) {
				return -1;
			}
			
			return presentHostnames.size();

		} catch (IOException ioException) {
			// TODO do something useful
			return -2;
		}
	}

	/**
	 * Saves a new hostfile to the file instance given as parameter
	 * @param file
	 */
	public void saveHostFile(final File file) {
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
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
			outputStreamWriter.write(stringBuffer.toString());
			outputStreamWriter.close();
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace(); 
		}
	}

	/**
	 * Adds a new hostname to the file
	 * @param hostname
	 */
	public void addNewHostname(final String hostname) {
		if (!presentHostnames.contains(hostname)) {
			presentHostnames.add(hostname);
		}
	}

	/**
	 * removes a hostname from the file
	 * @param hostname
	 */
	public void removeHostname(final String hostname) {
		if (presentHostnames.contains(hostname)) {
			presentHostnames.remove(hostname);
		}
	}

	/**
	 * @return returns all found hostnames after a file was parsed
	 */
	public List<String> getPresentHostnames() {
		return presentHostnames;
	}
}
