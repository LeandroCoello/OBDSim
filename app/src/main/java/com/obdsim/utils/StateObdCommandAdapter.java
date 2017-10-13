package com.obdsim.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.obdsim.R;
import com.obdsim.activities.CommandsActivity;
import com.obdsim.activities.StateCommandsActivity;
import com.obdsim.persistence.entities.MockObdCommand;

import java.util.List;

/**
 * Created by Leo on 12/10/2017.
 */

public class StateObdCommandAdapter extends ObdCommandAdapter {

    /**
     * Default constructor
     * @param items to fill data to
     */
    public StateObdCommandAdapter(final List<MockObdCommand> items, Context context) {
       super(items, context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.state_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ObdCommandAdapter.MyViewHolder holder, int position) {
        final StateCommandsActivity sca = (StateCommandsActivity) mContext;
        final MockObdCommand cmd = cmds.get(position);
        holder.cmdName.setText( cmd.getDescription());
        holder.responseValue.setText( cmd.parseResponse());
        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmDialog.getStateUpdateDialog((CommandsActivity) mContext, cmd).show();
            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmDialog.getDeleteDialog((CommandsActivity) mContext, cmd).show();
            }
        });

    }

}
