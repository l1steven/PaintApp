package com.tradan.paintapp;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Fragment;
import android.graphics.*;

import java.io.IOException;

public class GalleryFragment extends Fragment{
    private Button b;
    private Uri uri;
    private ImageView img;
    private View view;
    private Button n;
    //public static Bitmap bmg;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.gallery,container,false);
        b=view.findViewById(R.id.access_gallery);
        img=view.findViewById(R.id.openedimage);
        n=view.findViewById(R.id.gallerynext);
        n.setVisibility(View.INVISIBLE);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,1046);
            }
        });
        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).goToPaint(uri);
            }
        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1046) {
                Uri u=data.getData();
                uri=u;
                Bitmap b=null;
                try {
                    b = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), u);
                }
                catch(IOException e)
                {}
                MainActivity.bit=b;
                img.setImageBitmap(b);
                n.setVisibility(View.VISIBLE);
        }
    }
}
