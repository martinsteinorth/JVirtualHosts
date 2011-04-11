package net.launchpad.jvirtualhosts.manager;

import net.launchpad.jvirtualhosts.management.apache.VirtualHostEntry;
import net.launchpad.jvirtualhosts.management.apache.VirtualHostManager;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * User: mmueller
 * Date: 08.04.11
 * Time: 09:22
 */
public class TestVirtualHostManager {

    private File tempPath;

    public void setUp() {
        tempPath = new File(System.getProperty("java.io.tmpdir"));
    }

    @Test
    public void testDirectoryReader() throws IOException {
        File directory = new File(getClass().getResource("../../../../").getPath());

        VirtualHostManager vhm = VirtualHostManager.getInstance();
        vhm.setOverrideApacheConfPath(directory.getAbsolutePath() + "/hosts-available");
        vhm.setOverrideApacheEnabledPath(directory.getAbsolutePath() + "/hosts-enabled");
        vhm.parseDirectoryContents(directory.getAbsolutePath() + "/hosts-available");

        assertEquals("Number of found hosts is expected to be 1, found " + vhm.getHostList().size(), 1, vhm.getHostList().size());
        VirtualHostManager.clearInstance();
    }

    @Test
    public void testHostEnabled() throws IOException {
        File directory = new File(getClass().getResource("../../../../").getPath());

        VirtualHostManager vhm = VirtualHostManager.getInstance();
        vhm.setOverrideApacheConfPath(directory.getAbsolutePath() + "/hosts-available");
        vhm.setOverrideApacheEnabledPath(directory.getAbsolutePath() + "/hosts-enabled");
        vhm.parseDirectoryContents(directory.getAbsolutePath() + "/hosts-available");

        List<VirtualHostEntry> entryList =  vhm.getHostList();
        VirtualHostEntry entry = entryList.get(0);
        assertTrue("Host is expected to be enabled, but is disabled", vhm.isVirtualHostEnabled(entry));
        VirtualHostManager.clearInstance();
    }

    @Test
    public void testHostManipulation() throws IOException {
        File directory = new File(getClass().getResource("../../../../").getPath());

        VirtualHostManager vhm = VirtualHostManager.getInstance();
        vhm.setOverrideApacheConfPath(directory.getAbsolutePath() + "/hosts-available");
        vhm.setOverrideApacheEnabledPath(directory.getAbsolutePath() + "/hosts-enabled");
        vhm.parseDirectoryContents(directory.getAbsolutePath() + "/hosts-available");

        List<VirtualHostEntry> entryList =  vhm.getHostList();
        VirtualHostEntry entry = entryList.get(0);
        
        assertTrue("Host is expected to be enabled, but is disabled", vhm.isVirtualHostEnabled(entry));
        assertTrue("Disabling the host failed.", vhm.disableVirtualHost(entry));
        assertTrue("Enabling the host failed.", vhm.enableVirtualHost(entry));
        VirtualHostManager.clearInstance();
    }
}
