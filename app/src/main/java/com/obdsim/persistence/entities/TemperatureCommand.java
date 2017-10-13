package com.obdsim.persistence.entities;


public class TemperatureCommand extends MockObdCommand{

	@Override
	public String getResponse() {
		String responseHeader = prepareCommandResponse();
		Integer processedValue = transformValue();
		String stValue = putSpaces(Integer.toHexString(processedValue));

		return  responseHeader + stValue  + ">";
	}
	public Integer transformValue() {
		return Integer.valueOf(value) + 40;
	}
}
