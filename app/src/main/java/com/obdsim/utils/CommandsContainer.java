package com.obdsim.utils;

import com.obdsim.persistence.entities.MockObdCommand;

import java.util.ArrayList;

/**
 * Created by Leo on 20/6/2017.
 */

public class CommandsContainer {

    ArrayList<MockObdCommand> commandList = new ArrayList<>();
    private static CommandsContainer instance = null;
    private static Integer speed = 160;
    private static Integer rpm = 3500;

    private CommandsContainer() {

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

        commandList.add(new MockObdCommand("0146", "41 46 50>"));
        commandList.add(new MockObdCommand("0142", "41 42 0F 0F>"));
        commandList.add(new MockObdCommand("0143", "41 43 64 FA>"));
        commandList.add(new MockObdCommand("0144", "41 44 64 FF>"));
        commandList.add(new MockObdCommand("0121", "41 21 10 00>"));
        commandList.add(new MockObdCommand("0101", "41 01 81>"));
        commandList.add(new MockObdCommand("010E", "41 0E 84>"));
        commandList.add(new MockObdCommand("03", "43 05 7F 01 26 D3 97>"));
        commandList.add(new MockObdCommand("0902", "31 48 47 42 48 34 31 4A 58 4D 4E 31 30 39 31 38 36>"));

        // Engine
        commandList.add(new MockObdCommand("0104", "41 04 78>"));
        commandList.add(new MockObdCommand("010C", "41 0C 32 96>"));
        commandList.add(new MockObdCommand("011F", "41 0F 10 14>"));
        commandList.add(new MockObdCommand("0110", "41 10 64 64>"));
        commandList.add(new MockObdCommand("0111", "41 11 96>"));

        // Fuel
        commandList.add(new MockObdCommand("0151", "41 51 01>"));
        commandList.add(new MockObdCommand("015E", "41 5E 64 FA>"));
        commandList.add(new MockObdCommand("012F", "41 5E 64>"));
        commandList.add(new MockObdCommand("0106", "41 06 B4>"));
        commandList.add(new MockObdCommand("0107", "41 07 B4>"));
        commandList.add(new MockObdCommand("0108", "41 08 B4>"));
        commandList.add(new MockObdCommand("0109", "41 09 B4>"));
        commandList.add(new MockObdCommand("0144", "41 44 34 87>"));
        commandList.add(new MockObdCommand("0134", "41 34 34 87>"));
        commandList.add(new MockObdCommand("015C", "41 5C 64>"));

        // Pressure
        commandList.add(new MockObdCommand("0133", "41 33 96>"));
        commandList.add(new MockObdCommand("010A", "41 0A 97>"));
        commandList.add(new MockObdCommand("0123", "41 23 98 15>"));
        commandList.add(new MockObdCommand("010B", "41 0B 99>"));

        // Temperature
        commandList.add(new MockObdCommand("010F", "41 0F A0>"));
        commandList.add(new MockObdCommand("0105", "41 05 A1>"));

        // Misc
        commandList.add(new MockObdCommand("010D", "41 0D A0>"));

    }

    public static CommandsContainer getInstance() {
        if(instance == null) {
            instance = new CommandsContainer();
        }
        return instance;
    }

    public ArrayList<MockObdCommand> getCommandList() {
        return commandList;
    }
}
