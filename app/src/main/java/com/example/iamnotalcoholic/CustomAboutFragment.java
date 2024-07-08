package com.example.iamnotalcoholic;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class CustomAboutFragment extends DialogFragment {

    private OnDataChangeListener dataChangeListener;

    public void setOnDataChangeListener(OnDataChangeListener listener) {
        this.dataChangeListener = listener;
    }
    private DialogInterface.OnClickListener onOK(final int id){
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = getActivity().openOrCreateDatabase("app.db", android.content.Context.MODE_PRIVATE, null);
                String sql = String.format("DELETE FROM drinks where rowid = '%d';", id);
                db.execSQL(sql);
                db.close();

                if (dataChangeListener != null) {
                    dataChangeListener.onDataChanged();
                }
            }
        };

    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int id = getArguments().getInt("id");
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Удалить запись")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Вы уверены что хотите удалить запись?")
                .setPositiveButton("OK", onOK(id))
                .setNegativeButton("Отмена", null)
                .create();
    }
}
