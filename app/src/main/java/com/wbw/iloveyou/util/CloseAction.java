package com.wbw.iloveyou.util;


import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.wbw.iloveyou.R;
import com.wbw.iloveyou.inter.AllSurfaceView;


public class CloseAction {

    private NotificationManager myNotiManager;


    public CloseAction(final Context context, final AllSurfaceView sv) {
        String firstconfig = SharedPreferencesXml.init().getConfigSharedPreferences("firstconfig", "true");
        if (Boolean.valueOf(firstconfig)) {
            Toast.makeText(context, R.string.tishi,
                    Toast.LENGTH_LONG).show();
        }
        myNotiManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_message)
                .setNegativeButton(R.string.cancle,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                            }
                        })

                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                if (sv != null)
                                    sv.setRun(true);
                                System.exit(0);
                            }
                        })

                .create().show();
    }


}
