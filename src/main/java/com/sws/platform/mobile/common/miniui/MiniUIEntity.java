package com.sws.platform.mobile.common.miniui;

/**
 * Created by MaoLiang on 2016/4/10.
 */
public class MiniUIEntity<T> {
    private STATE state;
    private T entity;

    public STATE getState() {
        return state;
    }

    public void setState(STATE state) {
        this.state = state;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public void setState(String state) {
        if ("removed".equals(state) || "deleted".equals(state)) {
            this.state = STATE.DELETED;
        } else if ("added".equals(state)) {
            this.state = STATE.ADDED;
        } else if ("modified".equals(state)) {
            this.state = STATE.MODIFIED;
        }
    }

    public enum STATE {
        ADDED,
        MODIFIED,
        DELETED
    }
}
