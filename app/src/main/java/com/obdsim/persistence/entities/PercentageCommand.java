package com.obdsim.persistence.entities;

public class PercentageCommand extends MockObdCommand{

	@Override
	public String getResponse() {
		String responseHeader = prepareCommandResponse();
		Integer processedValue = transformValue();
		String stValue = putSpaces(Integer.toHexString(processedValue));

		return  responseHeader + stValue  + ">";
	}
	
	public Integer transformValue() {
		Integer val = Integer.valueOf(value);
		return (val * 255) / 100;
		
	}
}
