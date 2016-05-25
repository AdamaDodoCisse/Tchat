package com.socket.component;

import java.io.Serializable;

public class SocketInformation implements Serializable {
    /**
     *
     */
    private String protocol;

    /**
     *
     */
    private Object data;

    public SocketInformation(String protocol, Object data) {
        this.protocol = protocol;
        this.data = data;
    }

    public String getProtocol() {
        return protocol;

    }

    public Object getData() {
        return data;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }


    public void setData(Object data) {
        this.data = data;
    }

}
