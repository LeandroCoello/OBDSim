package com.obdsim;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 1234;

    // Name for the SDP record when creating server socket
    private static final String NAME_SECURE = "BluetoothChatSecure";
    private static final String NAME_INSECURE = "BluetoothChatInsecure";

    // Unique UUID for this application

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private static final UUID MY_UUID_SECURE = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
    private static final UUID MY_UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    private static AcceptThread listeningThread;
    private BluetoothChatService mChatService;
    private String mConnectedDeviceName = null;
    private StringBuffer mOutStringBuffer;



    private ListView status;
    private Button startButton;
    private Button stopButton;
    private List<String> states = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        status = (ListView)findViewById(R.id.status);
        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        stopButton.setEnabled(false);
        status.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item,states));
        updateStatus("hola", 0);
        enableBluetooth();

//        if (mChatService == null) {
//            setupChat();
//        }

    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//
//        // Performing this check in onResume() covers the case in which BT was
//        // not enabled during onStart(), so we were paused to enable it...
//        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
//        if (mChatService != null) {
//            // Only if the state is STATE_NONE, do we know that we haven't started already
//            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
//                // Start the Bluetooth chat services
//                mChatService.start();
//            }
//        }
//    }

    /**
     * The Handler that gets information back from the BluetoothChatService
     */
//    private final Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
////            FragmentActivity activity = getActivity();
//            switch (msg.what) {
//                case Constants.MESSAGE_STATE_CHANGE:
//                    switch (msg.arg1) {
//                        case BluetoothChatService.STATE_CONNECTED:
//                            updateStatus("Conectado a", 0);
//                            break;
//                        case BluetoothChatService.STATE_CONNECTING:
//                            updateStatus("Conecting..", 0);
//                            break;
//                        case BluetoothChatService.STATE_LISTEN:
//                        case BluetoothChatService.STATE_NONE:
//                            updateStatus("Not connected.", 0);
//                            break;
//                    }
//                    break;
//                case Constants.MESSAGE_READ:
//                    byte[] readBuf = (byte[]) msg.obj;
//                    // construct a string from the valid bytes in the buffer
//                    String readMessage = new String(readBuf, 0, msg.arg1);
//                    updateStatus(readMessage,0);
//                    break;
//                case Constants.MESSAGE_DEVICE_NAME:
//                    // save the connected device's name
//                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
//
//                    updateStatus("Connected to " + mConnectedDeviceName, 0);
//
//                    break;
//            }
//        }
//    };
//
//    private void setupChat() {
//
//        // Initialize the BluetoothChatService to perform bluetooth connections
//        mChatService = new BluetoothChatService(this, mHandler);
//
//        // Initialize the buffer for outgoing messages
//        mOutStringBuffer = new StringBuffer("");
//    }



    public void start(View v){
        updateStatus("Connecting..", 0);

        BluetoothAdapter ba = startBluetooth();
        if ( ba != null ) {
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            listeningThread = new AcceptThread(ba, this);
            listeningThread.execute();
        }
    }

    public void stop(View v){
        Toast.makeText(getApplicationContext(),"Stopped Listening.",Toast.LENGTH_LONG).show();
        updateStatus("Stopped Listening.", 0);
        stopButton.setEnabled(false);
        startButton.setEnabled(true);
        listeningThread.cancel(false);

    }

    protected BluetoothAdapter startBluetooth(){

        //Getting de Bluetooth Adapter
        BluetoothAdapter mBluetoothAdapter = getBluetoothAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            Toast.makeText(getApplicationContext(),"Device does not support Bluetooth",Toast.LENGTH_LONG).show();
            updateStatus("Device does not support Bluetooth",2);
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

        states.add(newState);
        status.setAdapter(new ArrayAdapter<>(this, R.layout.list_item,states));
    }

    public void enableBluetooth() {

        if(getBluetoothAdapter()!= null && !getBluetoothAdapter().isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        // If BT is not on, request that it be enabled.
//        // setupChat() will then be called during onActivityResult
//        if (!getBluetoothAdapter().isEnabled()) {
//            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
//            // Otherwise, setup the chat session
//        } else if (mChatService == null) {
//            setupChat();
//        }
//    }


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

        if (mChatService != null) {
            mChatService.stop();
        }
    }

    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG).show();
    }
}
