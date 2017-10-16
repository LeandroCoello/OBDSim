package com.obdsim.persistence.entities;

/**
 * Created by Leo on 14/10/2017.
 */

public class WidebandAirFuelRatioCommand extends MassAirFlowCommand {

    public WidebandAirFuelRatioCommand() {

        super("0134", "41 34 34 87>", "Sensor de oxígeno 1", true);
    }

    public String parseResponse(){
        return value+":1 AFR";
    }

    public String setValue() {
        String res = getResponse().replaceAll("\\s","");
        if (res.length() < 8) {
            value = "-1";
            return value;
        }
        res = res.substring(4,8);
        Float val = ((Long.parseLong(res, 16) / 32768.0f) * 14.7f) ;
        val = roundValue(val);
        value = val.toString();
        return value;
    }

    public Float roundValue(Float val) {
        val = val * 1000 +0.5f;
        Long l = val.longValue();
        val = l /1000.0f;
        return val;
    }

    public Integer transformValue() {
        Float va = Float.parseFloat(value);
        va = va * 32768.0f / 14.7f;
        Integer intVa = Math.round(va);
        return intVa;
    }
}
