package org.senegas.trafficlight.view;

import org.llschall.ardwloop.ArdwloopStarter;
import org.llschall.ardwloop.IArdwConfig;
import org.llschall.ardwloop.IArdwProgram;
import org.llschall.ardwloop.structure.StructureTimer;
import org.llschall.ardwloop.value.ValueMap;
import org.senegas.trafficlight.model.TrafficLightModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SerialMessageEmitter implements IArdwProgram, PropertyChangeListener {
    private static final Logger LOGGER = Logger.getLogger(SerialMessageEmitter.class.getName());

    private TrafficLightModel model;

    boolean isRedOn = false;
    boolean isYellowOn = false;
    boolean isGreenOn = false;

    public SerialMessageEmitter() {
        initSerialPort();
    }

    public void setModel(TrafficLightModel model) {
        assert model != null : "model should not be null";
        this.model = model;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        isRedOn = model.isRedOn();
        isYellowOn = model.isYellowOn();
        isGreenOn = model.isGreenOn();
   }

    private void initSerialPort() {
        ArdwloopStarter.get().start(this, IArdwConfig.BAUD_19200);
        LOGGER.log(Level.INFO, "Ardwloop started.");
    }

    @Override
    public ValueMap ardwSetup(ValueMap setupData) {
        return new ValueMap();
    }

    @Override
    public ValueMap ardwLoop(ValueMap loopData) {

        StructureTimer.get().delayMs(99);

        int ax = model.isRedOn() ? 1 : 0;
        int ay = model.isYellowOn() ? 1 : 0;
        int az = model.isGreenOn() ? 1 : 0;

        return new ValueMap(0,0,ax,ay,az);
    }
}
