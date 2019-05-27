package com.example.listview_demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


public class ListviewDemoDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.search_title);
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View mView = inflater.inflate(R.layout.dialog_search, null);
        final TextView mTextView = mView.findViewById(R.id.search_content);
        builder.setView(mView);
        builder.setPositiveButton(R.string.search_pos, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogPositiveClick(mTextView.getText().toString());
                    }
                })
                .setNegativeButton(R.string.search_neg, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ListviewDemoDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(String search_content);
    }

    NoticeDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement NoticeDialogListener");
        }
    }
}
