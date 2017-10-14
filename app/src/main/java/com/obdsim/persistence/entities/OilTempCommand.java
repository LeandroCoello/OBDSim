package com.obdsim.persistence.entities;

/**
 * Created by Leo on 13/10/2017.
 */

public class OilTempCommand extends EngineCoolantTemperatureCommand {

    public OilTempCommand() {
        super("015C", "41 5C 64>", "Temperatura del aceite del motor", true);
    }
}
