package net.launchpad.jvirtualhosts;

import net.launchpad.jvirtualhosts.management.apache.VirtualHostEntry;
import net.launchpad.jvirtualhosts.management.apache.VirtualHostManager;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.List;

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
		
		hostListEnabledTab.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent componentEvent) {
				super.componentShown(componentEvent);

				JList list = (JList) componentEvent.getComponent();

				VirtualHostManager vhm = new VirtualHostManager();
				try {
					vhm.parseDirectoryContents("/etc/apache2/sites-available");
					List<VirtualHostEntry> vhostList = vhm.getHostList();


					for (VirtualHostEntry vhost : vhostList) {
						((DefaultListModel)list.getModel()).addElement(vhost.getHostname());
					}

				} catch (IOException e) {

				}
			}
		});
	}
}
