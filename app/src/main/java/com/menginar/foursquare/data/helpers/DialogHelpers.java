package com.menginar.foursquare.data.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.view.View;

import com.menginar.foursquare.R;


public final class DialogHelpers {

    private DialogHelpers() {

    }

    public static void showWarnDialog(Activity activity, String message, DialogInterface.OnClickListener buttonClickListener) {

        showDialog(activity, activity.getString(R.string.dialog_title_warn), message,
                false, activity.getString(R.string.dialog_okay_button_text),
                buttonClickListener, null, null, null, null);
    }

    public static void showErrorDialog(Activity activity, String message, DialogInterface.OnClickListener buttonClickListener) {

        showDialog(activity, activity.getString(R.string.dialog_title_error), message, false, activity.getString(R.string.dialog_okay_button_text),
                buttonClickListener, null, null, null, null);
    }

    public static void showInfoDialog(Activity activity, String message, DialogInterface.OnClickListener buttonClickListener) {

        showDialog(activity, activity.getString(R.string.dialog_title_info), message, false, activity.getString(R.string.dialog_okay_button_text),
                buttonClickListener, null, null, null, null);
    }

    public static void showDialog(Activity activity,
                                  String title,
                                  String message,
                                  boolean cancelable,
                                  String positiveButtonText,
                                  DialogInterface.OnClickListener positiveButtonClickListener,
                                  @Nullable String negativeButtonText,
                                  @Nullable DialogInterface.OnClickListener negativeButtonClickListener,
                                  @Nullable String neutralButtonText,
                                  @Nullable DialogInterface.OnClickListener neutralButtonClickListener
    ) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setCancelable(cancelable);

        builder.setTitle(title).setMessage(message).setPositiveButton(positiveButtonText, positiveButtonClickListener);

        if (negativeButtonText != null) {

            builder.setNegativeButton(negativeButtonText, negativeButtonClickListener);
        }

        if (neutralButtonText != null) {

            builder.setNeutralButton(neutralButtonText, neutralButtonClickListener);
        }

        builder.show();
    }
}