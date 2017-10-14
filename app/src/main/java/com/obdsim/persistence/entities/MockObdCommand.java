package com.obdsim.persistence.entities;

import android.text.TextUtils;

/**
 * Created by Leo on 20/6/2017.
 */

public class MockObdCommand {

    private Integer _id;
    private String cmd;
    private String response;
    private Boolean stateFlag = false;
    protected String value;
    protected String description;

    public MockObdCommand() {
    }

    public MockObdCommand(String cmd, String response) {
        this.cmd = cmd;
        this.response = response;
    }

    public MockObdCommand(Integer _id, String cmd, String response, String desc) {
        this._id = _id;
        this.cmd = cmd;
        this.response = response;
        this.description = desc;
        this.stateFlag = false;
    }

    public MockObdCommand(String cmd, String response, String desc, Boolean bool) {
        this.cmd = cmd;
        this.response = response;
        this.stateFlag = bool;
        this.description = desc;
    }

    public MockObdCommand(Integer _id, String cmd, String response, Boolean stateFlag, String description) {
        this._id = _id;
        this.cmd = cmd;
        this.response = response;
        this.stateFlag = stateFlag;
        this.description = description;
    }

    public String getResponse() {
        return response;
    }

    public String parseResponse(){
        return null;
    }

    public String setValue() {
        return value;
    }

    public String generateResponse() {
        String responseHeader = prepareCommandResponse();
        Integer processedValue = transformValue();
        String stValue = validateZeros(Integer.toHexString(processedValue));
        stValue = putSpaces(stValue).toUpperCase();
        String res = responseHeader + stValue  + ">";
        return  res;
    }

    public String validateZeros(String st) {
        return st;
    }

    public Boolean validateInput(String val) {
        if (TextUtils.isEmpty(val)) {
            return false;
        }
        return TextUtils.isDigitsOnly(val);
    }

    protected Integer transformValue() {
		return null;
	}

	public void setResponse(String response) {
        this.response = response;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }
    
    public Boolean getStateFlag() {
        return stateFlag;
    }

    public void setStateFlag(Boolean stateFlag) {
        this.stateFlag = stateFlag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String putSpaces(String st) {
    	return st.replaceAll("..(?=..)", "$0 ");
    }
    
    public String prepareCommandResponse() {
    	String spaceSt = putSpaces(cmd);
    	return "4"+spaceSt.substring(1)+" ";
    }
}
