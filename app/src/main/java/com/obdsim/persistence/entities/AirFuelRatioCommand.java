package com.obdsim.persistence.entities;

/**
 * Created by Leo on 14/10/2017.
 */

public class AirFuelRatioCommand extends MassAirFlowCommand {

    public AirFuelRatioCommand() {

        super("0144", "41 44 64 FF>", "Relación combustible - aire", true);
    }

    public String parseResponse(){
        return value + ":1 AFR";
    }

    public String setValue() {
        String res = getResponse().replaceAll("\\s","");
        res = res.substring(4,8);
        Float val = Long.parseLong(res, 16) / 32768.0f * 14.7f;
        val = roundValue(val);
        value = val.toString();
        return value;
    }

    public Integer transformValue() {
        Float va = Float.parseFloat(value);
        va = va * 32768.0f / 14.7f;
        Integer intVa = Math.round(va);
        return intVa;
    }

}