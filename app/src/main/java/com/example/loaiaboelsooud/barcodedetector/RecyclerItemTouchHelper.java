package com.example.loaiaboelsooud.barcodedetector;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {
    private RecyclerItemTouchHelperLisetner listener;

    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs, RecyclerItemTouchHelperLisetner listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (listener != null) {
            listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        View foregroundView = ((BarcodeAdapter.Barcode) viewHolder).viewForeground;
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            View foregroundView = ((BarcodeAdapter.Barcode) viewHolder).viewForeground;
            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View foregroundView = ((BarcodeAdapter.Barcode) viewHolder).viewForeground;
        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View foregroundView = ((BarcodeAdapter.Barcode) viewHolder).viewForeground;
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
    }


}
