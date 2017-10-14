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
        res = res.substring(4,6);
        Long val = Long.parseLong(res, 16);
        value = val.toString();
        return value;
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
