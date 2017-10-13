package com.obdsim.persistence.entities;

import android.text.TextUtils;

public class RPMCommand extends MockObdCommand{

    public RPMCommand(String cmd, String response, String desc, Boolean bool) {
        super(cmd, response, desc, bool);
    }

    public RPMCommand(int _id, String cmd, String response, Boolean stateFlag, String desc) {
        super(_id, cmd, response, stateFlag, desc);
    }

    public RPMCommand() {
    }

    public String generateResponse() {
		String responseHeader = prepareCommandResponse();
		Integer processedValue = transformValue();
		String stValue = putSpaces(Integer.toHexString(processedValue)).toUpperCase();
        String res = responseHeader + stValue  + ">";
		return  res;
	}

	public String parseResponse(){
       return value + " RPM";
    }

    public String setValue() {
        String res = getResponse().replaceAll("\\s","");
        res = res.substring(4,8);
        Long val = Long.parseLong(res, 16) / 4;
        value = val.toString();
        return value;
    }
	
	public Integer transformValue() {
		return Integer.valueOf(value) * 4;
	}

	public Boolean validateInput(String val){
        if (TextUtils.isEmpty(val)) {
            return false;
        }

        return TextUtils.isDigitsOnly(val);
    }
	
}
