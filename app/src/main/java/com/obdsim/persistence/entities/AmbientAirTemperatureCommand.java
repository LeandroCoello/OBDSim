package com.obdsim.persistence.entities;

/**
 * Created by Leo on 13/10/2017.
 */

public class AmbientAirTemperatureCommand extends EngineCoolantTemperatureCommand {

    public AmbientAirTemperatureCommand() {
        super("0146", "41 46 50>", "Temperatura del aire del ambiente", true);
    }
}
