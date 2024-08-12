package com.xposeddbg.module.ui;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xposeddbg.module.R;

import io.github.ikws4.treeview.TreeView;

class TaskTreeAdapter extends TreeView.Adapter<TaskTreeAdapter.ViewHolder, String> {

    @Override
    public ViewHolder onCreateViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.name.setText(items.get(position).getValue());
    }

    @Override
    public int getLayoutRes() {
        return R.layout.app_tree_item;
    }

    static class ViewHolder extends TreeView.ViewHolder {
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.treeItem_text);
        }
    }
}