package com.example.sosgame;

public class ChangeListener {
    int boo = 0;

    public ChangeListener(int b){
        boo = b;
    }

    private listener l = null;

    public interface listener{
        public void onChange(int b);
    }

    public void setChangeListener(listener mListener){
        l = mListener;
    }

    public void somethingChanged(){
        if(l != null){
            l.onChange(boo);
        }
    }


}