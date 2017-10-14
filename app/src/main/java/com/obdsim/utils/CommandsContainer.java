package com.obdsim.utils;

import com.obdsim.persistence.entities.AbsoluteLoadCommand;
import com.obdsim.persistence.entities.AirIntakeTemperatureCommand;
import com.obdsim.persistence.entities.AmbientAirTemperatureCommand;
import com.obdsim.persistence.entities.BarometricPressureCommand;
import com.obdsim.persistence.entities.ConsumptionRateCommand;
import com.obdsim.persistence.entities.DistanceMILOnCommand;
import com.obdsim.persistence.entities.EngineCoolantTemperatureCommand;
import com.obdsim.persistence.entities.AirFuelRatioCommand;
import com.obdsim.persistence.entities.FuelLevelCommand;
import com.obdsim.persistence.entities.FuelPressureCommand;
import com.obdsim.persistence.entities.FuelRailPressureCommand;
import com.obdsim.persistence.entities.FuelTrimLTB1;
import com.obdsim.persistence.entities.FuelTrimLTB2;
import com.obdsim.persistence.entities.FuelTrimSTB1;
import com.obdsim.persistence.entities.FuelTrimSTB2;
import com.obdsim.persistence.entities.IntakeManifoldPressureCommand;
import com.obdsim.persistence.entities.LoadCommand;
import com.obdsim.persistence.entities.MassAirFlowCommand;
import com.obdsim.persistence.entities.MockObdCommand;
import com.obdsim.persistence.entities.ModuleVoltageCommand;
import com.obdsim.persistence.entities.OilTempCommand;
import com.obdsim.persistence.entities.RPMCommand;
import com.obdsim.persistence.entities.RuntimeCommand;
import com.obdsim.persistence.entities.SpeedCommand;
import com.obdsim.persistence.entities.ThrottlePositionCommand;
import com.obdsim.persistence.entities.TimingAdvanceCommand;
import com.obdsim.persistence.entities.WidebandAirFuelRatioCommand;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Leo on 20/6/2017.
 */

public class CommandsContainer {

    ArrayList<MockObdCommand> commandList = new ArrayList<>();
    private static CommandsContainer instance = null;
    private LinkedHashMap<String, Class<? extends MockObdCommand>> map = new LinkedHashMap<String, Class<? extends MockObdCommand>>();


    private CommandsContainer() {
        setCommands();
        setMapper();
    }

