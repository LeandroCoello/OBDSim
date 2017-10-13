package com.obdsim.persistence;

import android.database.Cursor;

import com.obdsim.persistence.entities.MockObdCommand;
import com.obdsim.utils.CommandsContainer;

import java.util.LinkedHashMap;

/**
 * Created by Leo on 4/8/2017.
 */

public class ObdCommandRowMapper {

    public static MockObdCommand getCommand(Cursor c){
        MockObdCommand cmd = null;
        CommandsContainer ins = CommandsContainer.getInstance();
        LinkedHashMap<String, Class<? extends MockObdCommand>> map = ins.getMap();
        Class<? extends MockObdCommand> cla = map.get(c.getString(1));

        try {
            cmd = cla.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
        Boolean stateFlag = (c.getInt(3) == 1) ? true : false;

        cmd.set_id(c.getInt(0));
        cmd.setCmd(c.getString(1));
        cmd.setResponse(c.getString(2));
        cmd.setStateFlag(stateFlag);
        cmd.setDescription(c.getString(4));
        cmd.setValue();

        return cmd;
    }
}
