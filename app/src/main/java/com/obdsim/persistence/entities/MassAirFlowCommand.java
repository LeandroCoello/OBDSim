package com.obdsim.persistence.entities;

import android.text.TextUtils;

/**
 * Created by Leo on 14/10/2017.
 */

public class MassAirFlowCommand extends FuelLevelCommand {

    public MassAirFlowCommand() {

        super("0110", "41 10 64 64>", "Velocidad del flujo del aire", true);
    }

    public MassAirFlowCommand(String cmd, String response, String desc, Boolean isState) {
        super(cmd, response, desc, isState);
    }
    public String parseResponse(){
        return value + " g/s";
    }

    public String setValue() {
        String res = getResponse().replaceAll("\\s","");
        res = res.substring(4,8);
        Float val = Long.parseLong(res, 16) / 100.0f ;
        val = roundValue(val);
        value = val.toString();
        return value;
    }

    @Override
    public String validateZeros(String stValue) {
        Integer len = stValue.length();

        if (len == 3) {
            return "0"+stValue;
        }

        if (len == 2) {
            return "00"+stValue;
        }

        return stValue;
    }

    public Integer transformValue() {
        Float va = Float.parseFloat(value);
        va = va * 100.0f;
        Integer intVa = Math.round(va);
        return intVa;
    }

    public Boolean validateInput(String val){
        Float va;
        if (TextUtils.isEmpty(val) || val.length() > 8) {
            return false;
        }

        try {
            va = Float.parseFloat(val);
        } catch (NumberFormatException e) {
            return false;
        }

        return va < 65536;
    }

}
