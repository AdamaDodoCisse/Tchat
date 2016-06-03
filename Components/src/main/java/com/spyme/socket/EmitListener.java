package com.spyme.socket;

/**
 * Created by balbe on 16/04/2016.
 */
public interface EmitListener {
    void onSuccess(Object object);
    void onError(Exception e);
}
