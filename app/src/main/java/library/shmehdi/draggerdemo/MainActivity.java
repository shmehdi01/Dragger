package library.shmehdi.draggerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import library.shmehdi.dragger.Dragger;

public class MainActivity extends AppCompatActivity implements Dragger.DragEventListener {

    private Button dragBtn, targetOne, targetTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dragBtn = findViewById(R.id.btn1);
        targetOne = findViewById(R.id.btn2);
        targetTwo = findViewById(R.id.btn3);


        Dragger.create()
                .setDragView(dragBtn)
                .setTargetViews(new View[]{targetOne, targetTwo})
                .setDragEventListener(this)
                .startDragging();

    }

    @Override
    public void onDragComplete(View targetView) {
        if (targetView.equals(targetOne)) {
            Toast.makeText(this, "Target 1 complete", Toast.LENGTH_SHORT).show();
        } else if (targetView.equals(targetTwo)) {
            Toast.makeText(this, "Target 2 complete", Toast.LENGTH_SHORT).show();
        }
    }
}
