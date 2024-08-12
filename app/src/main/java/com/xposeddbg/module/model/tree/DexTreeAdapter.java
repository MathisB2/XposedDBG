package com.xposeddbg.module.model.tree;


import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xposeddbg.module.R;

import io.github.ikws4.treeview.TreeItem;
import io.github.ikws4.treeview.TreeView;
import io.github.ikws4.treeview.TreeView.Adapter.OnTreeItemClickListener;

public class DexTreeAdapter extends TreeView.Adapter<DexTreeAdapter.ViewHolder, MethodWrapper> implements OnTreeItemClickListener<MethodWrapper> {

    public DexTreeAdapter() {
        setTreeItemClickListener(this);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.app_tree_item;
    }

    @Override
    public ViewHolder onCreateViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        TreeItem<MethodWrapper> item = items.get(position);
//        if (item.isExpandable()) {
//            holder.icon.setImageResource(R.drawable.ic_folder);
//        } else {
//            holder.icon.setImageResource(R.drawable.ic_file);
//        }

        holder.name.setText(item.getValue().getDisplayedValue());
    }

    @Override
    public void setRoot(TreeItem<MethodWrapper> root, boolean showRoot) {
        super.setRoot(root, showRoot);
    }

    @Override
    public void setRoot(TreeItem<MethodWrapper> root) {
        super.setRoot(root);
    }

    @Override
    public void onClick(TreeItem<MethodWrapper> item) {
        if (item.isExpandable() && !item.isExpanded()) {
            expand(item);
        }
    }

    static class ViewHolder extends TreeView.ViewHolder {
        ImageView icon;
        TextView name;
        CheckBox beforeHook;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.treeItem_icon);
            name = itemView.findViewById(R.id.treeItem_text);
            beforeHook = itemView.findViewById(R.id.treeItem_hookBeforeCheckbox);
        }
    }

    private void expand(TreeItem<MethodWrapper> root) {
//        if (!root.getChildren().isEmpty()) return;
//
//        for (File child : getChildren(new File(getFilePath(root)))) {
//            TreeItem<String> item = new TreeItem<>(child.getName(), child.isDirectory());
//            root.getChildren().add(item);
//        }
//        Collections.sort(root.getChildren(), (a, b) -> {
//            int res = Boolean.compare(b.isExpandable(), a.isExpandable());
//            if (res == 0) return a.getValue().compareToIgnoreCase(b.getValue());
//            return res;
//        });
    }
}