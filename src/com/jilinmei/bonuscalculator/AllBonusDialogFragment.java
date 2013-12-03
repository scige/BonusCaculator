package com.jilinmei.bonuscalculator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AllBonusDialogFragment extends DialogFragment {
	
	View view = null;
	
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.input_dialog, null);
        builder.setView(view)
               .setPositiveButton("确定", new PositiveOnClickListener())
               .setNegativeButton("取消", new NegativeOnClickListener());
        return builder.create();
    }
    
    class PositiveOnClickListener implements DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			EditText allBonusEdit = (EditText)view.findViewById(R.id.inputDialogEdit);
            String allBonusStr = allBonusEdit.getText().toString();
            double allBonus = 0.0;
            try {
            	allBonus = Double.parseDouble(allBonusStr);
            }
            catch (Exception ex) {
            	allBonus = 0.0;
            }
            ((MainActivity)getActivity()).onDialogPositiveClick(allBonus);
		}
    }
    
    class NegativeOnClickListener implements DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			((MainActivity)getActivity()).onDialogNegativeClick();
		}
    }
}
