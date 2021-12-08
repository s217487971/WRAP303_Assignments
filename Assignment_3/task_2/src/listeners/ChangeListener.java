package listeners;

public class ChangeListener {
    Object boo = 0;

    public ChangeListener(Object b){
        boo = b;
    }
    public void setBoo(Object boo){this.boo = boo;}

    private listener l = null;

    public interface listener{
        public void onChange(Object b);
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