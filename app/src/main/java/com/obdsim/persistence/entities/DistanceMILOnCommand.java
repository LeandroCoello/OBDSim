package com.obdsim.persistence.entities;

/**
 * Created by Leo on 14/10/2017.
 */

public class DistanceMILOnCommand extends RuntimeCommand {

    public DistanceMILOnCommand() {
        super("0121", "41 21 10 00>", "Distancia recorrida con la luz de falla", true);
    }

    public String parseResponse(){
        return value + " km";
    }

}
