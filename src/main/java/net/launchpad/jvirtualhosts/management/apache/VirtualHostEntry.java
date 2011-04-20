package net.launchpad.jvirtualhosts.management.apache;

import net.launchpad.jvirtualhosts.tool.LocalFileUtils;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Represents as single VirtualHost tag. The member names are equal to their
 * respective representations in the template file:
 * hostname == $hostname$
 * basedir == $basedir$
 * etc...
 *
 * @author Mario Mueller<mario.mueller.work@gmail.com>
 */
public class VirtualHostEntry {
	/**
	 * Hostname
	 */
	private String hostname = "";

	/**
	 * Port, defaults to 80
	 */
	private String port = "80";

	/**
	 * Document Root of the vHost
	 */
	private String documentRoot = "";

	/**
	 * Basedir of the vHost
	 * The basedir differs from the document root in case the application has some
	 * "public" or "http" folder, which is located in the basedir
	 */
	private String basedir = "";

	/**
	 * Directory config for the basedir. This should be something like "AllowOverride", etc
	 */
	private String directoryConfig = "";

	/**
	 * Extra config you want to have in the VirtualHost tag, placed after
	 * the directory tag mentioned in the basedir comment.
	 */
	private String extraConfig = "";

	/**
	 * The vhost template that should be used. This file has to be on classpath.
	 * @TODO make this more felxible through configuration (file or gui based?)
	 */
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

	/**
	 * This method can be used to generate a list of host entries and put it into a
	 * vector for the list view.
	 * @return the hostname, like "www.example.com"
	 */
	@Override
	public String toString() {
		return this.getHostname();
	}

	/**
	 * Renders the template given in VirtualHostEntry.TEMPLATE combined with
	 * the values of this instance. Be sure to check the values first, there is
	 * no validation while rendering the template!
	 * @return rendered template
	 */
	public String renderTemplate() {
		Logger log = Logger.getLogger("VHostTemplateCreator");
		try {
			// FIXME: Until we have a config and an absolute path to the template file, try some resource loaders:
			String filePath = getClass().getClassLoader().getResource(TEMPLATE).getPath();

			// first try failed, next try
			if (filePath == null) {
				filePath = ClassLoader.getSystemClassLoader().getResource(TEMPLATE).getPath();
			}

			// again next try. If this fails, we have no other option atm., but failing with a NPE!
			// TODO: Find a way to report this back to the caller of the method and therefore returning it to the gui
			if (filePath == null) {
				filePath = Thread.currentThread().getContextClassLoader().getResource(TEMPLATE).getPath();
			}

			String template = LocalFileUtils.readFileAsString(filePath);

			template = template.replace("$hostname$", getHostname());
			template = template.replace("$port$", getPort());
			template = template.replace("basedir", getBasedir());
			template = template.replace("$documentRoot$", getDocumentRoot());
			template = template.replace("$directoryConfig$", getDirectoryConfig());
			template = template.replace("$extraConfig$", getExtraConfig());
			log.debug("Rendered Template: " + template);
			return template;
		} catch (IOException e) {
			log.error("Error rendering template", e);
			return null;
		}
	}
}
