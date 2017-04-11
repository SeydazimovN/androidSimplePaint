package com.example.seydazimovnurbol.pencilofpaint;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import petrov.kristiyan.colorpicker.ColorPicker;

public class MainActivity extends AppCompatActivity {


    private CanvasView canvasView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        canvasView = (CanvasView) findViewById(R.id.canvas);

        // Color Picker
        final Button fab = (Button) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final ColorPicker colorPicker = new ColorPicker(MainActivity.this);
                    ArrayList<String> colors = new ArrayList<>();
                    colors.add("#82B926");
                    colors.add("#a276eb");
                    colors.add("#6a3ab2");
                    colors.add("#666666");
                    colors.add("#FFFF00");
                    colors.add("#3C8D2F");
                    colors.add("#FA9F00");
                    colors.add("#FF0000");

                    colorPicker.setColors(colors).setDefaultColorButton(Color.parseColor("#f84c44")).setColumns(5).setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                        @Override
                        public void onChooseColor(int position, int color) {
                            Log.d("position",""+position);// will be fired only when OK button was tapped
                            canvasView.currentColor = color;
                        }

                        @Override
                        public void onCancel() {

                        }
                    }).addListenerButton("newButton", new ColorPicker.OnButtonListener() {
                        @Override
                        public void onClick(View v, int position, int color) {
                            Log.d("position",""+position);
                        }
                    }).setRoundColorButton(true).show();
                }
            });
        }


    }

    public void clearCanvas(View v) {
        canvasView.clearCanvas();
    }


}
