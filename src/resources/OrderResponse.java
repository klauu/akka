package resources;

import java.io.Serializable;

public class OrderResponse implements Serializable {
    private String title;
    private boolean succeed;

    public OrderResponse(String title, boolean succeed){
        this.title = title;
        this.succeed = succeed;
    }

    public String getTitle() {
        return title;
    }

    public boolean isSucceed(){
        return succeed;
    }
}