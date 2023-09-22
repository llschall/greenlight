package org.senegas.trafficlight.view;

import org.senegas.trafficlight.TrafficLightApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.net.URL;

/**
 * Specialized JFrame with system tray icon
 * see https://docs.oracle.com/javase/tutorial/uiswing/misc/systemtray.html
 */
public class TrafficLightFrame extends JFrame {
    TrayIcon trayIcon;
    SystemTray tray;

    public TrafficLightFrame(String title) throws HeadlessException {
        super(title);
        createTrayIcon();
    }

    private void createTrayIcon() {
        if (! SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }

        this.tray = SystemTray.getSystemTray();
        this.trayIcon =
                new TrayIcon(createImage("/images/Traffic_lights_icon.gif", "tray icon"),
                        "TrafficLight",
                        createTrayIconPopupMenu());
        this.trayIcon.setImageAutoSize(true);

        addWindowStateListener(this::handleWindowState);
        setIconImage(createImage("/images/Traffic_lights_icon256.png", "icon"));
    }

    private void handleWindowState(WindowEvent e) {
        if (e.getNewState() == ICONIFIED) {
            try {
                this.tray.add(this.trayIcon);
                setVisible(false);
                System.out.println("added to SystemTray");
            } catch (AWTException ex) {
                System.out.println("TrayIcon could not be added.");
            }
        }
        if (e.getNewState() == 7) {
            try{
                this.tray.add(this.trayIcon);
                setVisible(false);
                System.out.println("added to SystemTray");
            } catch (AWTException ex) {
                System.out.println("TrayIcon could not be added.");
            }
        }
        if (e.getNewState() == MAXIMIZED_BOTH) {
            this.tray.remove(this.trayIcon);
            setVisible(true);
            System.out.println("TrayIcon could not be removed.");
        }
        if (e.getNewState() == NORMAL) {
            this.tray.remove(this.trayIcon);
            setVisible(true);
            System.out.println("TrayIcon could not be removed.");
        }
    }

    private PopupMenu createTrayIconPopupMenu() {
        final PopupMenu popup = new PopupMenu();

        MenuItem aboutItem = new MenuItem("About");
        MenuItem showItem = new MenuItem("Show");
        MenuItem exitItem = new MenuItem("Exit");

        popup.add(aboutItem);
        popup.add(showItem);
        popup.addSeparator();
        popup.add(exitItem);

        aboutItem.addActionListener(this::handleAboutActionListener);
        showItem.addActionListener(this::handleShowActionListener);
        exitItem.addActionListener(this::handleExitAction);

        return popup;
    }

    private void handleShowActionListener(ActionEvent actionEvent) {
        setVisible(true);
        setExtendedState(JFrame.NORMAL);
    }

    private void handleAboutActionListener(ActionEvent actionEvent) {
        JOptionPane.showMessageDialog(null, getTitle(), "About", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleExitAction(ActionEvent actionEvent) {
        this.tray.remove(this.trayIcon);
        System.exit(0);
    }

    protected static Image createImage(String path, String description) {
        URL imageURL = TrafficLightApp.class.getResource(path);
        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
}
