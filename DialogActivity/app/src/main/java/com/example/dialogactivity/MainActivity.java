package com.example.dialogactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button dialogBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialogBtn=findViewById(R.id.dialogBtn);

        dialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    private void openDialog()
    {
        final Dialog dialog = new Dialog(this);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width=WindowManager.LayoutParams.MATCH_PARENT;
        lp.height= WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity=Gravity.CENTER;
        lp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button okButton= dialog.findViewById(R.id.ok_btn);
        Button cancelButton= dialog.findViewById(R.id.cancel_btn);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setAttributes(lp);

    }
}
