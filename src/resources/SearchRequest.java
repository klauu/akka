package resources;

import java.io.Serializable;

public class SearchRequest implements Serializable{
    private String title;

    public SearchRequest(String title) {
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }
}
