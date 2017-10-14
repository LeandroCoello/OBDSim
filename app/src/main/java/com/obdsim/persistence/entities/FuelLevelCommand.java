package com.obdsim.persistence.entities;

import android.icu.math.MathContext;
import android.text.TextUtils;

/**
 * Created by Leo on 14/10/2017.
 */

public class FuelLevelCommand extends MockObdCommand  {

    public FuelLevelCommand(String cmd, String response, String desc, Boolean bool) {
        super(cmd, response, desc, bool);
    }

    public FuelLevelCommand(int _id, String cmd, String response, Boolean stateFlag, String desc) {
        super(_id, cmd, response, stateFlag, desc);
    }

    public FuelLevelCommand() {
        super("012F", "41 5E 64>", "Nivel de combustible", true);
    }

    public String parseResponse(){
        return value + " %";
    }

    public String setValue() {
        String res = getResponse().replaceAll("\\s","");
        res = res.substring(4,6);
        Float val = Long.parseLong(res, 16) / 2.55f;
        val = roundValue(val);
        value = val.toString();
        return value;
    }

    public Integer transformValue() {
        Float va = Float.parseFloat(value);
        va = va * 2.55f;
        Integer intVa = Math.round(va);
        return intVa;
    }

    public Boolean validateInput(String val){
        Float va;
        if (TextUtils.isEmpty(val) || val.length() > 4) {
            return false;
        }

        try {
            va = Float.parseFloat(val);
        } catch (NumberFormatException e) {
            return false;
        }

        return va < 256;
    }

    public Float roundValue(Float val) {
        val = (val * 10) + 0.50f;
        Long l = val.longValue();
        val = l /10.00f;
        return val;
    }

}
