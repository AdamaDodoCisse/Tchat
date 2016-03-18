package adama;

import java.io.Serializable;

/**
 *
 */
public class SocketData implements Serializable {

    private String context;
    private Object data;

    public SocketData(String context, Object data) {

        this.context = context;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
