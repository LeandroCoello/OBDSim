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

    public String setValue() {
        String res = getResponse().replaceAll("\\s","");
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
