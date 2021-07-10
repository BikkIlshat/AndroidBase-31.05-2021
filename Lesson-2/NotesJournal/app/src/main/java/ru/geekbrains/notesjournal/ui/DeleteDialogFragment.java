package ru.geekbrains.notesjournal.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;

import ru.geekbrains.notesjournal.R;

public class DeleteDialogFragment extends DialogFragment {

    private DeleteDialogListener deleteDlgListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final View contentView = requireActivity().getLayoutInflater().
                inflate(R.layout.delete_note_dialog_fragment, null);
        MaterialButton confirmDelete = contentView.findViewById(R.id.confirm_delete_note_dialog_button);
        confirmDelete.setOnClickListener(v -> {
            if (deleteDlgListener != null) {
                deleteDlgListener.onDelete();
                dismiss();
            }
        });
        MaterialButton cancelDelete = contentView.findViewById(R.id.no_delete_note_dialog_button);
        cancelDelete.setOnClickListener(v -> {
            if (deleteDlgListener != null) {
                deleteDlgListener.onCancelDelete();
                dismiss();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity())
                .setView(contentView);
        return builder.create();
    }

    public void setOnDialogListener(DeleteDialogListener deleteDlgListener) {
        this.deleteDlgListener = deleteDlgListener;
    }
}