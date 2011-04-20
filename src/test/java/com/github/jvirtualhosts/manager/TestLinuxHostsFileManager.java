package com.github.jvirtualhosts.manager;

import com.github.jvirtualhosts.management.dns.LinuxHostsFileManager;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the hosts file manager.
 *
 * @author Mario Mueller<mario.mueller.work@gmail.com>
 */
public class TestLinuxHostsFileManager {

	@Test
	public void testReadFileAtEnd() throws URISyntaxException {
		LinuxHostsFileManager lhfm = new LinuxHostsFileManager();

		URL hosts = ClassLoader.getSystemClassLoader().getResource("hosts-at-end");
		assertNotNull("hosts file URL is null", hosts);

		File hostFile = new File(hosts.toURI());
		assertTrue("Fixture file is not readable", hostFile.canRead());

		int numberOfHosts = lhfm.readHostsFileData(hostFile);
		assertTrue("Expected number of hosts is 2, found " + numberOfHosts, numberOfHosts == 2);
	}

	@Test
	public void testReadFileInMiddle() throws URISyntaxException {
		LinuxHostsFileManager lhfm = new LinuxHostsFileManager();

		URL hosts = ClassLoader.getSystemClassLoader().getResource("hosts-in-middle");
		assertNotNull("hosts file URL is null", hosts);

		File hostFile = new File(hosts.toURI());
		assertTrue("Fixture file is not readable", hostFile.canRead());

		int numberOfHosts = lhfm.readHostsFileData(hostFile);
		assertTrue("Expected number of hosts is 2, found " + numberOfHosts, numberOfHosts == 2);
	}

	@Test
	public void testHostnamesParsedCorrectly() throws URISyntaxException {
		LinuxHostsFileManager lhfm = new LinuxHostsFileManager();

		URL hosts = ClassLoader.getSystemClassLoader().getResource("hosts-at-end");
		assertNotNull("hosts file URL is null", hosts);

		File hostFile = new File(hosts.toURI());
		assertTrue("Fixture file is not readable", hostFile.canRead());

		int numberOfHosts = lhfm.readHostsFileData(hostFile);
		assertTrue("Expected number of hosts is 2, found " + numberOfHosts, numberOfHosts == 2);
		assertTrue("Expected hostname pimcore.mm not found", lhfm.getPresentHostnames().contains("pimcore.mm"));
	}

	@Test
	public void testHostnamesAdditionCorrectly() throws URISyntaxException, IOException {
		LinuxHostsFileManager lhfm = new LinuxHostsFileManager();

		URL hosts = ClassLoader.getSystemClassLoader().getResource("hosts-at-end");
		assertNotNull("hosts file URL is null", hosts);

		File hostFile = new File(hosts.toURI());
		assertTrue("Fixture file is not readable", hostFile.canRead());

		int numberOfHosts = lhfm.readHostsFileData(hostFile);
		assertTrue("Expected number of hosts is 2, found " + numberOfHosts, numberOfHosts == 2);
		assertTrue("Expected hostname pimcore.mm not found", lhfm.getPresentHostnames().contains("pimcore.mm"));

		lhfm.addNewHostname("dummy.site");
		File newFile = File.createTempFile("tempHostFile", "tempHostFile");
		lhfm.saveHostFile(newFile);

		LinuxHostsFileManager testFileManager = new LinuxHostsFileManager();
		int numberOfNewHosts = testFileManager.readHostsFileData(newFile);
		assertTrue("Expected number of hosts is 3, found " + numberOfNewHosts, numberOfNewHosts == 3);
		assertTrue("Expected hostname pimcore.mm not found", testFileManager.getPresentHostnames().contains("dummy.site"));
	}

	@Test
	public void testHostnamesDeletedCorrectly() throws URISyntaxException, IOException {
		LinuxHostsFileManager lhfm = new LinuxHostsFileManager();

		URL hosts = ClassLoader.getSystemClassLoader().getResource("hosts-at-end");
		assertNotNull("hosts file URL is null", hosts);

		File hostFile = new File(hosts.toURI());
		assertTrue("Fixture file is not readable", hostFile.canRead());

		int numberOfHosts = lhfm.readHostsFileData(hostFile);
		assertTrue("Expected number of hosts is 2, found " + numberOfHosts, numberOfHosts == 2);
		assertTrue("Expected hostname pimcore.mm not found", lhfm.getPresentHostnames().contains("pimcore.mm"));

		lhfm.removeHostname("pimcore.mm");
		File newFile = File.createTempFile("tempHostFile", "tempHostFile");
		lhfm.saveHostFile(newFile);

		LinuxHostsFileManager testFileManager = new LinuxHostsFileManager();
		int numberOfNewHosts = testFileManager.readHostsFileData(newFile);
		assertTrue("Expected number of hosts is 1, found " + numberOfNewHosts, numberOfNewHosts == 1);
		assertFalse("Unexpected hostname pimcore.mm found", testFileManager.getPresentHostnames().contains("pimcore.mm"));
	}
}
