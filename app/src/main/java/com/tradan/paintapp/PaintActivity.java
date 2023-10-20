package com.tradan.paintapp;

import android.app.Dialog;
import android.graphics.*;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.*;
import android.os.Bundle;
import android.view.View;
import android.content.DialogInterface;
import android.app.AlertDialog;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.UUID;

public class PaintActivity extends AppCompatActivity implements View.OnClickListener{
    private PaintView v;
    private ImageButton b,drawBtn,eraseBtn,saveBtn;
    private float smallBrush, mediumBrush, largeBrush;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawing);
        v = findViewById(R.id.drawing);
        LinearLayout l = findViewById(R.id.paint_colors);
        b = (ImageButton) l.getChildAt(0);
        b.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed, null));
        //Bitmap bo = (Bitmap) getIntent().getParcelableExtra("key");
        v.setCanvasBitmap(MainActivity.bit);
        v.invalidate();
        //if(bo==null){System.out.print("null");}
        smallBrush = getResources().getInteger(R.integer.small_size);
        mediumBrush = getResources().getInteger(R.integer.medium_size);
        largeBrush = getResources().getInteger(R.integer.large_size);
        drawBtn=(ImageButton)findViewById(R.id.draw_btn);
        drawBtn.setOnClickListener(this);
        v.setBrushSize(mediumBrush);
        eraseBtn=(ImageButton)findViewById(R.id.erase_btn);
        eraseBtn.setOnClickListener(this);
        saveBtn=(ImageButton)findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(this);

        //setContentView(R.layout.drawing);
    }
    @Override
    public void onClick(View view){
        if(view.getId()==R.id.draw_btn){
            final Dialog brushDialog = new Dialog(this);
            brushDialog.setTitle("Brush size:");
            brushDialog.setContentView(R.layout.brush_chooser);
            ImageButton smallBtn = (ImageButton)brushDialog.findViewById(R.id.small_brush);
            smallBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View vi) {
                    v.setBrushSize(smallBrush);
                    v.setLastBrushSize(smallBrush);
                    v.setErase(false);
                    brushDialog.dismiss();
                }
            });
            ImageButton mediumBtn = (ImageButton)brushDialog.findViewById(R.id.medium_brush);
            mediumBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View vi) {
                    v.setBrushSize(mediumBrush);
                    v.setLastBrushSize(mediumBrush);
                    v.setErase(false);
                    brushDialog.dismiss();
                }
            });

            ImageButton largeBtn = (ImageButton)brushDialog.findViewById(R.id.large_brush);
            largeBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View vi) {
                    v.setBrushSize(largeBrush);
                    v.setLastBrushSize(largeBrush);
                    v.setErase(false);
                    brushDialog.dismiss();
                }
            });
            brushDialog.show();
        }
        else if(view.getId()==R.id.erase_btn){
            final Dialog brushDialog = new Dialog(this);
            brushDialog.setTitle("Eraser size:");
            brushDialog.setContentView(R.layout.brush_chooser);
            ImageButton smallBtn = (ImageButton)brushDialog.findViewById(R.id.small_brush);
            smallBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View vi) {
                    v.setErase(true);
                    v.setBrushSize(smallBrush);
                    brushDialog.dismiss();
                }
            });
            ImageButton mediumBtn = (ImageButton)brushDialog.findViewById(R.id.medium_brush);
            mediumBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View vi) {
                    v.setErase(true);
                    v.setBrushSize(mediumBrush);
                    brushDialog.dismiss();
                }
            });
            ImageButton largeBtn = (ImageButton)brushDialog.findViewById(R.id.large_brush);
            largeBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View vi) {
                    v.setErase(true);
                    v.setBrushSize(largeBrush);
                    brushDialog.dismiss();
                }
            });
            brushDialog.show();
        }
        else if(view.getId()==R.id.save_btn){
            AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
            saveDialog.setTitle("Save drawing");
            saveDialog.setMessage("Save drawing to device Gallery?");
            saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    v.setDrawingCacheEnabled(true);
                    String imgSaved = MediaStore.Images.Media.insertImage(
                            getContentResolver(), v.getDrawingCache(),
                            UUID.randomUUID().toString()+".png", "drawing");
                    if(imgSaved!=null){
                        Toast savedToast = Toast.makeText(getApplicationContext(),
                                "Drawing saved to Gallery!", Toast.LENGTH_SHORT);
                        savedToast.show();
                    }
                    else{
                        Toast unsavedToast = Toast.makeText(getApplicationContext(),
                                "Oops! Image could not be saved.", Toast.LENGTH_SHORT);
                        unsavedToast.show();
                    }
                    v.destroyDrawingCache();
                }
            });
            saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which){
                    dialog.cancel();
                }
            });
            saveDialog.show();
        }
    }
    public void paintClicked(View view)
    {
        v.setErase(false);
        if(view!=b)
        {
            ImageButton imgView = (ImageButton)view;
            String color = view.getTag().toString();
            v.setColor(color);
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed,null));
            b.setImageDrawable(getResources().getDrawable(R.drawable.paint,null));
            b=(ImageButton)view;
        }
    }
}