package resources;

import java.io.Serializable;

public class OrderRequest implements Serializable {
    private String title;

    public OrderRequest(String title) {
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }
}
