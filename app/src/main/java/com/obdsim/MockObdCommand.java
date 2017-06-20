package com.obdsim;

/**
 * Created by Leo on 20/6/2017.
 */

public class MockObdCommand {

    private String cmd;
    private String response;

    public MockObdCommand(String cmd, String response) {
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
}
