package com.example.cinemaapp.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ShowtimeDTO {
    private Integer id;
    private String showDate;
    private String startTime;
    private Integer roomId;
    private String roomName;

    public ShowtimeDTO(Integer id, String showDate, String startTime, Integer roomId, String roomName) {
        this.id = id;
        this.showDate = showDate;
        this.startTime = startTime;
        this.roomId = roomId;
        this.roomName = roomName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
