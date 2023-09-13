/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.senegas.trafficlight;

import org.senegas.trafficlight.view.TrafficLightPanel;

import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;

public class TrafficLightApp {

    public static final String TITLE = "Traffic Light App";
    public static final String VERSION = "0.4.0";

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new TrafficLightApp().create());
    }

    private void create() {
        final JFrame f = new JFrame(MessageFormat.format("{0} v{1}", TITLE, VERSION));
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.add(new TrafficLightPanel());
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
