package com.obdsim.persistence.entities;

/**
 * Created by Leo on 14/10/2017.
 */

public class ConsumptionRateCommand extends MassAirFlowCommand {

    public ConsumptionRateCommand() {

        super("015E", "41 5E 00 78>", "Índice de consumo de combustible", true);
    }

    public String parseResponse(){
        return value + " L/h";
    }

    public String setValue() {
        String res = getResponse().replaceAll("\\s","");
        if (res.length() < 8) {
            value = "-1";
            return value;
        }
        res = res.substring(4,8);
        Float val = Long.parseLong(res, 16) * 0.05f ;
        val = roundValue(val);
        value = val.toString();
        return value;
    }

    public Integer transformValue() {
        Float va = Float.parseFloat(value);
        va = va / 0.05f;
        Integer intVa = Math.round(va);
        return intVa;
    }
}
