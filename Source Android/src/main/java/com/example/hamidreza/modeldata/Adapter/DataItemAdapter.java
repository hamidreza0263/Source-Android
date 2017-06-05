package com.example.hamidreza.modeldata.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hamidreza.modeldata.Activity.Dateil;
import com.example.hamidreza.modeldata.Model.DataItem;
import com.example.hamidreza.modeldata.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DataItemAdapter extends RecyclerView.Adapter<DataItemAdapter.ViewHolder> {
    public static final String ITEM_ID_KٍEY = "item_id_key";
    public static final String ITEM_KٍEY = "item_key";
    private List<DataItem> mItems;
    private Context mContext;
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;

    public DataItemAdapter(Context context, List<DataItem> items) {
        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public DataItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                Log.v("preferences", "OnSharedPreferenceChangeListener:" + s);
            }
        };
        preferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener);

        boolean grid = preferences.getBoolean(mContext.getString(R.string.pref_display_grid), false);
        int layoutSettings = grid ? R.layout.grid_item : R.layout.item_list;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(layoutSettings, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DataItemAdapter.ViewHolder holder, int position) {
        final DataItem item = mItems.get(position);

        try {
            holder.tvName.setText(item.getItemName());
            String imageFile = item.getImage();
            InputStream inputStream = mContext.getAssets().open(imageFile);
            Drawable d = Drawable.createFromStream(inputStream, null);
            holder.imageView.setImageDrawable(d);

        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                String itemId = item.getItemId();
                Intent intent = new Intent(mContext, Dateil.class);
                intent.putExtra(ITEM_KٍEY, item);
                mContext.startActivity(intent);


            }
        });
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(mContext, "دوباره امتحان کنید" + item.getItemName(),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public ImageView imageView;
        public View mView;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            tvName = (TextView) itemView.findViewById(R.id.itemName);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}