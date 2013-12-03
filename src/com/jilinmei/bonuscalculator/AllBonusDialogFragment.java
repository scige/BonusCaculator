package com.jilinmei.bonuscalculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/*
public class AllBonusDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        //在onClick()中使用这个view，这里必须声明为final
        //另外一种方式是使用onCreateView()，绘制完整的UI，注册几个回调函数
        final View view = inflater.inflate(R.layout.input_dialog, null);
        builder.setView(view)
               .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   EditText allBonusEdit = (EditText)view.findViewById(R.id.inputDialogEdit);
                	   String allBonusStr = allBonusEdit.getText().toString();
                	   double allBonus = Double.parseDouble(allBonusStr);
                	   mListener.onDialogPositiveClick(allBonus);
                   }
               })
               .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   mListener.onDialogNegativeClick();
                   }
               });
        return builder.create();
    }
    
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(double allBonus);
        public void onDialogNegativeClick();
    }
    
    NoticeDialogListener mListener;
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
*/

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
            double allBonus = Double.parseDouble(allBonusStr);
            mListener.onDialogPositiveClick(allBonus);
		}
    }
    
    class NegativeOnClickListener implements DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			mListener.onDialogNegativeClick();
		}
    }
    
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(double allBonus);
        public void onDialogNegativeClick();
    }
    
    NoticeDialogListener mListener;
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
