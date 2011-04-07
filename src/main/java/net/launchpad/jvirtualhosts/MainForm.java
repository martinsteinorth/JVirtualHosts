package net.launchpad.jvirtualhosts;

import net.launchpad.jvirtualhosts.management.apache.VirtualHostEntry;
import net.launchpad.jvirtualhosts.management.apache.VirtualHostManager;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.io.IOException;
import java.util.List;
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
        VirtualHostManager vhm = new VirtualHostManager();
        Logger log = Logger.getLogger("DataInitializer");
        try {

            log.info("Parsing available vHosts");

            vhm.parseDirectoryContents("/etc/apache2/sites-available");
            List<VirtualHostEntry> vhostList = vhm.getHostList();
            log.info("Found " + vhostList.size() + " hosts");

            Vector<VirtualHostEntry> vhostVector = new Vector<VirtualHostEntry>();
            log.debug("Created new vector");
            vhostVector.addAll(vhostList);
            log.debug("Vector aquired " + vhostVector.size() + " hosts");

            log.info("Setting data to view list component");
            hostListEnabledTab.setListData(vhostVector);

        } catch (IOException e) {
            log.fatal("Could not parse vhosts", e);
        }

    }
}
