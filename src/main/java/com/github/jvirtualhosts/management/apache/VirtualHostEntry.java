/**
 * Copyright 2011 Martin Steinorth <martin.steinorth@gmail.com>, Mario Mueller <mario.mueller.work@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jvirtualhosts.management.apache;

import com.github.jvirtualhosts.tool.ConnectionType;
import com.github.jvirtualhosts.tool.FileFactory;
import com.github.jvirtualhosts.tool.FileOperator;
import com.github.jvirtualhosts.tool.LocalFileUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Vector;

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
     * The type of connection to use
     */
    private ConnectionType type;

	/**
	 * The vhost template that should be used. This file has to be on classpath.
	 * @TODO make this more felxible through configuration (file or gui based?)
	 */
	private static final String TEMPLATE = "vhost.tpl";

    /**
     * Factory for vHost Entries
     * @param type
     * @return a connection-typed instance of a vhost
     */
    public static VirtualHostEntry factory(ConnectionType type) {
        VirtualHostEntry virtualHostEntry = new VirtualHostEntry();
        virtualHostEntry.type = type;
        return virtualHostEntry;
    }

    /**
     * Hostname getter
     * @return the virtual hostname
     */
	public String getHostname() {
		return hostname;
	}

    /**
     * Sets the virtual hostname
     * @param hostname
     */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

    /**
     * Port getter
     * @return the port as string
     */
	public String getPort() {
		return port;
	}

    /**
     * Sets the port as string!
     * @param port
     */
	public void setPort(String port) {
		this.port = port;
	}

    /**
     * DocRoot getter
     * @return The document root of the server
     */
	public String getDocumentRoot() {
		return documentRoot;
	}

    /**
     * Sets the document root of the sever
     * @param documentRoot
     */
	public void setDocumentRoot(String documentRoot) {
		this.documentRoot = documentRoot;
	}

    /**
     * Returns the basedir of the server, which might
     * be the same as the document root.
     * @return the path to the basedir as string
     */
	public String getBasedir() {
		return basedir;
	}

    /**
     * Sets the basedir as string. Remember, that the
     * document root must be located within the basedir's
     * child structure.
     * Like:
     *  - Basedir: /var/www/somecustomer
     *  - DocRoot: /var/www/somecustomer/subdomain.example.com/htdocs
     * @param basedir
     */
	public void setBasedir(String basedir) {
		this.basedir = basedir;
	}

    /**
     * Gets the full config for the basedir (Options, AllowOverride, etc.)
     * @return the full config stack for the basedir, separated by "\n"
     */
	public String getDirectoryConfig() {
		return directoryConfig;
	}

    /**
     * Sets the full config stack for the basedir (Options, AllowOverride, etc.)
     * @param directoryConfig the full config for the basedir separated by a newline (\n)
     */
	public void setDirectoryConfig(String directoryConfig) {
		this.directoryConfig = directoryConfig;
	}

    /**
     * Gets all the extra config lines
     * @return the configuration that has been added by the user. Put things like "<Location /example>\n"
     *          etc. in here.
     */
	public String getExtraConfig() {
		return extraConfig;
	}

    /**
     * Sets the extra config settings for the host.
     * @param extraConfig You can put something like Location tags, JKMount Settings
     *                      ruby env or passenger configs in here, separated by a newline char (\n)
     */
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

            FileOperator operator = FileFactory.factory(type);
			String template = operator.readFileAsString(filePath);

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
