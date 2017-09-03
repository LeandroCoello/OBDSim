package com.obdsim.activities;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.obdsim.R;
import com.obdsim.persistence.DataBaseService;
import com.obdsim.persistence.entities.MockObdCommand;
import com.obdsim.utils.ConfirmDialog;
import com.obdsim.utils.ObdCommandAdapter;

import java.util.List;

public class CommandsActivity extends AppCompatActivity {

    private DataBaseService dataBaseService;
    private RecyclerView recyclerView;
    private List<MockObdCommand> commands;
    private static final int ADD_COMMAND = 0;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commands);
        dataBaseService = new DataBaseService(this);
        commands = dataBaseService.getCommands(null,null);

        recyclerView = (RecyclerView) findViewById(R.id.command_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ObdCommandAdapter(commands,this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        this.menu = menu;
        menu.add(0, ADD_COMMAND, 0, "Add Command");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case ADD_COMMAND:
                ConfirmDialog.getInsertDialog(this).show();
        }
        return false;
    }

    public DataBaseService getDataBaseService() {
        return dataBaseService;
    }

    public void refreshRecyclerViewAdapter(){
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public void refreshRecyclerViewAdapterOnInsert(MockObdCommand cmd) {
        commands.add(cmd);
        refreshRecyclerViewAdapter();
    }

    public void refreshRecyclerViewAdapterOnDelete(MockObdCommand cmd) {
        commands.remove(cmd);
        refreshRecyclerViewAdapter();
    }

    public void addCommand(View v) {
        ConfirmDialog.getInsertDialog(this).show();
    }

    public void removeCommand(View v) {
        ConfirmDialog.getInsertDialog(this).show();
    }
}
