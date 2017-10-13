package com.obdsim.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.obdsim.R;
import com.obdsim.activities.CommandsActivity;
import com.obdsim.persistence.entities.MockObdCommand;

import java.util.List;

/**
 * Created by Leo on 30/7/2017.
 */

public class ObdCommandAdapter extends RecyclerView.Adapter<ObdCommandAdapter.MyViewHolder> {

    protected List<MockObdCommand> cmds;
    protected Context mContext;

    /**
     * Default constructor
     * @param items to fill data to
     */
    public ObdCommandAdapter(final List<MockObdCommand> items, Context context) {
        this.cmds = items;
        this.mContext = context;
   }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, null, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final MockObdCommand cmd = cmds.get(position);
        holder.cmdName.setText( cmd.getCmd());
        holder.responseValue.setText( cmd.getResponse());
        holder.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmDialog.getUpdateDialog((CommandsActivity) mContext, cmd).show();
            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmDialog.getDeleteDialog((CommandsActivity) mContext, cmd).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return cmds.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        protected TextView cmdName;
        protected TextView responseValue;
        protected Button updateBtn;
        protected Button deleteBtn;

        public MyViewHolder(View itemView) {
            super(itemView);
            cmdName = (TextView) itemView.findViewById(R.id.textview_cmd_value);
            responseValue = (TextView) itemView.findViewById(R.id.textview_response_value);
            updateBtn = (Button) itemView.findViewById(R.id.button_update);
            deleteBtn = (Button) itemView.findViewById(R.id.button_delete);
        }
    }
}

