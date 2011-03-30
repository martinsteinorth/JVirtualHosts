package net.launchpad.jvirtualhosts.management.apache;

import net.launchpad.jvirtualhosts.tool.FileUtils;

import java.io.IOException;

/**
 * Represents as single VirtualHost tag. The member names are equal to their
 * respective representations in the template file:
 * hostname == $hostname$
 * basedir == $basedir$
 * etc...
 *
 * @author Mario Mueller <mario.mueller.mac@me.com>
 */
public class VirtualHostEntry {
	private String hostname = "";
	private String port = "80";
	private String documentRoot = "";
	private String basedir = "";
	private String directoryConfig = "";
	private String extraConfig = "";

	private static final String TEMPLATE = "vhost.tpl";

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDocumentRoot() {
		return documentRoot;
	}

	public void setDocumentRoot(String documentRoot) {
		this.documentRoot = documentRoot;
	}

	public String getBasedir() {
		return basedir;
	}

	public void setBasedir(String basedir) {
		this.basedir = basedir;
	}

	public String getDirectoryConfig() {
		return directoryConfig;
	}

	public void setDirectoryConfig(String directoryConfig) {
		this.directoryConfig = directoryConfig;
	}

	public String getExtraConfig() {
		return extraConfig;
	}

	public void setExtraConfig(String extraConfig) {
		this.extraConfig = extraConfig;
	}

	public String toString() {
		try {
			String template = FileUtils.readFileAsString(ClassLoader.getSystemClassLoader().getResource(TEMPLATE).toExternalForm());

			template = template.replace("$hostname$", getHostname());
			template = template.replace("$port$", getPort());
			template = template.replace("basedir", getBasedir());
			template = template.replace("$documentRoot$", getDocumentRoot());
			template = template.replace("$directoryConfig$", getDirectoryConfig());
			template = template.replace("$extraConfig$", getExtraConfig());

			return template;
		} catch (IOException e) {
			return e.getMessage();
		}
	}
}
