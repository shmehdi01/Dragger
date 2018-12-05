package library.shmehdi.dragger;

import android.graphics.Rect;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

public class Dragger {

    private View dragView;
    private View[] targetViews;
    private float dX, dY;
    private int initXY[] = new int[2];
    private boolean isInitXy = false;
    private boolean eventSuccess = false;
    private DragEventListener dragEventListener;
    private View view;


    public Dragger(View dragView, View[] targetViews) {
        this.dragView = dragView;
        this.targetViews = targetViews;
    }

    public Dragger() {
    }

    public static Dragger create(){
        return new Dragger();
    }

    public Dragger setDragView(View dragView) {
        this.dragView = dragView;
        return this;
    }

    public Dragger setTargetViews(View[] targetViews) {
        this.targetViews = targetViews;
        return this;
    }

    public Dragger setDragEventListener(DragEventListener dragEventListener) {
        this.dragEventListener = dragEventListener;
        return this;
    }

    public void startDragging(){

        if(dragView == null){
            return;
        }

        if(targetViews == null){
            return;
        }

        dragView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:

                        dX = dragView.getX() - event.getRawX();
                        dY = dragView.getY() - event.getRawY();

                        if(!isInitXy) {
                            dragView.getLocationInWindow(initXY);
                            isInitXy = true;
                        }
                        break;

                    case MotionEvent.ACTION_MOVE:

                        dragView.animate()
                                .x(event.getRawX() + dX)
                                .y(event.getRawY() + dY)
                                .setDuration(0)
                                .start();

                        for(int i=0;i<targetViews.length;i++) {
                            if (isViewInBounds(targetViews[i], event.getRawX(), event.getRawY())) {
                                Dragger.this.view = targetViews[i];
                                eventSuccess = true;
                                break;
                            }
                            else {
                                eventSuccess = false;
                            }
                        }

                        break;

                    case MotionEvent.ACTION_UP:
                        if(!eventSuccess) {
                            dragView.animate()
                                    .x(initXY[0])
                                    .y(initXY[1])
                                    .setDuration(0)
                                    .start();
                        }else {
                            if(dragEventListener != null)
                                dragEventListener.onDragComplete(view);
                        }
                    default:
                        return false;
                }
                return true;
            }
        });
    }


    private Rect rect = new Rect();
    private int[] location = new int[2];

    private boolean isViewInBounds(View target, float x, float y){
        target.getDrawingRect(rect);
        target.getLocationOnScreen(location);
        rect.offset(location[0], location[1]);
        return rect.contains((int) x, (int)y);
    }


    public interface DragEventListener{
        public void onDragComplete(View targetView);
    }

}
