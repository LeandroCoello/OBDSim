package com.obdsim.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.obdsim.R;
import com.obdsim.activities.CommandsActivity;
import com.obdsim.activities.MainActivity;
import com.obdsim.persistence.DataBaseService;
import com.obdsim.persistence.entities.MockObdCommand;

public class ConfirmDialog {


    public static void showDialog(Context context, String title, String msg, LayoutInflater inflater) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

//       View view = inflater.inflate(R.layout.custom_dialog, null);

        // set dialog message
        alertDialogBuilder
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
//                .setCustomTitle(view);

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    public static AlertDialog.Builder getDialog(final Context context, String title, String msg) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        final CommandsActivity sta = (CommandsActivity) context;
        // set dialog message
        alertDialogBuilder
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false);

        return alertDialogBuilder;
    }

    public static AlertDialog getWaitTimeDialog(final MainActivity mainActivity){

        LayoutInflater inflater = mainActivity.getLayoutInflater();

        TableLayout view  = (TableLayout) inflater.inflate(R.layout.update_wait, null);
        View title = inflater.inflate(R.layout.update_command_title, null);
        final TextView titleView = (TextView) title.findViewById(R.id.dialog_title);
        titleView.setText("Update Wait Time");

        final EditText waitTime = (EditText) view.findViewById(R.id.edit_wait);
        Integer value = mainActivity.getWaitTime();
        waitTime.setText(value.toString());
        AlertDialog dialog = new AlertDialog.Builder(mainActivity)
                .setCustomTitle(title)
                .setView(view)
                .setCancelable(false)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        int input = Integer.parseInt(waitTime.getText().toString());
                        mainActivity.setWaitTime(input);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .create();
        return dialog;
    }

    public static AlertDialog getUpdateDialog(final CommandsActivity commandsActivity, final MockObdCommand cmd){

        LayoutInflater inflater = commandsActivity.getLayoutInflater();
        final DataBaseService db = commandsActivity.getDataBaseService();


        TableLayout view  = (TableLayout) inflater.inflate(R.layout.update_cmd_dialog, null);
        View title = inflater.inflate(R.layout.update_command_title, null);
        final EditText editCmd = (EditText) view.findViewById(R.id.edit_command);
        final EditText editResponse = (EditText) view.findViewById(R.id.edit_response);
        editCmd.setText(cmd.getCmd());
        editResponse.setText(cmd.getResponse());
        AlertDialog dialog = new AlertDialog.Builder(commandsActivity)
                .setCustomTitle(title)
                .setView(view)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        cmd.setCmd(editCmd.getText().toString());
                        cmd.setResponse(editResponse.getText().toString());
                        db.updateCommand(cmd);
                        commandsActivity.refreshRecyclerViewAdapter();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .create();
        return dialog;
    }

    public static AlertDialog getStateUpdateDialog(final CommandsActivity commandsActivity, final MockObdCommand cmd){

        LayoutInflater inflater = commandsActivity.getLayoutInflater();
        final DataBaseService db = commandsActivity.getDataBaseService();


        LinearLayout view  = (LinearLayout) inflater.inflate(R.layout.staupdate_cmd_dialog, null);
        View title = inflater.inflate(R.layout.update_command_title, null);
        final TextView txtCmd = (TextView) view.findViewById(R.id.edit_command);
        final EditText editResponse = (EditText) view.findViewById(R.id.edit_response);
        txtCmd.setText(cmd.getDescription());
        editResponse.setText(cmd.getValue());
        AlertDialog dialog = new AlertDialog.Builder(commandsActivity)
                .setCustomTitle(title)
                .setView(view)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (!cmd.validateInput(editResponse.getText().toString())) {
                            commandsActivity.showToast("Invalid Input!");
                            return;
                        }
                        cmd.setValue(editResponse.getText().toString());
                        cmd.setResponse(cmd.generateResponse());
                        cmd.setValue();
                        db.updateCommand(cmd);
                        commandsActivity.refreshRecyclerViewAdapter();
                        commandsActivity.showToast("Update Succesful!");
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .create();
        return dialog;
    }

    public static AlertDialog getInsertDialog(final CommandsActivity commandsActivity){

        LayoutInflater inflater = commandsActivity.getLayoutInflater();
        final DataBaseService db = commandsActivity.getDataBaseService();
        final MockObdCommand cmd = new MockObdCommand();
        TableLayout view  = (TableLayout) inflater.inflate(R.layout.update_cmd_dialog, null);
        View title = inflater.inflate(R.layout.update_command_title, null);
        final TextView titleView = (TextView) title.findViewById(R.id.dialog_title);
        titleView.setText("Add Command");

        final EditText editCmd = (EditText) view.findViewById(R.id.edit_command);
        final EditText editResponse = (EditText) view.findViewById(R.id.edit_response);
        AlertDialog dialog = new AlertDialog.Builder(commandsActivity)
                .setCustomTitle(title)
                .setView(view)
                .setPositiveButton("Add Command", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        cmd.setCmd(editCmd.getText().toString());
                        cmd.setResponse(editResponse.getText().toString());
                        db.insertCommand(cmd);
                        commandsActivity.refreshRecyclerViewAdapterOnInsert(cmd);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .create();
        return dialog;
    }

    public static AlertDialog getDeleteDialog(final CommandsActivity commandsActivity, final MockObdCommand cmd){

        LayoutInflater inflater = commandsActivity.getLayoutInflater();
        final DataBaseService db = commandsActivity.getDataBaseService();
        View title = inflater.inflate(R.layout.update_command_title, null);
        final TextView titleView = (TextView) title.findViewById(R.id.dialog_title);
        titleView.setText("Remove Command");
        AlertDialog dialog = new AlertDialog.Builder(commandsActivity)
                .setCustomTitle(title)
                .setMessage("Are you sure?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db.deleteCommand(cmd);
                        commandsActivity.refreshRecyclerViewAdapterOnDelete(cmd);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .create();
        return dialog;
    }


}
