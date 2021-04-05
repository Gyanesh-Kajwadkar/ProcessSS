package com.req.processmaster;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.req.processmaster.mytask.DownloadFileFromURL;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    Button btnDownload,goToWeb_btn;
    AlertDialog alertDialog;
    private static final int REQUEST_WRITE_PERMISSION = 108;
    // File url to download
    private static String file_url = "https://salestrip.blob.core.windows.net/tst-container/simpleWebsiteHTMLCSSJavaScricpt.zip";

    String urlOfWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    //requestToReadWrite
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        }
        else
            {
            // starting new Async Task
            new DownloadFileFromURL(MainActivity.this).execute(file_url);
            }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // starting new Async Task
            new DownloadFileFromURL(MainActivity.this).execute(file_url);
        }
    }

    private void initView()
    {

        btnDownload =findViewById(R.id.btnDownload);
        goToWeb_btn =findViewById(R.id.goToWeb_btn);
        alertDialog = new AlertDialog.Builder(MainActivity.this).create();

        btnDownload.setOnClickListener(v -> {

            requestPermission();
        });

        goToWeb_btn.setOnClickListener(view -> {
            Intent intent= new Intent(MainActivity.this,WebViewActivity.class);
            intent.putExtra("webUrlPath",urlOfWeb);
            startActivity(intent);
        });
    }

    //Convert zip
    public void unpackZip(String path, String zipname)
    {
        Toast.makeText(this, "File Save to "+zipname, Toast.LENGTH_SHORT).show();
        InputStream is = null;
        ZipInputStream zis;
        try
        {
            String filename;
            try {
                is = new FileInputStream(path + zipname);
            } catch (Exception e) {
                callAlert("Storage error");
                Log.e("fileSaving",e.getMessage());
            }
            zis = new ZipInputStream(new BufferedInputStream(is));
            ZipEntry ze;
            byte[] buffer = new byte[1024];
            int count;

            while ((ze = zis.getNextEntry()) != null)
            {
                filename = ze.getName();

                //  create directories if not exists
                if (ze.isDirectory())
                {
                    File fmd = new File(path + "/"+filename);
                    fmd.mkdirs();
                    continue;
                }

                String extractFileName=path + "/"+filename;
                String lowercaseName = extractFileName.toLowerCase();
                if (lowercaseName.endsWith(".html"))
                {
                    urlOfWeb=lowercaseName;
                    btnDownload.setVisibility(View.GONE);
                    goToWeb_btn.setVisibility(View.VISIBLE);
                }

                FileOutputStream fout = new FileOutputStream(path +"/"+filename);

                while ((count = zis.read(buffer)) != -1)
                {
                    fout.write(buffer, 0, count);
                }

                fout.close();
                zis.closeEntry();
            }

            zis.close();
        }
        catch(IOException e)
        {
            callAlert("Fail to extract Zip");
            Log.e("zipException",e.getMessage());
        }

    }

    //call alert dialog
    public void callAlert(String message)
    {
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        if(goToWeb_btn.getVisibility()==View.VISIBLE)
        {
            goToWeb_btn.setVisibility(View.GONE);
            btnDownload.setVisibility(View.VISIBLE);
        }
        else {
            super.onBackPressed();
        }


    }
}