package com.hammollc.dotapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    WebView webview;
    ProgressBar pbar;

    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        public void onPageFinished(WebView view, String url) {
            view.evaluateJavascript("(function() { return 'this'; })();", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
                    Log.i("AHHH", s); // Prints: "this"
                    pbar.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setVars();
//        final EditText dot = findViewById(R.id.eText);
//        Button btn = findViewById(R.id.dotBtn);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String dotNum = dot.getText().toString();
//
//                getDotInfo(dotNum);
//                try {
//                    Log.i("AHHH", "Here");
//                    String url;
//                    url = "https://safer.fmcsa.dot.gov/query.asp?searchtype=ANY&query_type=queryCarrierSnapshot&query_param=USDOT&query_string="+dotNum;
//
//                    Document doc = Jsoup.connect(url).get();
//                    Log.i("AHHH",doc.title());
//                } catch (Exception e) {
//                    Log.i("AHHH", "Caught\n"+e.toString());
//                    e.printStackTrace();
//                }

//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                builder.setTitle("DOT Num");
//                builder.setMessage(dotNum);
//
//                builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//
//                builder.create().show();
//            }
//        });
    }

    private void setVars() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final EditText date = findViewById(R.id.eText);
        final Button btn = findViewById(R.id.dotBtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadMcData(date, btn);
            }
        });

        setDate(date);
    }

    private void setDate(final EditText dateEditText) {
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(dateEditText, myCalendar);
            }

        };

        dateEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(EditText editText, Calendar myCalendar) {
        String myFormat = "dd-MMM-yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        String text = sdf.format(myCalendar.getTime());
        text = text.substring(0,4)+text.substring(4,7).toUpperCase()+text.substring(7);
        editText.setText(text);
    }

    private void downloadMcData(final EditText date, final Button btn) {
        date.setVisibility(View.GONE);
        btn.setVisibility(View.GONE);

        pbar = findViewById(R.id.progress);
        pbar.setVisibility(View.VISIBLE);

        String dateString = date.getText().toString();
        String urlParameters = "pd_date="+dateString+"&pv_vpath=LIVIEW";
        String url = "https://li-public.fmcsa.dot.gov/LIVIEW/PKG_register.prc_reg_detail?"+urlParameters;

        webview = findViewById(R.id.webView);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.addJavascriptInterface(new MyJavaScriptInterface(this), "HtmlViewer");

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                webview.loadUrl("javascript:window.HtmlViewer.showHTML" +
                        "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
            }
        });

        webview.loadUrl(url);
//        wb = findViewById(R.id.webView);
//        wb.getSettings().setJavaScriptEnabled(true);
//        wb.getSettings().setLoadWithOverviewMode(true);
//        wb.getSettings().setUseWideViewPort(true);
//        wb.getSettings().setBuiltInZoomControls(true);
//        wb.getSettings().setPluginState(WebSettings.PluginState.ON);
//        wb.getSettings().setPluginsEnabled(true);
//        wb.setWebViewClient(new HelloWebViewClient());
//        wb.loadUrl(url);
//        byte[] b = urlParameters.getBytes(StandardCharsets.UTF_8);
//
//        try {
//            URL url;
//            url = new URL("http://li-public.fmcsa.dot.gov/LIVIEW/PKG_register.prc_reg_detail");
//            URLConnection connection;
//            connection = url.openConnection();
//            HttpURLConnection httppost = (HttpURLConnection) connection;
//            httppost.setDoInput(true);
//            httppost.setDoOutput(true);
//            httppost.setRequestMethod("POST");
//            httppost.setRequestProperty("User-Agent", "Tranz-Version-t1.914");
//            httppost.setRequestProperty("Accept_Language", "en-US");
//            httppost.setRequestProperty("Content-Type",
//                    "application/x-www-form-urlencoded");
//            DataOutputStream dos = new DataOutputStream(httppost.getOutputStream());
//            dos.write(b); // bytes[] b of post data
//
//            String reply = "Un-updated";
//            InputStream in = httppost.getInputStream();
//            StringBuffer sb = new StringBuffer();
//
//            try {
//                int chr;
//                while ((chr = in.read()) != -1) {
//                    sb.append((char) chr);
//                }
//                reply = sb.toString();
//            } finally {
//                in.close();
//                Log.i("AHHH", reply);
//
//                pbar.setVisibility(View.GONE);
//                date.setVisibility(View.VISIBLE);
//                btn.setVisibility(View.VISIBLE);
//            }
//        } catch (MalformedURLException ee) {
//            Log.i("AHHH", "Malformed URL: " + ee.getMessage());
//        }
//        catch (IOException eee) {
//            Log.i("AHHH", "I/O Error: " + eee.getMessage());
//        }
    }

    class MyJavaScriptInterface {

        private Context ctx;

        MyJavaScriptInterface(Context ctx) {
            this.ctx = ctx;
        }

        @JavascriptInterface
        public void showHTML(String html) {
//            new AlertDialog.Builder(ctx).setTitle("HTML").setMessage(html)
//                    .setPositiveButton(android.R.string.ok, null).setCancelable(false).create().show();
            Log.i("AHHH", html);
        }

    }
}
