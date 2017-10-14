package com.obdsim.persistence.entities;

/**
 * Created by Leo on 13/10/2017.
 */

public class IntakeManifoldPressureCommand extends SpeedCommand {


    public IntakeManifoldPressureCommand() {
        super("010B", "41 0B 99>", "Presión absoluta del colector de admisión", true);
    }

    @Override
    public String parseResponse(){
        return value + " kPa";
    }
}
