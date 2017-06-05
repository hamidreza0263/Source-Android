package com.example.hamidreza.modeldata.Activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hamidreza.modeldata.Adapter.DataItemAdapter;
import com.example.hamidreza.modeldata.Model.DataItem;
import com.example.hamidreza.modeldata.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by HAMIDREZA on 5/30/2017.
 */

public class Dateil extends AppCompatActivity {
    private TextView txName, txdescraption, txMavad;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dateil);
        //       String itemId = getIntent().getExtras().getString(DataItemAdapter.ITEM_ID_KٍEY);
        //        DataItem dataItem = SampleDataProvider.dataItemMap.get(itemId);
        DataItem item = getIntent().getExtras().getParcelable(DataItemAdapter.ITEM_KٍEY);

        txName = (TextView) findViewById(R.id.nameText);
        txdescraption = (TextView) findViewById(R.id.descraotion);
        txMavad = (TextView) findViewById(R.id.mavad);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        txName.setText(item.getItemName());
        txdescraption.setText(item.getDescription());
        txMavad.setText(item.getMavad());

        InputStream inputStream = null;
        try {
            String itemImage = item.getImage();
            inputStream = getAssets().open(itemImage);
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            imageView.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
