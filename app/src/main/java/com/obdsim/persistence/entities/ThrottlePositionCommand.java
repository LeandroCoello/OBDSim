package com.obdsim.persistence.entities;

/**
 * Created by Leo on 14/10/2017.
 */

public class ThrottlePositionCommand extends FuelLevelCommand {

    public ThrottlePositionCommand() {
        super("0111", "41 11 96>", "Posición del acelerador", true);
    }
}
