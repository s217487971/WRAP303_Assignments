package com.example.sosgame;

import android.os.Parcel;
import android.os.Parcelable;

import java.security.PublicKey;

public class Player implements Parcelable {

    public String name;
    public int Score;
    public int numberOS;
    public int numberS;
    public int numberSOS;
    public int MAXnumberSOS;
    public int numberTurns;

    public Player(String name)
    {
        this.name = name;
        Score = 0;
        numberOS = 0;
        numberS = 0;
        numberSOS = 0;
        MAXnumberSOS = 0;
        numberTurns = 0;
    }

    protected Player(Parcel in) {
        name = in.readString();
        Score = in.readInt();
        numberOS = in.readInt();
        numberS = in.readInt();
        numberSOS = in.readInt();
        MAXnumberSOS = in.readInt();
        numberTurns = in.readInt();
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    public void setNumberTurns() {
        this.numberTurns = numberTurns+1;
    }

    public void setNumberOS() {
        this.numberOS = numberOS+1;
    }

    public void setNumberS() {
        this.numberS = numberS+1;
    }

    public void setNumberSOS(int numberSOS) {
        this.numberSOS = numberSOS;
    }

    public void setMAXnumberSOS(int MAXnumberSOS) {
        this.MAXnumberSOS = MAXnumberSOS;
    }

    public void AddPoint(int point)
    {
        Score = Score + point;
    }

    public void Reset()
    {
        Score = 0;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return Score;
    }

    public int getNumberOS() {
        return numberOS;
    }

    public int getNumberS() {
        return numberS;
    }

    public int getNumberSOS() {
        return numberSOS;
    }

    public int getMAXnumberSOS() {
        return MAXnumberSOS;
    }

    public int getNumberTurns() {
        return numberTurns;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(Score);
        dest.writeInt(numberOS);
        dest.writeInt(numberS);
        dest.writeInt(numberSOS);
        dest.writeInt(MAXnumberSOS);
        dest.writeInt(numberTurns);
    }
}
