package com.polling.polling_project.domain;

public enum EVoteStatus {
    ACTIVE,
    INACTIVE;

    @Override
    public String toString (){
        return this.name();
    }
}
