package net.launchpad.jvirtualhosts.manager;

import net.launchpad.jvirtualhosts.management.apache.VirtualHostEntry;
import net.launchpad.jvirtualhosts.management.apache.VirtualHostParser;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by IntelliJ IDEA.
 * User: mario
 * Date: 30.03.11
 * Time: 12:33
 * To change this template use File | Settings | File Templates.
 */
public class TestVirtualHostParser {
	@Test
	public void testHostnamesParsedCorrectly() throws URISyntaxException, IOException {
		URL hosts = ClassLoader.getSystemClassLoader().getResource("vhost-test1");
		assertNotNull("hosts file URL is null", hosts);

		VirtualHostEntry vhost = VirtualHostParser.parse(hosts.getPath());

		assertEquals("Port is not correct", "80", vhost.getPort());
		assertEquals("Hostname is not correct", "www.test.de", vhost.getHostname());
		assertEquals("Basedir is not correct", "/opt/var/www", vhost.getBasedir());
		assertEquals("DocumentRoot is not correct", "/opt/var/www", vhost.getDocumentRoot());
	}
}
