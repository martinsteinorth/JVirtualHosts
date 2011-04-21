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
import com.github.jvirtualhosts.management.apache.VirtualHostManager;
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
