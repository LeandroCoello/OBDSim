package com.obdsim.persistence.entities;

/**
 * Created by Leo on 13/10/2017.
 */

public class AirIntakeTemperatureCommand extends EngineCoolantTemperatureCommand {

    public AirIntakeTemperatureCommand() {
        super("010F", "41 0F A0>", "Temperatura del aire del colector de admisión", true);
    }
}
