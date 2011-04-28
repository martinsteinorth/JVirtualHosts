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

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Virtual Host Parser
 *
 * @author Mario Mueller<mario.mueller.work@gmail.com>
 */
public class VirtualHostParser {

    /**
     * The type of connection to be used
     */
    private ConnectionType type;

    /**
     * A factory to type the parser for a connection
     * @param type local or remote connection
     * @return the virtual host parser
     */
    public static VirtualHostParser factory(ConnectionType type) {
        VirtualHostParser virtualHostParser = new VirtualHostParser();
        virtualHostParser.type = type;
        return virtualHostParser;
    }

    /**
     * The main parser method
     *
     * @deprecated This parser method is still made for local parsing and is subject to be refactored
     * @param fullPath the full path to the file that should be read
     * @return a parsed and filled instance of a virtual host entry
     * @throws IOException
     */
	public VirtualHostEntry parse(String fullPath) throws IOException {
        FileOperator operator = FileFactory.factory(type);
		String vhostConfig = operator.readFileAsString(fullPath);
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
