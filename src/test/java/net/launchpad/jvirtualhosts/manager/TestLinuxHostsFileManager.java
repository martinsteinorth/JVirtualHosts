package net.launchpad.jvirtualhosts.manager;

import net.launchpad.jvirtualhosts.management.LinuxHostsFileManager;
import org.junit.Test;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: mario
 * Date: 30.03.11
 * Time: 00:19
 * To change this template use File | Settings | File Templates.
 */
public class TestLinuxHostsFileManager {

	@Test
	public void testReadFile() {
		LinuxHostsFileManager lhfm = new LinuxHostsFileManager();
		URL hosts = getClass().getClassLoader().getResource("hosts");
		File hostFile = new File("hosts");

		lhfm.readHostsFileData(hostFile);
		assertTrue(lhfm.getPresentHostnames().size()> 0);
	}
}
