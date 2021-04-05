package com.example.edittext_animation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText name_et,password_et;
    View name_viewLine, password_viewLine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name_et=findViewById(R.id.name_et);
        password_et=findViewById(R.id.password_et);
        name_viewLine=findViewById(R.id.name_viewLine);
        password_viewLine=findViewById(R.id.password_viewLine);


        name_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                {
                  name_viewLine.setElevation(2);
                  lineAnimation(name_viewLine);
                }
                else
                {
                    reverseAnimation(name_viewLine);
                }
            }
        });


        password_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                {
                    password_viewLine.setElevation(2);
                    lineAnimation(password_viewLine);
                }
                else {
                    reverseAnimation(password_viewLine);
                }
            }
        });


    }

    public void lineAnimation(View view)
    {
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.center_left);
        Animation animationTwo=AnimationUtils.loadAnimation(this,R.anim.center_right);
        AnimationSet sets=new AnimationSet(false);
        sets.addAnimation(animation);
        sets.addAnimation(animationTwo);
        view.startAnimation(sets);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void reverseAnimation(final View view)
    {
        Animation animationOne=AnimationUtils.loadAnimation(this,R.anim.left_center);
        Animation animationTwo=AnimationUtils.loadAnimation(this,R.anim.right_center);

        AnimationSet set=new AnimationSet(false);
        set.addAnimation(animationOne);
        set.addAnimation(animationTwo);
        view.startAnimation(set);

        animationOne.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {

                view.setElevation(-2f);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animationTwo.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
            view.setElevation(-2f);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }



}
