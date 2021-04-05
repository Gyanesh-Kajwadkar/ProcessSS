package com.andoidproject.culluze_app.Activity.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.andoidproject.culluze_app.R;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;


public class Qr_Fragment extends Fragment {

    private final String TAG = "scanner";
    private DecoratedBarcodeView barcodeView;
    private BeepManager beepManager;
    private String lastText="x";
    TextView showMessage;
    Button submit_btn;
    ImageView back_image;
    Spinner attendanceSpinner,status_spin;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_qr_, container, false);
    return view;
    }

}
