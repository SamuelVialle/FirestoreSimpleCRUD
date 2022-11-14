package com.samuelvialle.firestoresimplecrud;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class TouchHelper extends ItemTouchHelper.SimpleCallback {

    Context context;
    private AdpaterNotes adapter;
//    private Drawable iconDelete;
//    private Drawable iconDelete = ContextCompat.getDrawable(context, R.drawable.ic_baseline_delete_24);
    private Bitmap icon;

    public TouchHelper(AdpaterNotes adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }

    private final int paintColor = Color.BLACK;

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();
        if(direction == ItemTouchHelper.LEFT){
            Log.i("TAG", "onSwiped: " + position + " " + direction );
            adapter.updateData(position);
            adapter.notifyDataSetChanged();
        } else {
            adapter.deleteData(position);
        }
    }
// TODO Find a suitable way to show icon into item

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        final int DIRECTION_LEFT = 1;
        final int DIRECTION_RIGHT = 0;

        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE && isCurrentlyActive){
            int direction = dX > 0 ? DIRECTION_RIGHT : DIRECTION_LEFT;
            int absoluteDisplacement = Math.abs((int) dX);

            View itemView = viewHolder.itemView;
            ColorDrawable bg = new ColorDrawable();
            switch (direction){
                case DIRECTION_RIGHT:
                    bg.setColor(Color.RED);
                    bg.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getRight(), itemView.getBottom());
                    bg.draw(c);

//                    iconDelete.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getRight(), itemView.getBottom());
//                    iconDelete.draw(c);
                    break;
                case DIRECTION_LEFT:
                    bg.setColor(Color.YELLOW);
                    bg.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getRight(), itemView.getBottom());
                    bg.draw(c);

//                    int itemTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
//                    int itemMargin = (itemHeight - intrinsicHeight) / 2;
//                    int itemLeft = itemView.getLeft() + itemMargin;
//                    int itemRight = itemView.getLeft() + itemMargin + intrinsicWidth;
//                    int itemBottom = itemTop + intrinsicHeight;
//                    swipeRightIcon.setBounds(itemLeft, itemTop, itemRight, itemBottom);
//                    swipeRightIcon.draw(c);

//                    iconDelete.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getRight(), itemView.getBottom());
//                    iconDelete.draw(c);
                    break;
            }

            bg.draw(c);
        }
    }
}
