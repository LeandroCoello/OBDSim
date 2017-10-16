package com.obdsim.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.widget.Toast;

import com.obdsim.R;
import com.obdsim.persistence.DataBaseService;
import com.obdsim.persistence.ObdCommandContract;
import com.obdsim.persistence.entities.MockObdCommand;
import com.obdsim.utils.ObdCommandAdapter;
import com.obdsim.utils.StateObdCommandAdapter;

import java.util.List;

/**
 * Created by Leo on 12/10/2017.
 */

public class StateCommandsActivity extends CommandsActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commands);
        dataBaseService = new DataBaseService(this);
        String where = ObdCommandContract.CommandEntry.STATE_FLAG + "=?";
        String [] values = new String[] {"1"};
        commands = dataBaseService.getCommands(where,values,true);

        recyclerView = (RecyclerView) findViewById(R.id.command_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new StateObdCommandAdapter(commands,this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
