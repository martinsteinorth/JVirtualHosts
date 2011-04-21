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
package com.github.jvirtualhosts.manager;

import com.github.jvirtualhosts.management.apache.VirtualHostEntry;
import com.github.jvirtualhosts.management.apache.VirtualHostParser;
import com.github.jvirtualhosts.tool.ConnectionType;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for the Virtual Host Parser
 *
 * @author Mario Mueller<mario.mueller.work@gmail.com>
 */
public class TestVirtualHostParser {
	@Test
	public void testHostnamesParsedCorrectly() throws URISyntaxException, IOException {
		URL hosts = ClassLoader.getSystemClassLoader().getResource("vhost-test1");
		assertNotNull("hosts file URL is null", hosts);

        VirtualHostParser virtualHostParser = VirtualHostParser.factory(ConnectionType.LOCAL);
		VirtualHostEntry vhost = virtualHostParser.parse(hosts.getPath());

		assertEquals("Port is not correct", "80", vhost.getPort());
		assertEquals("Hostname is not correct", "www.test.de", vhost.getHostname());
		assertEquals("Basedir is not correct", "/opt/var/www", vhost.getBasedir());
		assertEquals("DocumentRoot is not correct", "/opt/var/www", vhost.getDocumentRoot());
	}
}
