package com.smlnskgmail.jaman.hashchecker.components.fileexplorer.explorer.filesadapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.smlnskgmail.jaman.hashchecker.R;
import com.smlnskgmail.jaman.hashchecker.components.fileexplorer.entities.FileItem;
import com.smlnskgmail.jaman.hashchecker.components.fileexplorer.explorer.OnFileClickListener;

class FileDialogHolder extends RecyclerView.ViewHolder {

    private TextView tvItemFile;

    private OnFileClickListener fileClickListener;

    FileDialogHolder(@NonNull View itemView, @NonNull OnFileClickListener fileClickListener) {
        super(itemView);
        tvItemFile = itemView.findViewById(R.id.tv_item_file);
        this.fileClickListener = fileClickListener;
    }

    void bind(@NonNull FileItem file) {
        loadItem(file);
        itemView.setOnClickListener(v -> fileClickListener.onFileClick(file, getAdapterPosition()));
    }

    private void loadItem(@NonNull FileItem file) {
        tvItemFile.setText(file.getFileName());
        Drawable fileIcon = ContextCompat.getDrawable(itemView.getContext(), file.getFileType()
                .getIconResId());
        tvItemFile.setCompoundDrawablesRelativeWithIntrinsicBounds(fileIcon, null, null, null);
    }

}