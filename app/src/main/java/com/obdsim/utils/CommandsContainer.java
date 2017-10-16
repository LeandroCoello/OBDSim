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

        commandList.add(new MockObdCommand("0100", "41 00 FF FF FF FF>"));
        commandList.add(new MockObdCommand("0120", "41 20 FF FF FF FF>"));
        commandList.add(new MockObdCommand("0140", "41 40 FF FF FF FF>"));
        commandList.add(new MockObdCommand("0160", "41 60 FF FF FF FF>"));
        commandList.add(new MockObdCommand("0180", "41 80 FF FF FF F0>"));

        // Control

        commandList.add(new AmbientAirTemperatureCommand());
        commandList.add(new ModuleVoltageCommand());
        commandList.add(new AbsoluteLoadCommand());
        commandList.add(new AirFuelRatioCommand());
        commandList.add(new MockObdCommand("0121", "41 21 10 00>"));
        commandList.add(new DistanceMILOnCommand());
        commandList.add(new TimingAdvanceCommand());
        commandList.add(new MockObdCommand("03", "43 05 7F 01 26 D3 97>"));
        commandList.add(new MockObdCommand("0902", "31 48 47 42 48 34 31 4A 58 4D 4E 31 30 39 31 38 36>"));

        // Engine
        commandList.add(new LoadCommand());
        commandList.add(new RPMCommand());
        commandList.add(new RuntimeCommand());
        commandList.add(new MassAirFlowCommand());
        commandList.add(new ThrottlePositionCommand());

        // Fuel
        commandList.add(new MockObdCommand("0151", "41 51 01>"));
        commandList.add(new ConsumptionRateCommand());
        commandList.add(new FuelLevelCommand());
        commandList.add(new FuelTrimSTB1());
        commandList.add(new FuelTrimLTB1());
        commandList.add(new FuelTrimSTB2());
        commandList.add(new FuelTrimLTB2());
        commandList.add(new WidebandAirFuelRatioCommand());
        commandList.add(new OilTempCommand());

        // Pressure
        commandList.add(new BarometricPressureCommand());
        commandList.add(new FuelPressureCommand());
        commandList.add(new FuelRailPressureCommand());
        commandList.add(new IntakeManifoldPressureCommand());

        // Temperature
        commandList.add(new AirIntakeTemperatureCommand());
        commandList.add(new EngineCoolantTemperatureCommand());

        // Misc
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
