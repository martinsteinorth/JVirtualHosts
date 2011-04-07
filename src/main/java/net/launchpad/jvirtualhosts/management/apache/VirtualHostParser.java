package net.launchpad.jvirtualhosts.management.apache;

import net.launchpad.jvirtualhosts.tool.FileUtils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Virtual Host Parser
 *
 * @author Mario Mueller<mario.mueller.work@gmail.com>
 */
abstract public class VirtualHostParser {

	private VirtualHostParser() {}

	public static VirtualHostEntry parse(String fullPath) throws IOException {
		String vhostConfig = FileUtils.readFileAsString(fullPath);
		VirtualHostEntry vhostEntry = new VirtualHostEntry();

		Pattern findPort = Pattern.compile("<VirtualHost \\*:([0-9]{1,5})>");
		Pattern findHostname = Pattern.compile("ServerName (.*)");
		Pattern findDocumentRoot = Pattern.compile("DocumentRoot (.*)");
		Pattern findDirectoryTags = Pattern.compile("#directoryConfig#(.*)#/directoryConfig#", Pattern.DOTALL);

		// This happens within the result string of findDirectoryTags!
		Pattern findDirectory = Pattern.compile("<Directory (.*?)>");
		Pattern findDirectoryConfig = Pattern.compile("<Directory [^>]*>(.*)</Directory>", Pattern.DOTALL);
		// Finished proc. findDirectoryTags

		Pattern findExtraConfig = Pattern.compile("#extraConfig#(.*)#/extraConfig#", Pattern.DOTALL);

		Matcher matchPort = findPort.matcher(vhostConfig);
		matchPort.find();
		vhostEntry.setPort(matchPort.group(1));

		Matcher matchHostname = findHostname.matcher(vhostConfig);
		matchHostname.find();
		vhostEntry.setHostname(matchHostname.group(1));

		Matcher matchDocumentRoot = findDocumentRoot.matcher(vhostConfig);
		matchDocumentRoot.find();
		vhostEntry.setDocumentRoot(matchDocumentRoot.group(1));

		Matcher matcherDirectoryTags = findDirectoryTags.matcher(vhostConfig);
		matcherDirectoryTags.find();
		String directoryTags = matcherDirectoryTags.group(1);

		Matcher matchBasedir = findDirectory.matcher(directoryTags);
		matchBasedir.find();
		vhostEntry.setBasedir(matchBasedir.group(1));

		Matcher matchDirectoryConfig = findDirectoryConfig.matcher(directoryTags);
		matchDirectoryConfig.find();
		vhostEntry.setDirectoryConfig(matchDirectoryConfig.group(1));

		Matcher matchExtraConfig = findExtraConfig.matcher(vhostConfig);
		matchExtraConfig.find();
		vhostEntry.setExtraConfig(matchExtraConfig.group(1));

		return vhostEntry;
	}
}
