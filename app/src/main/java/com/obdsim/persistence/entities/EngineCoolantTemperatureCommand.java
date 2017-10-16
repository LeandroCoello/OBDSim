package com.obdsim.persistence.entities;

import android.text.TextUtils;

/**
 * Created by Leo on 13/10/2017.
 */

public class EngineCoolantTemperatureCommand extends MockObdCommand {

    public EngineCoolantTemperatureCommand(String cmd, String response, String desc, Boolean bool) {
        super(cmd, response, desc, bool);
    }

    public EngineCoolantTemperatureCommand(int _id, String cmd, String response, Boolean stateFlag, String desc) {
        super(_id, cmd, response, stateFlag, desc);
    }

    public EngineCoolantTemperatureCommand() {
        super("0105", "41 05 A1>", "Temperatura del líquido refrigerante del motor", true);
    }

    public String parseResponse(){
        return value + " °C";
    }

    public String setValue() {
        String res = getResponse().replaceAll("\\s","");
        if (res.length() < 6) {
            value = "-1";
            return value;
        }
        res = res.substring(4,6);
        Long val = Long.parseLong(res, 16) - 40;
        value = val.toString();
        return value;
    }

    @Override
    public String validateZeros(String stValue) {
        Integer len = stValue.length();

        if (len == 1) {
            return "0"+stValue;
        }

        return stValue;
    }

    public Integer transformValue() {
        return Integer.valueOf(value) + 40;
    }

    public Boolean validateInput(String val) {
        if (TextUtils.isEmpty(val)) {
            return false;
        }
        Integer intVal = Integer.valueOf(val);
        return TextUtils.isDigitsOnly(val) && intVal < 256;
    }

}
