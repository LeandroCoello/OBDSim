package com.obdsim.persistence.entities;

/**
 * Created by Leo on 20/6/2017.
 */

public class MockObdCommand {

    private Integer _id;
    private String cmd;
    private String response;

    public MockObdCommand() {
    }

    public MockObdCommand(String cmd, String response) {
        this.cmd = cmd;
        this.response = response;
    }

    public MockObdCommand(Integer _id, String cmd, String response) {
        this._id = _id;
        this.cmd = cmd;
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }
}
