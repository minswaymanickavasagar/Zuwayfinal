package com.ZuWay.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.ZuWay.R;

import java.util.Arrays;
import java.util.List;


import it.sephiroth.android.library.imagezoom.ImageViewTouch;


public class ViewZoomActivity extends AppCompatActivity {
   private List<Note> notes;
    private SubsamplingScaleImageView imageView;
    private TextView note;
    private int position = 0;
    private View btnNext, btnPrev;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_zoom);


        Toast.makeText(this, "ImageViewTouch.VERSION: " + ImageViewTouch.VERSION, Toast.LENGTH_SHORT).show();
        ActivityCompat.requestPermissions(ViewZoomActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},  1);

        note = (TextView) findViewById(R.id.note);
        btnNext = findViewById(R.id.next);
        btnPrev = findViewById(R.id.previous);
        locateImageView();
        btnPrev.setOnClickListener(onClickListener(0));
        btnNext.setOnClickListener(onClickListener(1));

        notes = Arrays.asList(
                new Note("Pinch to zoom", "Use two finger pinch to zoom in and out. The zoom is centred on the pinch gesture, and you can pan at the same time."),
                new Note("Quick scale", "Double tap and swipe up or down to zoom in or out. The zoom is centred where you tapped."),
                new Note("Drag", "Use one finger to drag the image around."),
                new Note("Fling", "If you drag quickly and let go, fling momentum keeps the image moving."),
                new Note("Double tap", "Double tap the image to zoom in to that spot. Double tap again to zoom out.")
        );
        note.setText(notes.get(position).subtitle + ": " + notes.get(0).text);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.rotate) {
            imageView.setOrientation((imageView.getOrientation() + 90) % 360);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetTextI18n")
    private View.OnClickListener onClickListener(final int i) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == btnNext) {
                    if (position < notes.size() - 1) {
                        position++;
                        note.setText(notes.get(position).subtitle + ": " + notes.get(position).text);
                        Toast.makeText(ViewZoomActivity.this, "Next tip!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (position > 0) {
                        position--;
                        Toast.makeText(ViewZoomActivity.this, "Previous tip!", Toast.LENGTH_SHORT).show();
                    }
                    note.setText(notes.get(position).subtitle + ": " + notes.get(position).subtitle + ": " + notes.get(position).text);
                }
            }
        };
    }

    private void locateImageView() {
        imageView = (SubsamplingScaleImageView) findViewById(R.id.image);
        imageView.setImage(ImageSource.resource(R.drawable.logo_200));    }


    private class Note {
        private final String text;
        private final String subtitle;

        public Note(String subtitle, String text) {
            this.subtitle = subtitle;
            this.text = text;
        }
    }
}
