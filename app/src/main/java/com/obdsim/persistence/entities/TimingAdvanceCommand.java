package com.obdsim.persistence.entities;

/**
 * Created by Leo on 14/10/2017.
 */

public class TimingAdvanceCommand extends FuelLevelCommand {

    public TimingAdvanceCommand() {
        super("010E", "41 0E 84>", "Avance del tiempo", true);
    }
}
