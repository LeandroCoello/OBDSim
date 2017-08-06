package com.obdsim;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Leo on 20/6/2017.
 */

public class CommandsContainer {

    LinkedHashMap<String,String> commandMap = new LinkedHashMap<String,String>();
    private static CommandsContainer instance = null;
    private static Integer speed = 160;
    private static Integer rpm = 3500;

    private CommandsContainer() {

        // Initialize
//        commandMap.put("ATZ", "OK>");
        commandMap.put("ATZ", "ELM327 v1.3a\nOK>");
        commandMap.put("ATE0", "OK>");
        commandMap.put("ATE1", "OK>");
        commandMap.put("ATL0", "OK>");
        commandMap.put("ATM0", "OK>");
        commandMap.put("ATS0", "OK>");
        commandMap.put("ATST3e", "OK>");
        commandMap.put("ATSP0", "OK>");
        commandMap.put("ATSP1", "OK>");
        commandMap.put("ATSP2", "OK>");
        commandMap.put("ATSP3", "OK>");
        commandMap.put("ATSP4", "OK>");
        commandMap.put("ATSP5", "OK>");
        commandMap.put("ATSP6", "OK>");
        commandMap.put("ATSP7", "OK>");
        commandMap.put("ATSP8", "OK>");
        commandMap.put("ATSP9", "OK>");
        commandMap.put("AT@1", "OBDII to RS232 Interpreter\nOK>");
        commandMap.put("ATI", "ELM327 v1.3a\nOK>");
        commandMap.put("ATH0", "OK>");
        commandMap.put("ATH1", "OK>");
        commandMap.put("ATAT1", "OK>");
        commandMap.put("ATDPN", "A6>");

        //Available pids

//        commandMap.put("01 00", "01 00 80 00 00 00>");
        commandMap.put("0100", "01 00 FF FF FF F0>");
        commandMap.put("0120", "01 20 FF FF FF F0>");
        commandMap.put("0140", "01 40 FF FF FF F0>");
        commandMap.put("0160", "01 60 FF FF FF F0>");
        commandMap.put("0180", "01 80 FF FF FF F0>");

        // Control

        commandMap.put("0146", "41 46 50>");
        commandMap.put("0142", "41 42 0F 0f>");
        commandMap.put("0144", "41 44 64 FF>");
        commandMap.put("0121", "41 21 10 00>");
        commandMap.put("0101", "41 01 84>");
        commandMap.put("010E", "41 0E 84>");
        commandMap.put("03", "43 01 33 00 00 00 00>");
        commandMap.put("0902", "31 48 47 42 48 34 31 4A 58 4D 4E 31 30 39 31 38 36>");

        // Engine
        commandMap.put("0104", "41 04 78>");
        commandMap.put("010C", "41 0C 32 96>");
        commandMap.put("011F", "41 0F 10 14>");
        commandMap.put("0110", "41 10 64 64>");
        commandMap.put("0111", "41 11 96>");

        // Fuel
        commandMap.put("0151", "41 51 01>");
        commandMap.put("015E", "41 5E 64 FA>");
        commandMap.put("012F", "41 5E 64>");
        commandMap.put("0106", "41 06 B4>");
        commandMap.put("0107", "41 07 B4>");
        commandMap.put("0108", "41 08 B4>");
        commandMap.put("0109", "41 09 B4>");
        commandMap.put("0144", "41 44 34 87>");
        commandMap.put("0134", "41 34 34 87>");
        commandMap.put("015C", "41 5C 64>");

        // Pressure
        commandMap.put("0133", "41 33 96>");
        commandMap.put("010A", "41 0A 97>");
        commandMap.put("0123", "41 23 98>");
        commandMap.put("010B", "41 0B 99>");

        // Temperature
        commandMap.put("010F", "41 0F A0>");
        commandMap.put("0105", "41 05 A1>");

        // Misc
        commandMap.put("010D", "41 0D A0>");



    }

    public static CommandsContainer getInstance() {
        if(instance == null) {
            instance = new CommandsContainer();
        }
        return instance;
    }

    public String getResponse(String readMessage){
        String response = commandMap.get(readMessage);
        return response!=null ? response : "";
    }

    public void updateSpeed(){

        speed ++;
        if(speed == 255){
            speed = 0;
        }
        String spp = Integer.valueOf(speed.toString(),16).toString();
        String value = "41 0D " + spp + ">";
        commandMap.put("010D", value);
    }

    public void updateRPM(){

        rpm ++;
        String spp = Integer.valueOf(rpm.toString(),16).toString();
        String value = "41 0C " + spp + ">";
        commandMap.put("010C", value);
    }

}