    private void setCommands() {

        // Initialize
        commandList.add(new MockObdCommand("ATZ", "ELM327 v1.3a\nOK>"));
//        commandList.add(new MockObdCommand("ATZ", "OK>"));
        commandList.add(new MockObdCommand("ATE0", "OK>"));
        commandList.add(new MockObdCommand("ATE1", "OK>"));
        commandList.add(new MockObdCommand("ATL0", "OK>"));
        commandList.add(new MockObdCommand("ATM0", "OK>"));
        commandList.add(new MockObdCommand("ATS0", "OK>"));
        commandList.add(new MockObdCommand("ATST3e", "OK>"));
        commandList.add(new MockObdCommand("ATSP0", "OK>"));
        commandList.add(new MockObdCommand("ATSP1", "OK>"));
        commandList.add(new MockObdCommand("ATSP2", "OK>"));
        commandList.add(new MockObdCommand("ATSP3", "OK>"));
        commandList.add(new MockObdCommand("ATSP4", "OK>"));
        commandList.add(new MockObdCommand("ATSP5", "OK>"));
        commandList.add(new MockObdCommand("ATSP6", "OK>"));
        commandList.add(new MockObdCommand("ATSP7", "OK>"));
        commandList.add(new MockObdCommand("ATSP8", "OK>"));
        commandList.add(new MockObdCommand("ATSP9", "OK>"));
        commandList.add(new MockObdCommand("AT@1", "OBDII to RS232 Interpreter\nOK>"));
        commandList.add(new MockObdCommand("ATI", "ELM327 v1.3a\nOK>"));
        commandList.add(new MockObdCommand("ATH0", "OK>"));
        commandList.add(new MockObdCommand("ATH1", "OK>"));
        commandList.add(new MockObdCommand("ATAT1", "OK>"));
        commandList.add(new MockObdCommand("ATDPN", "A6>"));

        //Available pids

        commandList.add(new MockObdCommand("0100", "01 00 FF FF FF FF>"));
        commandList.add(new MockObdCommand("0120", "01 20 FF FF FF FF>"));
        commandList.add(new MockObdCommand("0140", "01 40 FF FF FF FF>"));
        commandList.add(new MockObdCommand("0160", "01 60 FF FF FF FF>"));
        commandList.add(new MockObdCommand("0180", "01 80 FF FF FF F0>"));

        // Control

//        commandList.add(new MockObdCommand("0146", "41 46 50>"));
        commandList.add(new AmbientAirTemperatureCommand());
//        commandList.add(new MockObdCommand("0142", "41 42 0F 0F>"));
        commandList.add(new ModuleVoltageCommand());
//        commandList.add(new MockObdCommand("0143", "41 43 64 FA>"));
        commandList.add(new AbsoluteLoadCommand());
//        commandList.add(new MockObdCommand("0144", "41 44 64 FF>"));
        commandList.add(new AirFuelRatioCommand());
//        commandList.add(new MockObdCommand("0121", "41 21 10 00>"));
        commandList.add(new MockObdCommand("0121", "41 21 10 00>"));
        commandList.add(new DistanceMILOnCommand());
//        commandList.add(new MockObdCommand("010E", "41 0E 84>"));
        commandList.add(new TimingAdvanceCommand());
        commandList.add(new MockObdCommand("03", "43 05 7F 01 26 D3 97>"));
        commandList.add(new MockObdCommand("0902", "31 48 47 42 48 34 31 4A 58 4D 4E 31 30 39 31 38 36>"));

        // Engine
//        commandList.add(new MockObdCommand("0104", "41 04 78>"));
        commandList.add(new LoadCommand());
//        commandList.add(new MockObdCommand("010C", "41 0C 32 96>"));
        commandList.add(new RPMCommand());
//        commandList.add(new MockObdCommand("011F", "41 0F 10 14>"));
        commandList.add(new RuntimeCommand());
//        commandList.add(new MockObdCommand("0110", "41 10 64 64>"));
        commandList.add(new MassAirFlowCommand());
//        commandList.add(new MockObdCommand("0111", "41 11 96>"));
        commandList.add(new ThrottlePositionCommand());

        // Fuel
        commandList.add(new MockObdCommand("0151", "41 51 01>"));
//        commandList.add(new MockObdCommand("015E", "41 5E 64 FA>"));
        commandList.add(new ConsumptionRateCommand());
//        commandList.add(new MockObdCommand("012F", "41 5E 64>"));
        commandList.add(new FuelLevelCommand());
//        commandList.add(new MockObdCommand("0106", "41 06 B4>"));
        commandList.add(new FuelTrimSTB1());
//        commandList.add(new MockObdCommand("0107", "41 07 B4>"));
        commandList.add(new FuelTrimLTB1());
//        commandList.add(new MockObdCommand("0108", "41 08 B4>"));
        commandList.add(new FuelTrimSTB2());
//        commandList.add(new MockObdCommand("0109", "41 09 B4>"));
        commandList.add(new FuelTrimLTB2());
//        commandList.add(new MockObdCommand("0134", "41 34 34 87>"));
        commandList.add(new WidebandAirFuelRatioCommand());
//        commandList.add(new MockObdCommand("015C", "41 5C 64>"));
        commandList.add(new OilTempCommand());

        // Pressure
//        commandList.add(new MockObdCommand("0133", "41 33 96>"));
        commandList.add(new BarometricPressureCommand());
//        commandList.add(new MockObdCommand("010A", "41 0A 97>"));
        commandList.add(new FuelPressureCommand());
//        commandList.add(new MockObdCommand("0123", "41 23 98 15>"));
        commandList.add(new FuelRailPressureCommand());
//        commandList.add(new MockObdCommand("010B", "41 0B 99>"));
        commandList.add(new IntakeManifoldPressureCommand());

        // Temperature
//        commandList.add(new MockObdCommand("010F", "41 0F A0>"));
        commandList.add(new AirIntakeTemperatureCommand());
//        commandList.add(new MockObdCommand("0105", "41 05 A1>"));
        commandList.add(new EngineCoolantTemperatureCommand());

        // Misc
//        commandList.add(new MockObdCommand("010D", "41 0D A0>"));
        commandList.add(new SpeedCommand());

    }

    public void setMapper() {

        for (MockObdCommand cmd: commandList) {
            if (cmd.getStateFlag()) {
                map.put(cmd.getCmd(), cmd.getClass());
            }
        }

    }
    public static CommandsContainer getInstance() {
        if(instance == null) {
            instance = new CommandsContainer();
        }
        return instance;
    }

    public LinkedHashMap<String, Class<? extends MockObdCommand>> getMap() {
        return map;
    }

    public ArrayList<MockObdCommand> getCommandList() {
        return commandList;
    }
}
