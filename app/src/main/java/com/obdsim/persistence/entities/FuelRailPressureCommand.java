package com.obdsim.persistence.entities;

/**
 * Created by Leo on 14/10/2017.
 */

public class FuelRailPressureCommand extends RuntimeCommand {

    public FuelRailPressureCommand() {
        super("0123", "41 23 00 9B>", "Presión del medidor del tren de combustible", true);
    }

    public String parseResponse(){
        return value + " kPa";
    }

    public String setValue() {
        String res = getResponse().replaceAll("\\s","");
        if (res.length() < 8) {
            value = "-1";
            return value;
        }
        res = res.substring(4,8);
        Long val = Long.parseLong(res, 16) * 10;
        lValue = val;
        value = val.toString();
        return value;
    }

    public Integer transformValue() {
        return Integer.valueOf(value) / 10;
    }
}
