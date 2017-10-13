package com.obdsim.persistence.entities;

public class FuelTrimCommand extends MockObdCommand{

	@Override
	public String getResponse() {
		String responseHeader = prepareCommandResponse();
		Integer processedValue = transformValue();
		String stValue = putSpaces(Integer.toHexString(processedValue));

		return  responseHeader + stValue  + ">";
	}

	@Override
	protected Integer transformValue() {
		return ((Integer.valueOf(value) * 100) / 128 ) + 128;
	}
}
