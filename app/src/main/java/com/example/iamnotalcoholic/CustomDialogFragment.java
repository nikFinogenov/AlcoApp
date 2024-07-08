package com.example.iamnotalcoholic;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.annotation.NonNull;

public class CustomDialogFragment extends DialogFragment {

    private OnDataChangeListener dataChangeListener;

    public void setOnDataChangeListener(OnDataChangeListener listener) {
        this.dataChangeListener = listener;
    }

    private DialogInterface.OnClickListener onOK(){
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = getActivity().openOrCreateDatabase("app.db", android.content.Context.MODE_PRIVATE, null);
                db.execSQL("DELETE FROM drinks;");
                db.close();

                if (dataChangeListener != null) {
                    dataChangeListener.onDataChanged();
                }
            }
        };
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Удалить неделю")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Вы уверены что хотите удалить все недельные данные?")
                .setPositiveButton("OK", onOK())
                .setNegativeButton("Отмена", null)
                .create();
    }
}
