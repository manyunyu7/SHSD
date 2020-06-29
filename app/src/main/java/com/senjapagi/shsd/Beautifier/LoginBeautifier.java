package com.senjapagi.shsd.Beautifier;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.senjapagi.shsd.R;

public class LoginBeautifier {
    Context mContext;
    View view;

    public LoginBeautifier(Context mContext, View view) {
        this.mContext = mContext;
        this.view = view;
    }

    public void animFirst(){
        MediaPlayer.create(mContext, R.raw.intro).start();
        view.findViewById(R.id.tv_cred).setVisibility(View.GONE);
        view.findViewById(R.id.logohmsi).setVisibility(View.GONE);
        view.findViewById(R.id.tv_fakultas).setVisibility(View.GONE);
        view.findViewById(R.id.tv_ead).setVisibility(View.GONE);
        view.findViewById(R.id.btn_register).setVisibility(View.GONE);

        view.findViewById(R.id.fab_login).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.rl_input).setVisibility(View.INVISIBLE);

        view.findViewById(R.id.tv_cred).setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation_slowly));
        view.findViewById(R.id.logohmsi).setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.item_animation_falldown_slowly));
        view.findViewById(R.id.tv_fakultas).setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation_slowly));
        view.findViewById(R.id.btn_register).setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation));
        view.findViewById(R.id.tv_ead).setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation_slowly));
        view.findViewById(R.id.tv_cred).setVisibility(View.VISIBLE);
        view.findViewById(R.id.logohmsi).setVisibility(View.VISIBLE);
        view.findViewById(R.id.tv_fakultas).setVisibility(View.VISIBLE);
        view.findViewById(R.id.tv_ead).setVisibility(View.VISIBLE);
        view.findViewById(R.id.btn_register).setVisibility(View.VISIBLE);
    }

    public void animSecond(){
        view.findViewById(R.id.btn_forgot_password).setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation_go));
        view.findViewById(R.id.btn_forgot_password).setVisibility(View.GONE);view.findViewById(R.id.rl_input).setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation_go));
        view.findViewById(R.id.rl_input).setVisibility(View.GONE);
        view.findViewById(R.id.fab_login).setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation_go));
        view.findViewById(R.id.fab_login).setVisibility(View.GONE);
        view.findViewById(R.id.btn_register).setVisibility(View.GONE);
        view.findViewById(R.id.btn_register).setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation_go));
        view.findViewById(R.id.logohmsi).setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation_go));
        view.findViewById(R.id.logohmsi).setVisibility(View.GONE);
        view.findViewById(R.id.tv_cred).setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation_go));
        view.findViewById(R.id.tv_cred).setVisibility(View.GONE);
        view.findViewById(R.id.tv_fakultas).setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation_go));
        view.findViewById(R.id.tv_fakultas).setVisibility(View.GONE);
        view.findViewById(R.id.tv_ead).setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation_go));
        view.findViewById(R.id.tv_ead).setVisibility(View.GONE);
    }
}
