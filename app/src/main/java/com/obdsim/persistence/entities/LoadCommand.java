package com.obdsim.persistence.entities;

/**
 * Created by Leo on 14/10/2017.
 */

public class LoadCommand extends FuelLevelCommand {

    public LoadCommand() {
        super("0104", "41 04 78>", "Carga calculada del motor", true);
    }
}
