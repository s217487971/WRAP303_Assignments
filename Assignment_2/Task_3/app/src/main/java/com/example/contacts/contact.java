package com.example.contacts;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class contact implements Parcelable, Serializable {
    String name; //Name
    String id; //Profile Picture Saved During Contact Saving
    int drawable;
    String number;//number

    public String getId() {
        return id;
    }

    public contact(String id, String name, String number, int drawable)
    {
        this.id = id;
        this.name = name;
        this.number = number;
        this.drawable = drawable;

    }
    public contact(String name, String number, int drawable)
    {
        this.id = name+"."+number;
        this.name = name;
        this.number = number;
        this.drawable = drawable;
    }

    public String getName() {
        return name;
    }
    public String getNumber()
    {
        return number;
    }
    public void setName(String name) {
        this.name = name;
    }


    public void setNumber(String number) {
        this.number = number;
    }

    public int getImgResource() {
        return drawable;
    }

    public void setImgResource(int imgResource) {
        this.drawable = imgResource;
    }

    protected contact(Parcel in) {
        String[] data = new String[4];

        in.readStringArray(data);

        this.id = data[0];
        this.name = data[1];
        this.number = data[2];
        this.drawable = Integer.valueOf(data[3]);
    }

    public static final Creator<contact> CREATOR = new Creator<contact>() {
        @Override
        public contact createFromParcel(Parcel in) {
            return new contact(in);
        }

        @Override
        public contact[] newArray(int size) {
            return new contact[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.id, this.name,
                this.number,String.valueOf(this.drawable)});
    }

}
