package com.example.hamidreza.modeldata.Model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.hamidreza.modeldata.database.ItemsTable;

import java.util.UUID;

/**
 * Created by HAMIDREZA on 5/28/2017.
 */
public class DataItem implements Parcelable {
    public static final Parcelable.Creator<DataItem> CREATOR = new Parcelable.Creator<DataItem>() {
        @Override
        public DataItem createFromParcel(Parcel source) {
            return new DataItem(source);
        }

        @Override
        public DataItem[] newArray(int size) {
            return new DataItem[size];
        }
    };
    private String itemId;
    private String itemName;
    private String description;
    private String category;
    private int sortPosition;
    private String movad;
    private String image;

    public DataItem() {
    }

    public DataItem(String itemId, String itemName, String category, String description, int sprtPosition,
                    String movad, String image) {

        if (itemId == null) {
            itemId = UUID.randomUUID().toString();
        }


        this.itemId = itemId;
        this.image = image;
        this.movad = movad;
        this.sortPosition = sprtPosition;
        this.category = category;
        this.description = description;
        this.itemName = itemName;
    }

    protected DataItem(Parcel in) {
        this.itemId = in.readString();
        this.itemName = in.readString();
        this.description = in.readString();
        this.category = in.readString();
        this.sortPosition = in.readInt();
        this.movad = in.readString();
        this.image = in.readString();
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMavad() {
        return movad;
    }

    public void setMavad(String mavad) {
        this.movad = mavad;
    }

    public int getSortPosition() {
        return sortPosition;
    }

    public void setSortPosition(int sortPosition) {
        this.sortPosition = sortPosition;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public ContentValues toValues() {
        ContentValues values = new ContentValues(7);

        values.put(ItemsTable.COLUMN_ID, itemId);
        values.put(ItemsTable.COLUMN_NAME, itemName);
        values.put(ItemsTable.COLUMN_DESCRIPTION, description);
        values.put(ItemsTable.COLUMN_CATEGORY, category);
        values.put(ItemsTable.COLUMN_POSITION, sortPosition);
        values.put(ItemsTable.COLUMN_MOVAD, movad);
        values.put(ItemsTable.COLUMN_IMAGE, image);
        return values;
    }

    @Override
    public String toString() {
        return "DataItem{" +
                "itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", sortPosition=" + sortPosition +
                ", movad=" + movad +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.itemId);
        dest.writeString(this.itemName);
        dest.writeString(this.description);
        dest.writeString(this.category);
        dest.writeInt(this.sortPosition);
        dest.writeString(this.movad);
        dest.writeString(this.image);
    }
}
