package com.ob.app.consultaruc;

import com.loopj.android.http.*;

public class HttpUtil {

    private static final String BASE_URL = "http://www.winwaresac.com:2385/new/{ruc}/8a2e8dd9e201f609e304e5e2bb5b5a09";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static final void get(String ruc, AsyncHttpResponseHandler responseHandler) {
        String url = BASE_URL.replace("{ruc}", ruc);
        client.get(url, responseHandler);
    }
}
