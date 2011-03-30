package net.launchpad.jvirtualhosts.management.apache;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
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

	private static final String filePrefix = "jvh_";

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

	public final List<VirtualHostEntry> getHostList() {
		return hostList;
	}

	private class JVHDirectoryFiler implements FilenameFilter {
		@Override
		public boolean accept(File file, String s) {
			return s.startsWith(filePrefix);
		}
	}
}
