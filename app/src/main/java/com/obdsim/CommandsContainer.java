package com.obdsim;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Leo on 20/6/2017.
 */

public class CommandsContainer {

    LinkedHashMap<String,String> commandMap = new LinkedHashMap<String,String>();
    private static CommandsContainer instance = null;

    private CommandsContainer() {

        // Initialize
        commandMap.put("AT Z", "OK>");
        commandMap.put("AT E0", "OK>");
        commandMap.put("AT L0", "OK>");
        commandMap.put("AT ST 3e", "OK>");
        commandMap.put("AT SP 0", "OK>");

        // Control
        commandMap.put("01 46", "41 46 50>");
        commandMap.put("01 42", "41 42 0F 0f>");
        commandMap.put("01 44", "41 44 64 FF>");
        commandMap.put("01 21", "41 21 10 00>");
        commandMap.put("01 01", "41 01 84>");
        commandMap.put("01 0E", "41 0E 84>");
        commandMap.put("03", "43 01 33 00 00 00 00>");
        commandMap.put("09 02", "31 48 47 42 48 34 31 4A 58 4D 4E 31 30 39 31 38 36>");

        // Engine
        commandMap.put("01 04", "41 04 78>");
        commandMap.put("01 0C", "41 0C 32 96>");
        commandMap.put("01 1F", "41 0F 10 14>");
        commandMap.put("01 10", "41 10 64 64>");
        commandMap.put("01 11", "41 11 96>");

        // Fluel
        commandMap.put("01 51", "41 51 01>");
        commandMap.put("01 5E", "41 5E 64 FA>");
        commandMap.put("01 2F", "41 5E 64>");
        commandMap.put("01 06", "41 06 B4>");
        commandMap.put("01 07", "41 07 B4>");
        commandMap.put("01 08", "41 08 B4>");
        commandMap.put("01 09", "41 09 B4>");
        commandMap.put("01 44", "41 44 34 87>");
        commandMap.put("01 34", "41 34 34 87>");
        commandMap.put("01 5C", "41 5C 64>");

        // Pressure
        commandMap.put("01 33", "41 33 96>");
        commandMap.put("01 0A", "41 0A 97>");
        commandMap.put("01 23", "41 23 98>");
        commandMap.put("01 0B", "41 0B 99>");

        // Temperature
        commandMap.put("01 0F", "41 0F A0>");
        commandMap.put("01 05", "41 05 A1>");

        // Misc
        commandMap.put("01 0D", "41 0D A0>");



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

}
