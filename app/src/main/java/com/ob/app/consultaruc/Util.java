package com.ob.app.consultaruc;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by jc on 13/04/17.
 */

public class Util {

    public static void hideKeyboard(View v) {
        try {
            v.clearFocus();
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        } catch (Exception e) {
            // we all saw shit happening on this code before
        }
    }

    public static final String getString(byte[] response) throws JSONException {
        String values = Arrays.toString(response);
        String[] byteValues = values.substring(1, values.length() - 1).split(",");
        byte[] bytes = new byte[byteValues.length];

        for (int i=0, len=bytes.length; i<len; i++) {
            bytes[i] = Byte.parseByte(byteValues[i].trim());
        }

        return new String(bytes);
    }

    public static final JSONObject getJson(byte[] response) throws JSONException {
        return new JSONObject(getString(response));
    }
}
