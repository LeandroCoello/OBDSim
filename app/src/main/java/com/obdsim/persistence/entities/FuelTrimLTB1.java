package com.obdsim.persistence.entities;

/**
 * Created by Leo on 14/10/2017.
 */

public class FuelTrimLTB1 extends FuelLevelCommand {

    public FuelTrimLTB1() {
        super("0107", "41 07 B4>", "Ajuste de combustible a largo plazo—Banco 1", true);
    }

    public FuelTrimLTB1(String cmd, String response, String desc, Boolean isState) {
        super(cmd, response, desc, isState);
    }

    public String setValue() {
        String res = getResponse().replaceAll("\\s","");
        res = res.substring(4,6);
        Float val = (Long.parseLong(res, 16) - 128) * 100.0f / 128.0f;
        val = roundValue(val);
        value = val.toString();
        return value;
    }

    public Integer transformValue() {
        Float va = Float.parseFloat(value);
        va = (va * 128.0f / 100.0f) + 128;
        Integer intVa = Math.round(va);
        return intVa;
    }
}
