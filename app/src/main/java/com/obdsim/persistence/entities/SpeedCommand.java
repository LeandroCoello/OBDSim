package com.obdsim.persistence.entities;

import android.text.TextUtils;

public class SpeedCommand extends MockObdCommand {

    public SpeedCommand(String cmd, String response, String desc, Boolean bool) {
        super(cmd, response, desc, bool);
    }

    public SpeedCommand(int _id, String cmd, String response, Boolean stateFlag, String desc) {
        super(_id, cmd, response, stateFlag, desc);
    }

    public SpeedCommand() {
        super("010D", "41 0D A0>", "Velocidad del vehículo", true);
    }

    public String parseResponse(){
        return value + " km/h";
    }

    public String setValue() {
        String res = getResponse().replaceAll("\\s","");
        if (res.length() < 6) {
            value = "-1";
            return value;
        }
        res = res.substring(4,6);
        Long val = Long.parseLong(res, 16);
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
        return Integer.valueOf(value);
    }

    public Boolean validateInput(String val){
        if (TextUtils.isEmpty(val)) {
            return false;
        }

        return (TextUtils.isDigitsOnly(val) && Integer.valueOf(val) < 256);
    }

}
