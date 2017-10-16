package com.obdsim.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.obdsim.persistence.ObdCommandContract;
import com.obdsim.persistence.entities.MockObdCommand;
import com.obdsim.tasks.BluetoothTask;
import com.obdsim.R;
import com.obdsim.persistence.DataBaseService;
import com.obdsim.utils.ConfirmDialog;
import com.obdsim.utils.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 1234;
    private static final int UPDATE_COMMAND = 0;
    int waitTime = Constants.WAIT_TIME;


    // Name for the SDP record when creating server socket
    private static final String NAME_SECURE = "BluetoothChatSecure";
    private static final String NAME_INSECURE = "BluetoothChatInsecure";

    // Unique UUID for this application

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private static final UUID MY_UUID_SECURE = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
    private static final UUID MY_UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    private static BluetoothTask listeningThread;
    private ListView status;
    private Button startButton;
    private Button stopButton;
    private List<String> states = new ArrayList<String>();
    private DataBaseService dataBaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        status = (ListView)findViewById(R.id.status);
        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        stopButton.setEnabled(false);
        status.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item_main,states));

        dataBaseService = new DataBaseService(this);
        enableBluetooth();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, UPDATE_COMMAND, 0, "Update Wait Time");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case UPDATE_COMMAND:
                ConfirmDialog.getWaitTimeDialog(this).show();
        }
        return false;
    }


    public void start(View v){
        states = new ArrayList<String>();
        status.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item_main,states));

        updateStatus(getString(R.string.connecting), 0);

        BluetoothAdapter ba = startBluetooth();
        if ( ba != null ) {
            startButton.setEnabled(false);
            stopButton.setEnabled(true);

            if (listeningThread != null && !listeningThread.isCancelled()) {
                listeningThread.cancel(true);
                listeningThread = null;
            }

            listeningThread = new BluetoothTask(ba, this);
            listeningThread.execute();
        } else {
            updateStatus(getString(R.string.no_bluetooth_adapter),2);
        }
    }

    public void stop(View v){
        showToast(getString(R.string.stopped_listening));
        states.clear();
        status.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item,states));
        startButton.setEnabled(true);
        stopButton.setEnabled(false);

        if (listeningThread != null && !listeningThread.isCancelled()) {

            BluetoothSocket socket = listeningThread.getSocket();
            if (socket == null) {
                return;
            }
            InputStream in = null;
            try {
                in = socket.getInputStream();
                in.close();
                OutputStream out = socket.getOutputStream();
                out.close();
                socket.close();
            } catch (IOException e) {
                updateStatus(e.getMessage(), 2);
            }
            listeningThread.cancel(true);
            listeningThread = null;
        }

    }

    public void showCommands(View v) {
        startActivity(new Intent(this, CommandsActivity.class));
    }

    public void showStateCommands(View v) {
        startActivity(new Intent(this, StateCommandsActivity.class));
    }


    protected BluetoothAdapter startBluetooth(){

        //Getting de Bluetooth Adapter
        BluetoothAdapter mBluetoothAdapter = getBluetoothAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            showToast(getString(R.string.no_bluetooth));
            updateStatus(getString(R.string.no_bluetooth),2);
            return mBluetoothAdapter;
        }

        //Asking for enable Bluetooth
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        //Getting the paired devices
        /*Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Do something with each device
            }
        }*/
        return mBluetoothAdapter;
    }

    public static String getNameSecure() {
        return NAME_SECURE;
    }

    public static String getNameInsecure() {
        return NAME_INSECURE;
    }

    public static UUID getMyUuid() {
        return MY_UUID;
    }

    public static UUID getMyUuidSecure() {
        return MY_UUID_SECURE;
    }

    public static UUID getMyUuidInsecure() {
        return MY_UUID_INSECURE;
    }

    public Button getStartButton() {
        return startButton;
    }

    public Button getStopButton() {
        return stopButton;
    }

    public void updateStatus(String newState, Integer type) {

        if (type == 0) {
            newState = "[INFO] " + newState;
        }

        if (type == 1) {
            newState = "[WARN] " + newState;
        }

        if (type == 2) {
            newState = "[ERROR] " + newState;
        }

        if (type == 3) {
            newState = "[INFO][RECEIVED] " + newState;
        }

        if (type == 4) {
            newState = "[INFO][SENT] " + newState;
        }

        states.add(newState);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_main,states);
        status.setAdapter(adapter);
        status.setSelection(adapter.getCount());
    }

    public void enableBluetooth() {

        if(getBluetoothAdapter()!= null && !getBluetoothAdapter().isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }
    }


    private BluetoothAdapter getBluetoothAdapter() {
        return BluetoothAdapter.getDefaultAdapter();
    }

    @Override
    protected void onDestroy() {
        BluetoothAdapter ba = getBluetoothAdapter();
        if (ba != null && ba.isEnabled()) {
            ba.disable();
        }
        super.onDestroy();
    }

    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
    }

    public DataBaseService getDataBaseService() {
        return dataBaseService;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
        if (listeningThread != null) {
            listeningThread.setWaitTime(waitTime);
        }
    }

    @Override
    public void onBackPressed(){
        if(listeningThread !=null && !listeningThread.isCancelled()) {
            listeningThread.cancel(true);
            listeningThread = null;
        }
        super.onBackPressed();
    }
}
