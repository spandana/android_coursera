package com.example.puppy.modernartui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends Activity {

    final String MTAG = "MUI";
    SeekBar mseekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mseekBar = (SeekBar) findViewById(R.id.seekBar);

        LinearLayout rectangleLayout = (LinearLayout) findViewById(R.id.rectangles_area_layout);
        LinearLayout verticalStripLayout;
        TextView horizontalBoxLayout;

        //List of all textviews on the screen
        final ArrayList<TextView> gridsList = new ArrayList<>();

        //List of initial colors of each textview on the screen
        final ArrayList<Integer> gridsColorList = new ArrayList<>();

        int numrows = 10, numcols = 7;
        for (int i = 0; i < numrows; i++) {
            verticalStripLayout = new LinearLayout(this);
            rectangleLayout.addView(verticalStripLayout);
            verticalStripLayout.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            verticalStripLayout.setOrientation(LinearLayout.VERTICAL);

            if (i % 2 == 0) {
                numcols = 7;
            } else {
                numcols = 6;
                horizontalBoxLayout = new TextView(this);
                verticalStripLayout.addView(horizontalBoxLayout);
                horizontalBoxLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
                gridsList.add(horizontalBoxLayout);
            }

            for (int j = 0; j < numcols; j++) {
                horizontalBoxLayout = new TextView(this);
                verticalStripLayout.addView(horizontalBoxLayout);
                horizontalBoxLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 2));
                gridsList.add(horizontalBoxLayout);
            }

            if (i % 2 != 0) {
                horizontalBoxLayout = new TextView(this);
                verticalStripLayout.addView(horizontalBoxLayout);
                horizontalBoxLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
                gridsList.add(horizontalBoxLayout);
            }
        }

        //Assign initial colors to the textviews using random number generation
        Random r = new Random(0);
        int red, green, blue;
        for (TextView temp : gridsList) {
            red = r.nextInt() % 255;
            green = r.nextInt() % 255;
            blue = r.nextInt() % 255;
            temp.setBackgroundColor(Color.rgb(red, green, blue));
            gridsColorList.add(Color.rgb(red, green, blue));
        }
        //Requirement: Atleast one rectangle should have white/grey color
        gridsList.get(0).setBackgroundColor(Color.WHITE);


        mseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int currentProgressValue = progress;
                int colorIncrement = currentProgressValue * 2;

                for (int i = 1; i < gridsList.size(); i++) {
                    TextView curr = gridsList.get(i);
                    int color = gridsColorList.get(i);
                    int redTemp = Color.red(color);
                    int greenTemp = Color.green(color);
                    int blueTemp = Color.blue(color);

                    curr.setBackgroundColor(Color.rgb((redTemp + colorIncrement) % 256,
                            (greenTemp + colorIncrement) % 256, (blueTemp + colorIncrement) % 256));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_more_information) {
            DialogFragment myFragment = new MyDialogFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.addToBackStack(null);
            myFragment.show(ft, "dialog");
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
