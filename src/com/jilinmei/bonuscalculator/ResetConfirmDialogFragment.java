package com.jilinmei.bonuscalculator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class ResetConfirmDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("���ú�ɾ��ȫ������, ȷ��Ҫ���ã�")
               .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   ((MainActivity)getActivity()).onResetDialogPositiveClick();
                   }
               })
               .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   ((MainActivity)getActivity()).onResetDialogNegativeClick();
                   }
               });
        return builder.create();
    }
}
