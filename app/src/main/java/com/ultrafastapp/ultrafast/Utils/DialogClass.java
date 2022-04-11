package com.ultrafastapp.ultrafast.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class DialogClass {
        private Activity activity;
        private AlertDialog dialog;

        public DialogClass(Activity miactivity)
        {
            activity=miactivity;

        }
        public void startDialog()
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(activity);
            LayoutInflater inflater=activity.getLayoutInflater();
            builder.setCancelable(false);

            dialog=builder.create();
            dialog.show();
        }
        public void atopDialog()
        {
            dialog.dismiss();

        }
}
