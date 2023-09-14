package org.senegas.trafficlight.view;

import org.senegas.trafficlight.model.TrafficLightModel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Timer;
import java.util.TimerTask;

public class TrafficLightComponent extends JComponent {

    public static final int TRAFFIC_LIGHT_WIDTH = 50;
    public static final int GAP = 5;
    private TrafficLightModel model;
    private boolean blinkyRedLed = false;
    private boolean blinkyYellowLed = false;
    private boolean blinkyGreenLed = false;

    public TrafficLightComponent() {
        TimerTask task = new TimerTask() {
            private long previousRedMillis = 0;
            private long previousYellowMillis = 0;
            private long previousGreenMillis = 0;

            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                toogleRed(currentTime);
                toogleYellow(currentTime);
                toogleGreen(currentTime);
                repaint();
            }

            private void toogleRed(long currentTime) {
                if ((model != null) &&
                        (currentTime - previousRedMillis > model.getRedDelay())) {
                    blinkyRedLed = ! blinkyRedLed;
                    previousRedMillis = currentTime;
                }
            }

            private void toogleYellow(long currentTime) {
                if ((model != null) &&
                        (currentTime - previousYellowMillis > model.getYellowDelay())) {
                    blinkyYellowLed = ! blinkyYellowLed;
                    previousYellowMillis = currentTime;
                }
            }

            private void toogleGreen(long currentTime) {
                if ((model != null) &&
                        (currentTime - previousGreenMillis > model.getGreenDelay())) {
                    blinkyGreenLed = ! blinkyGreenLed;
                    previousGreenMillis = currentTime;
                }
            }
        };
        Timer timer = new Timer("Blinky timer");
        timer.scheduleAtFixedRate(task, 0, 250);
    }

    public void setModel(TrafficLightModel model) {
        this.model = model;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        drawTrafficLight(g2);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(TRAFFIC_LIGHT_WIDTH, TRAFFIC_LIGHT_WIDTH * 3 + 15);
    }

    private void drawTrafficLight(Graphics2D g2) {
        drawTrafficLightBackGround(g2, 0, 0);
        drawTrafficLightBulbs(g2, 0, 0);
    }

    private void drawTrafficLightBulbs(Graphics2D g2, int xLeft, int yTop) {
        drawRedBulb(g2, xLeft, yTop);
        drawYellowBulb(g2, xLeft, yTop);
        drawGreenBulb(g2, xLeft, yTop);
    }

    private void drawTrafficLightBackGround(Graphics2D g2, int xLeft, int yTop) {
        Rectangle box = new Rectangle(xLeft, yTop, TRAFFIC_LIGHT_WIDTH, 3 * TRAFFIC_LIGHT_WIDTH - 2 * GAP);
        g2.setColor(Color.BLACK);
        g2.fill(box);
    }

    private void drawRedBulb(Graphics2D g2, int xLeft, int yTop) {
        Ellipse2D.Double redBulb = new Ellipse2D.Double(xLeft + GAP, yTop + GAP, TRAFFIC_LIGHT_WIDTH - 2 * GAP, TRAFFIC_LIGHT_WIDTH - 2 * GAP);
        boolean isRedOn = model.isRedOn();
        if (model.getRedDelay() > 0) {
            isRedOn = blinkyRedLed;
        }
        g2.setColor(isRedOn ? Color.RED.brighter() : Color.RED.darker());
        g2.fill(redBulb);
    }

    private void drawYellowBulb(Graphics2D g2, int xLeft, int yTop) {
        Ellipse2D.Double YellowBulb = new Ellipse2D.Double(xLeft + GAP, yTop + TRAFFIC_LIGHT_WIDTH, TRAFFIC_LIGHT_WIDTH - 2 * GAP, TRAFFIC_LIGHT_WIDTH - 2 * GAP);
        boolean isYellowOn = model.isYellowOn();
        if (model.getYellowDelay() > 0) {
            isYellowOn = blinkyYellowLed;
        }
        g2.setColor(isYellowOn ? Color.YELLOW.brighter() : Color.YELLOW.darker());
        g2.fill(YellowBulb);
    }

    private void drawGreenBulb(Graphics2D g2, int xLeft, int yTop) {
        Ellipse2D.Double greenBulb = new Ellipse2D.Double(xLeft + GAP, yTop + 2 * TRAFFIC_LIGHT_WIDTH - GAP, TRAFFIC_LIGHT_WIDTH - 2 * GAP, TRAFFIC_LIGHT_WIDTH - 2 * GAP);
        boolean isGreenOn = model.isGreenOn();
        if (model.getGreenDelay() > 0) {
            isGreenOn = blinkyGreenLed;
        }
        g2.setColor(isGreenOn ? Color.GREEN.brighter() : Color.GREEN.darker());
        g2.fill(greenBulb);
    }
}
