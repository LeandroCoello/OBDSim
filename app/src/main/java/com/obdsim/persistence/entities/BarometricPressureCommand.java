package com.obdsim.persistence.entities;

/**
 * Created by Leo on 13/10/2017.
 */

public class BarometricPressureCommand extends SpeedCommand {

    public BarometricPressureCommand() {
        super("0133", "41 33 96>", "Presión barométrica", true);
    }

    @Override
    public String parseResponse(){
        return value + " kPa";
    }
}
