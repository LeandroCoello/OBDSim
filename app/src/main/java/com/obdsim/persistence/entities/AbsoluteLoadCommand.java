package com.obdsim.persistence.entities;

import android.text.TextUtils;

/**
 * Created by Leo on 14/10/2017.
 */

public class AbsoluteLoadCommand extends MassAirFlowCommand {

    public AbsoluteLoadCommand() {

        super("0143", "41 43 00 3E>", "Valor absoluto de carga", true);
    }

    public String parseResponse(){
        return value + " %";
    }

    public String setValue() {
        String res = getResponse().replaceAll("\\s","");
        res = res.substring(4,8);
        Float val = (Long.parseLong(res, 16) * 100f / 255f) ;
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
        if (TextUtils.isEmpty(val) || val.length() > 8) {
            return false;
        }

        try {
            va = Float.parseFloat(val);
        } catch (NumberFormatException e) {
            return false;
        }

        return va < 100;
    }
}
