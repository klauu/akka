package resources;

import java.io.Serializable;

public class ErrorResponse implements Serializable {
    private String msg;

    public ErrorResponse(String msg){
        this.msg = msg;
    }

    public String getMsg(){
        return msg;
    }
}
