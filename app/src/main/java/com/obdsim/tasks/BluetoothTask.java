package com.obdsim.tasks;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.obdsim.R;
import com.obdsim.activities.MainActivity;
import com.obdsim.persistence.DataBaseService;
import com.obdsim.persistence.entities.MockObdCommand;
import com.obdsim.persistence.ObdCommandContract;
import com.obdsim.utils.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leo on 18/6/2017.
 */

public class BluetoothTask extends AsyncTask<String,ArrayList<String>,String> {

    private final BluetoothServerSocket mmServerSocket;
    private MainActivity main;
    byte[] buffer = new byte[1024];
    int bytesReceived;
    private InputStream mmInStream = null;
    private OutputStream mmOutStream= null;
    DataBaseService dataBaseService;
    BluetoothSocket socket;
    private int waitTime = 0;

    public BluetoothTask(BluetoothAdapter mBluetoothAdapter, MainActivity main) {
        // Use a temporary object that is later assigned to mmServerSocket,
        // because mmServerSocket is final
        BluetoothServerSocket tmp = null;
        this.main = main;
        try {
            // MY_UUID is the app's UUID string, also used by the client code
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(main.getNameSecure(),main.getMyUuid());
        } catch (IOException e) { }
        mmServerSocket = tmp;
        waitTime = main.getWaitTime();
        dataBaseService = main.getDataBaseService();

    }

    @Override
    protected String doInBackground(String... strings) {

            try {
                while (!isCancelled()) {
                    //Accepting incoming connection.
                    socket = mmServerSocket.accept();
                    if (socket == null) {
                        publishProgress(getPublishList(main.getString(R.string.null_socket), "2"));
                        return null;
                    }
                    publishProgress(getPublishList(main.getString(R.string.accepted), "0"));

                    mmInStream = socket.getInputStream();
                    mmOutStream = socket.getOutputStream();

                    while (!isCancelled() || socket.isConnected()) {
                        try {
                            // Read from the InputStream
                            publishProgress(getPublishList(main.getString(R.string.receiving), "0"));

                            bytesReceived = mmInStream.read(buffer);
                            String readMessage = new String(buffer, 0, bytesReceived).trim();
                            publishProgress(getPublishList(readMessage, "3"));

                            if (waitTime > 0) {
                                Thread.sleep(waitTime);
                            }
                            // Get response and write it into OutputStream
                            String response = dataBaseService.getResponse(readMessage);
                            mmOutStream.write(response.getBytes());
                            publishProgress(getPublishList(response, "4"));

                        } catch (Exception e) {
                            publishProgress(getPublishList(main.getString(R.string.connection_closed), "0"));
                            publishProgress(getPublishList(main.getString(R.string.connecting), "0"));
                            socket.getInputStream().close();
                            socket.getOutputStream().close();
                            socket.close();
                            socket = null;
                            break;
                        }


                    }
                }
            } catch (IOException e) {
                try {
                    socket.close();
                    socket = null;
                } catch (IOException e1) {
                    publishProgress(getPublishList(main.getString(R.string.problem_closing_socket),"2"));
                    e1.printStackTrace();
                }
                publishProgress(getPublishList(main.getString(R.string.problem_receiving),"2"));
            }

            return null;
        }

    @Override
    protected void onProgressUpdate(ArrayList<String>... progress) {
        ArrayList<String> parameters = progress[0];
        String message = parameters.get(0);
        Integer code = Integer.valueOf(parameters.get(1));
        main.updateStatus(message, code);
    }

    public ArrayList<String> getPublishList(String message, String code){
        ArrayList<String> paramList = new ArrayList<String>();
        paramList.add(message);
        paramList.add(code.toString());

        return paramList;
    }

    protected void onPostExecute(String result) {
        main.updateStatus(main.getString(R.string.process_terminated), 0);
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public BluetoothSocket getSocket() {
        return socket;
    }

    public void setSocket(BluetoothSocket socket) {
        this.socket = socket;
    }

    @Override
    protected void onCancelled() {
        if ( mmServerSocket != null ) {
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                main.showToast(e.getMessage());
            }
        }
    }

}
