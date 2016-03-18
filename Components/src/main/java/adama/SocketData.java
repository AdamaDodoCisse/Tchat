package adama;

import java.io.Serializable;

/**
 *
 */
public class SocketData implements Serializable {

    private String context;
    private Object data;

    public SocketData(String context, Object data) {

        this.setContext(context);
        this.setData(data);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
