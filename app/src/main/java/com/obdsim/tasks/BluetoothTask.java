package com.obdsim.tasks;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;

import com.obdsim.R;
import com.obdsim.activities.MainActivity;
import com.obdsim.persistence.DataBaseService;
import com.obdsim.persistence.entities.MockObdCommand;
import com.obdsim.persistence.ObdCommandContract;

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
        BluetoothSocket socket = null;

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

            dataBaseService = main.getDataBaseService();

        }

    @Override
    protected String doInBackground(String... strings) {

            try {
                //Accepting incoming connection.
                socket = mmServerSocket.accept();
                if (socket == null) {
                    publishProgress(getPublishList(main.getString(R.string.null_socket),"2"));
                    return null;
                }
                publishProgress(getPublishList(main.getString(R.string.accepted),"0"));

                mmInStream = socket.getInputStream();
                mmOutStream = socket.getOutputStream();

                while ( !isCancelled() || socket.isConnected() ) {
                        try {
                        // Read from the InputStream
                        publishProgress(getPublishList(main.getString(R.string.receiving),"0"));

                        bytesReceived = mmInStream.read(buffer);
                        String readMessage = new String(buffer, 0, bytesReceived).trim();
                        publishProgress(getPublishList(readMessage,"3"));

                        // Get response and write it into OutputStream
                        String where = ObdCommandContract.CommandEntry.CODE+"=?";
                        String[] values = new String[]{readMessage};
                        List<MockObdCommand> commands = dataBaseService.getCommands(where, values);
                        MockObdCommand mCmd = commands.get(0);
                        String response = mCmd.getResponse();
                        mmOutStream.write(response.getBytes());
                        publishProgress(getPublishList(response,"4"));

                    } catch (IOException e) {
                        publishProgress(getPublishList(main.getString(R.string.problem_receiving),"2"));
                        socket.close();
                        break;
                    }
                }

            } catch (IOException e) {
                try {
                    socket.close();
                } catch (IOException e1) {
                    publishProgress(getPublishList(main.getString(R.string.problem_closing_socket),"2"));
                    e1.printStackTrace();
                }
                e.printStackTrace();
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

    @Override
    protected void onCancelled() {
        if ( socket != null ) {
            try {
                socket.close();
            } catch (IOException e) {
                main.showToast(e.getMessage());
            }
        }
    }

}
