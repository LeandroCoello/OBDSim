package com.obdsim.persistence.entities;

public class FuelPressureCommand extends SpeedCommand {

	public FuelPressureCommand() {
		super("010A", "41 0A 97>", "Presión del combustible", true);
	}

	public String setValue() {
		String res = getResponse().replaceAll("\\s","");
		if (res.length() < 6) {
			value = "-1";
			return value;
		}
		res = res.substring(4,6);
		Long val = Long.parseLong(res, 16) * 3;
		value = val.toString();
		return value;
	}

	public String parseResponse(){
		return value + " kPa";
	}

	public Integer transformValue() {
		return Integer.valueOf(value)/3;
	}
}
