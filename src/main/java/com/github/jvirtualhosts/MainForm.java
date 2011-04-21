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
package com.github.jvirtualhosts;

import com.github.jvirtualhosts.management.apache.VirtualHostEntry;
import com.github.jvirtualhosts.management.apache.VirtualHostManager;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.io.IOException;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: mario
 * Date: 29.03.11
 * Time: 11:34
 * To change this template use File | Settings | File Templates.
 */
public class MainForm {
    private JTabbedPane applicationTabs;
    private JPanel applicationPanel;
    private JList hostListEnabledTab;
    private JTabbedPane configTabsEnabledTab;
    private JTextArea textArea1;
    private JButton saveButtonEnabledPanel;
    private JButton disableButtonEnabledPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JButton exitButton;
    private JButton saveButton;
    private JButton restartApache2Button;

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("JVirtualHosts");
        frame.setContentPane(new MainForm().applicationPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setAlwaysOnTop(false);
    }

    public MainForm() {
        initVHostListing();
    }

    private void initVHostListing() {
        VirtualHostManager vhm = VirtualHostManager.getInstance();
        Logger log = Logger.getLogger("DataInitializer");
        try {

            log.info("Parsing available vHosts");

            vhm.parseDirectoryContents("/etc/apache2/sites-available");
            Vector<VirtualHostEntry> vhostList = vhm.getHostList();
            log.info("Found " + vhostList.size() + " hosts");

            log.info("Setting data to view list component");
            hostListEnabledTab.setListData(vhostList);

        } catch (IOException e) {
            log.fatal("Could not parse vhosts", e);
        }

    }
}
