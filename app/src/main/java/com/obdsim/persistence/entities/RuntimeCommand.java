package com.obdsim.persistence.entities;

/**
 * Created by Leo on 14/10/2017.
 */

public class RuntimeCommand extends RPMCommand {

    Long lValue = 0L;
    public RuntimeCommand() {
        super("011F", "41 1F 10 14>", "Tiempo en marcha del motor", true);
    }

    public RuntimeCommand(String cmd, String response, String desc, Boolean isState) {
        super(cmd, response, desc, isState);
    }

    public String parseResponse(){
        return value + " s ("+ formatSeconds() + ")";
    }

    public String setValue() {
        String res = getResponse().replaceAll("\\s","");
        if (res.length() < 8) {
            value = "-1";
            return value;
        }
        res = res.substring(4,8);
        Long val = Long.parseLong(res, 16);
        lValue = val;
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

        if (len == 1) {
            return "000"+stValue;
        }

        return stValue;
    }

    public Integer transformValue() {
        return Integer.valueOf(value);
    }

    public String formatSeconds() {
        lValue = Long.parseLong(value);
        Long h, m, s;
        String hour, minutes, secons;
        h = lValue / 3600;
        m = (lValue % 3600) / 60;
        s = lValue % 60;

        hour = addCero(h.toString());
        minutes = addCero(m.toString());
        secons = addCero(s.toString());

        return hour+":"+minutes+":"+secons;
    }

    public String addCero(String st) {
        return (st.length() ==1) ? "0"+st : st;
    }
}
