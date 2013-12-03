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
        builder.setMessage("重置后将删除全部数据, 确定要重置？")
               .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   ((MainActivity)getActivity()).onResetDialogPositiveClick();
                   }
               })
               .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   ((MainActivity)getActivity()).onResetDialogNegativeClick();
                   }
               });
        return builder.create();
    }
}
