package net.launchpad.jvirtualhosts.management.apache;

import net.launchpad.jvirtualhosts.tool.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: mario
 * Date: 30.03.11
 * Time: 11:40
 * To change this template use File | Settings | File Templates.
 */
public class VirtualHostParser {

	private File vhostFile;
	private String vhostConfig;
	private VirtualHostEntry vhostEntry;

	public void readFile(String fullPath) throws IOException {
		vhostFile = new File(fullPath);
		vhostConfig = FileUtils.readFileAsString(fullPath);
		vhostEntry = new VirtualHostEntry();
	}

	public void parse() {

		Pattern findPort = Pattern.compile("/^<VirtualHost \\*:([0-9]{1,5}?)>/");
		Pattern findHostname = Pattern.compile("/ServerName (.*)/");
		Pattern findDocumentRoot = Pattern.compile("/DocumentRoot (.*)/");
		Pattern findDirectoryTags = Pattern.compile("/#directoryConfig#(.*)#\\/directoryConfig#/", Pattern.DOTALL);

		// This happens within the result string of findDirectoryTags!
		Pattern findDirectory = Pattern.compile("/<Directory (.*?)>/");
		Pattern findDirectoryConfig = Pattern.compile("/<Directory [^>]*>(.*)</Directory>/", Pattern.DOTALL);
		// Finished proc. findDirectoryTags

		Pattern findExtraConfig = Pattern.compile("/#extraConfig#(.*)#\\/extraConfig#/", Pattern.DOTALL);
	}
}
