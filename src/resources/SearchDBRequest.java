package resources;

import java.io.Serializable;

public class SearchDBRequest implements Serializable{
    private String title;
    private String database;

    public SearchDBRequest(String title, String database) {
        this.title = title;
        this.database = database;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDatabase() {
        return database;
    }
}
