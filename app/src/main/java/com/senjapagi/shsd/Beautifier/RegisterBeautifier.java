package com.senjapagi.shsd.Beautifier;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.senjapagi.shsd.ActivityGeneral.MainActivity;
import com.senjapagi.shsd.R;

import java.util.logging.LogRecord;

public class RegisterBeautifier extends AppCompatActivity{

    Context mContext;
    View view ;

    public RegisterBeautifier(Context mcontext, View view) {
        this.mContext = mcontext;
        this.view = view;
    }
    public void animFirst(){
        view.findViewById(R.id.lyt_additional_login).setVisibility(View.GONE);
        view.findViewById(R.id.container_profile_pic).setVisibility(View.GONE);
        view.findViewById(R.id.lyt_additional_login).setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));
        view.findViewById(R.id.lyt_additional_login).setVisibility(View.VISIBLE);
        view.findViewById(R.id.container_profile_pic).setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.item_animation_falldown));
        view.findViewById(R.id.container_profile_pic).setVisibility(View.VISIBLE);
    }
    public void animGone(){
        view.findViewById(R.id.lyt_additional_login).setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation_go));
        view.findViewById(R.id.lyt_additional_login).setVisibility(View.GONE);
        view.findViewById(R.id.container_profile_pic).setVisibility(View.GONE);
        view.findViewById(R.id.container_profile_pic).setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.item_animation_fallup));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent a = new Intent(mContext,MainActivity.class);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(a);
                finish();
            }
        },1000);

    }

}
