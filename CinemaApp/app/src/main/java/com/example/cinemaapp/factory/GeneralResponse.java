package com.example.cinemaapp.factory;

public class GeneralResponse<T> {
    private StatusDTO status;
    private T data;

    // Getter và Setter
    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

