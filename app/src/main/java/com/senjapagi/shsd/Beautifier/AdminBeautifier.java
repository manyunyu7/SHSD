package com.senjapagi.shsd.Beautifier;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.senjapagi.shsd.R;

public class AdminBeautifier {

    Context mContext;
    View view;
    Animation animation1,animation2,animation3;

    public AdminBeautifier(Context mContext, View view) {
        this.mContext = mContext;
        this.view = view;
        animation1 = AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation3);
        animation2 = AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation_flip);
        animation3 = AnimationUtils.loadAnimation(mContext,R.anim.item_animation_falldown);
    }

    public void menuGone() {
        view.findViewById(R.id.containerAnim1).setVisibility(View.GONE);
        view.findViewById(R.id.containerAnim2).setVisibility(View.GONE);
        view.findViewById(R.id.containerAnim3).setVisibility(View.GONE);
        view.findViewById(R.id.containerAnim4).setVisibility(View.GONE);
        view.findViewById(R.id.containerAnim5).setVisibility(View.GONE);
        view.findViewById(R.id.containerAnim6).setVisibility(View.GONE);
        view.findViewById(R.id.containerAnim7).setVisibility(View.GONE);
        view.findViewById(R.id.containerHelpdesk).setVisibility(View.GONE);
        view.findViewById(R.id.containerAnim9).setVisibility(View.GONE);
    }

    public void menuAppear(){
        view.findViewById(R.id.containerAnim1).setVisibility(View.VISIBLE);
        view.findViewById(R.id.containerAnim1).setAnimation(animation3);
        view.findViewById(R.id.containerAnim2).setVisibility(View.VISIBLE);
        view.findViewById(R.id.containerAnim2).setAnimation(animation1);
        view.findViewById(R.id.containerAnim3).setVisibility(View.VISIBLE);
        view.findViewById(R.id.containerAnim3).setAnimation(animation1);
        view.findViewById(R.id.containerAnim4).setVisibility(View.VISIBLE);
        view.findViewById(R.id.containerAnim4).setAnimation(animation1);
        view.findViewById(R.id.containerAnim5).setVisibility(View.VISIBLE);
        view.findViewById(R.id.containerAnim5).setAnimation(animation2);
        view.findViewById(R.id.containerAnim6).setVisibility(View.VISIBLE);
        view.findViewById(R.id.containerAnim6).setAnimation(animation2);
        view.findViewById(R.id.containerAnim7).setVisibility(View.VISIBLE);
        view.findViewById(R.id.containerAnim7).setAnimation(animation2);
        view.findViewById(R.id.containerHelpdesk).setVisibility(View.VISIBLE);
        view.findViewById(R.id.containerHelpdesk).setAnimation(animation2);
        view.findViewById(R.id.containerAnim9).setVisibility(View.VISIBLE);
        view.findViewById(R.id.containerAnim9).setAnimation(animation2);
    }
}
