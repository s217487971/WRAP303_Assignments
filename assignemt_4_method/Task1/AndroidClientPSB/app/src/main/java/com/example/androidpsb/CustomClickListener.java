package com.example.androidpsb;

public class CustomClickListener {
    Object listenToMe;

    public CustomClickListener(){
        listenToMe = new Object();
    }
    private listener listenerInterfaceInstance = null;

    public interface listener{
        public void onChange(Object listenToMe);
    }

    public void setClickListener(listener mListener){

        listenerInterfaceInstance = mListener;
    }

    public void notifyListners(Object listenToMeNewValue){
        if(listenerInterfaceInstance != null){
            listenerInterfaceInstance.onChange(listenToMeNewValue);
        }
    }


}
