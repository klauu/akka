package resources;

import java.io.Serializable;

public class DBErrorResponse implements Serializable {
    private String msg;

    public DBErrorResponse(String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

}
