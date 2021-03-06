package com.obdsim.persistence.entities;

/**
 * Created by Leo on 14/10/2017.
 */

public class ModuleVoltageCommand extends MassAirFlowCommand {

    public ModuleVoltageCommand() {

        super("0142", "41 42 0F 0F>", "Voltaje del módulo de control", true);
    }

    public String parseResponse(){
        return value + " V";
    }

    public String generateResponse() {
        String responseHeader = prepareCommandResponse();
        Integer processedValue = transformValue();
        String stValue = validateZeros(Integer.toHexString(processedValue));
        stValue = putSpaces(stValue).toUpperCase();
        String res = responseHeader + stValue  + ">";
        return  res;
    }

    public String setValue() {
        String res = getResponse().replaceAll("\\s","");
        if (res.length() < 8) {
            value = "-1";
            return value;
        }
        res = res.substring(4,8);
        Float val = Long.parseLong(res, 16) / 1000.0f ;
        val = roundValue(val);
        value = val.toString();
        return value;
    }

    public Integer transformValue() {
        Float va = Float.parseFloat(value);
        va = va * 1000.0f;
        Integer intVa = Math.round(va);
        return intVa;
    }

}
