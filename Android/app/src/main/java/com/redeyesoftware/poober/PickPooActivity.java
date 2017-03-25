package com.redeyesoftware.poober;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class PickPooActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_poo);

        TextView blr =((TextView) this.findViewById(R.id.blurbLable));
        TextView mon =((TextView) this.findViewById(R.id.moneyLable));

        blr.setText(MapMarkerClickListener.poo.getDesc());
        mon.setText(MapMarkerClickListener.poo.getMoney());

        this.imageView = (ImageView)this.findViewById(R.id.pooSelfie);

        Button photoButton = (Button) this.findViewById(R.id.selfieButton);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });


        ImageButton closeButton = (ImageButton) this.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button submitButton = (Button) this.findViewById(R.id.submitClaimButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEBUG", "post request");
                //NetworkingUtility.post("/addpoo", new String[]{"time","price","description","longitude","latitude","picture"}, new String[]{"555","$5.00","Yolo","40","50",""});
                NetworkingUtility.post("/addpoo", new String[]{"time","price","description","longitude","latitude","picture"}, new String[]{"555","$5.00","Yolo","40","50",""});

                finish();
            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }
}
