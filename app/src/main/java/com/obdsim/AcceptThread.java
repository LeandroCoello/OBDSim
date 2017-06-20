package com.obdsim;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by Leo on 18/6/2017.
 */

public class AcceptThread extends AsyncTask<String,String,String> {

        private final BluetoothServerSocket mmServerSocket;
        private MainActivity main;

        public AcceptThread(BluetoothAdapter mBluetoothAdapter, MainActivity main) {
            // Use a temporary object that is later assigned to mmServerSocket,
            // because mmServerSocket is final
            BluetoothServerSocket tmp = null;
            this.main = main;
            try {
                // MY_UUID is the app's UUID string, also used by the client code
                tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(main.getNameSecure(),main.getMyUuid());
            } catch (IOException e) { }
            mmServerSocket = tmp;
        }

    @Override
    protected String doInBackground(String... strings) {

            BluetoothSocket socket = null;
            // Keep listening until exception occurs or a socket is returned
            while (true) {
                try {
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    break;
                }
                // If a connection was accepted
                if (socket != null) {
                    // Do work to manage the connection (in a separate thread)
                    publishProgress("Connected!");

                    ConnectedThread ct = new ConnectedThread(socket, main);
                    ct.run();

                    try {
                        mmServerSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }

            return null;
        }

    @Override
    protected void onProgressUpdate(String... progress) {
        main.updateStatus(progress[0], 0);
        main.showToast(progress[0]);
    }

    protected void onPostExecute(String result) {
        main.updateStatus("Process Terminated.", 0);
    }

}
