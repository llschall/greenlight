/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.senegas.trafficlight;

import com.formdev.flatlaf.FlatIntelliJLaf;
import org.senegas.trafficlight.model.TrafficLightModel;
import org.senegas.trafficlight.view.TrafficLightFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class TrafficLightApp {

    private static final Logger LOGGER = Logger.getLogger(TrafficLightApp.class.getName());

    public static final String TITLE = "Green Light App";

    static {
        try {
            // Ensure the logs directory exists
            Files.createDirectories(Paths.get("logs"));

            // Load the custom logging configuration from resources
            InputStream loggingConfig =
                    TrafficLightApp.class.getClassLoader().getResourceAsStream("logging.properties");
            if (loggingConfig == null) {
                LOGGER.severe("Logging configuration file not found.");
                throw new RuntimeException("Logging configuration is required.");
            }
            LogManager.getLogManager().readConfiguration(loggingConfig);
        } catch (IOException e) {
            LOGGER.severe("Failed to load logging configuration: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        LOGGER.log(Level.INFO, TITLE + " has started.");

        EventQueue.invokeLater(() -> {
            new TrafficLightApp().create();
        });
    }

    private void create() {
        FlatIntelliJLaf.setup();

        final TrafficLightModel model = new TrafficLightModel();

        final JFrame f = new TrafficLightFrame(TITLE, model);

        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ((TrafficLightFrame)f).dispose();
            }
        });
        f.setPreferredSize(new Dimension(350, 180));
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
