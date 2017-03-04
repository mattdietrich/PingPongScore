package ca.mattdietrich.pingpongscore;

import android.content.Context;
import android.support.v7.app.AlertDialog;

public class Utility {

    public static void showDialog(Context context, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.ok, null);
        builder.create();
        builder.show();
    }
}
