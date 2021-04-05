package com.andoidproject.culluze_app.Activity.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andoidproject.culluze_app.R;
import com.google.android.gms.vision.text.Line;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReportBugActivity extends Base_Activity implements View.OnClickListener {
    private  String[] synch = {"Synch Screen", "Report Screen", "DashBoard Screen","Customers Screen","Order Screen","Bill Screen"};
    Spinner synchScreen;
    EditText description_et;
    LinearLayout bug_ll;
    TextInputLayout description_input;
    ImageView back_img;
    View name_view;
    RecyclerView reportBug_recycelr;
    LinearLayout gallery_pick_ll;
    ReportBug_Adapter adapt;
    TextView save_tv;

    ArrayList<String>galleryImg= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_bug);
        initView();
    }
    
    public void initView(){
        reportBug_recycelr=findViewById(R.id.reportBug_recycelr);
        synchScreen=findViewById(R.id.synchScreen);
        description_et=findViewById(R.id.description_et);
        description_input=findViewById(R.id.description_input);
        bug_ll=findViewById(R.id.bug_ll);
        back_img=findViewById(R.id.back_img);
        name_view=findViewById(R.id.name_view);
        gallery_pick_ll=findViewById(R.id.gallery_pick_ll);
        save_tv=findViewById(R.id.save_tv);

        bug_ll.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                bug_ll.setFocusable(true);
                bug_ll.setFocusableInTouchMode(true);

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ReportBugActivity.this,
                android.R.layout.simple_spinner_item,synch);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        synchScreen.setAdapter(adapter);
        adapter.getItemViewType(0);

        description_et.setTextIsSelectable(true);

        back_img.setOnClickListener(this);
        gallery_pick_ll.setOnClickListener(this);
        save_tv.setOnClickListener(this);


        description_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    name_view.setElevation(2f);
                    lineAnimation(name_view);
                }
                else {

                    Reverse_lineAnimation(name_view);
                } }});

        reportBug_recycelr.setLayoutManager(new LinearLayoutManager(this));
        reportBug_recycelr.setItemAnimator(new DefaultItemAnimator());
        adapt= new ReportBug_Adapter(galleryImg);
        reportBug_recycelr.setAdapter(adapt);
    }

    @Override
    public void onClick(View v) {


        if(v==back_img)
        {
            onBackPressed();
        }

        if(gallery_pick_ll==v)
        {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"),0);
        }

        if(v==save_tv)
        {
            new AlertDialog.Builder(this).setTitle("Description is required")
                    .setMessage("Please provide a description")
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    // Perform Action & Dismiss dialog
                                    dialog.dismiss();
                                }
                            })
                    .create()
                    .show();
        }


    }

    public class ReportBug_Adapter extends RecyclerView.Adapter<ReportBug_Adapter.ViewHolders> {

        ArrayList<String>gallaryImages;

        public ReportBug_Adapter(ArrayList<String>gallaryImages)
        {
            this.gallaryImages= gallaryImages;
        }

        @NonNull
        @Override
        public ReportBug_Adapter.ViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reportbug_view,viewGroup,false);
            return new ViewHolders(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ReportBug_Adapter.ViewHolders viewHolders, final int i) {

            String str= gallaryImages.get(i);
            File imgFile = new  File(str);
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
               viewHolders.gallery_iv.setImageBitmap(myBitmap);
            }

            viewHolders.remove_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gallaryImages.remove(viewHolders.getAdapterPosition());
                    adapt.notifyDataSetChanged();
                }
            });

        }

        @Override
        public int getItemCount() {
            return gallaryImages.size();
        }

        public class ViewHolders extends RecyclerView.ViewHolder {

            ImageView remove_iv,gallery_iv;
            public ViewHolders(@NonNull View itemView) {
                super(itemView);
                remove_iv=itemView.findViewById(R.id.remove_iv);
                gallery_iv=itemView.findViewById(R.id.gallery_iv);
            }
        }
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        Uri selectedImageURI = data.getData();
                       String picturePath= getRealPathFromURI(selectedImageURI);
                        Log.e("Picture Path", picturePath);
                        galleryImg.add(picturePath);
                        adapt.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)  {
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getRealPathFromURI(Uri uri) {
        String filePath = "";

        Pattern p = Pattern.compile("(\\d+)$");
        Matcher m = p.matcher(uri.toString());
        if (!m.find()) {
            return filePath;
        }
        String imgId = m.group();

        String[] column = { MediaStore.Images.Media.DATA };
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{ imgId }, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();

        return filePath;
    }

}
