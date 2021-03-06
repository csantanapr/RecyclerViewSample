package com.twotoasters.recycled.animator;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;

import com.twotoasters.recycled.ReViewHolder;

public class FlyItemAnimator extends PendingItemAnimator<ReViewHolder> {

    public FlyItemAnimator() {
        setAddDuration(1000);
        setRemoveDuration(500);
        setMoveDuration(500);
    }

    @Override
    protected boolean prepHolderForAnimateRemove(ReViewHolder holder) {
        return true;
    }

    protected ViewPropertyAnimatorCompat animateRemoveImpl(ReViewHolder holder) {
        final View view = holder.itemView;
        ViewCompat.animate(view).cancel();
        final int width = getWidth(holder);
        return ViewCompat.animate(view)
                .translationXBy(width)
                .setInterpolator(new AnticipateOvershootInterpolator());
    }

    @Override
    protected void onRemoveCanceled(ReViewHolder holder) {
        ViewCompat.setTranslationX(holder.itemView, 0);
    }

    @Override
    protected boolean prepHolderForAnimateAdd(ReViewHolder holder) {
        int width = getWidth(holder);
        ViewCompat.setTranslationX(holder.itemView, width);
        return true;
    }

    protected ViewPropertyAnimatorCompat animateAddImpl(ReViewHolder holder) {
        final View view = holder.itemView;
        ViewCompat.animate(view).cancel();
        int width = getWidth(holder);
        return ViewCompat.animate(view)
                .translationXBy(-width)
                .setInterpolator(new BounceInterpolator());
    }

    @Override
    protected void onAddCanceled(ReViewHolder holder) {
        ViewCompat.setTranslationX(holder.itemView, 0);
    }

    public int getWidth(ViewHolder holder) {
        return getWidth(holder.itemView);
    }

    public int getWidth(View itemView) {
        return itemView.getMeasuredWidth() + itemView.getPaddingRight() + ((LayoutParams) itemView.getLayoutParams()).rightMargin;
    }
}