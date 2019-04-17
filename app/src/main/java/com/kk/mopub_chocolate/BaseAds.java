package com.kk.mopub_chocolate;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

class BaseAds {

    protected Context context;
    protected Handler handler = new Handler(Looper.getMainLooper());
    private TextView logView;

    BaseAds(Context context, TextView logView) {
        this.context = context;
        this.logView = logView;
    }

    protected void log(final String msg) {
        handler.post(() -> {
            logView.getEditableText().append(msg + "\r");
        });
    }

    protected void alert(final String msg) {
        handler.post(() -> new AlertDialog.Builder(context).setMessage(msg).create().show());
    }

    protected void toast(final String msg) {
        handler.post(() -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show());
    }
}
