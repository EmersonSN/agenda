package model.entities;

import java.time.LocalDateTime;

public class Schedule {
    private Integer scheduleId;
    private Client client;
    private LocalDateTime scheduleDate;

    public Schedule(Integer scheduleId, LocalDateTime scheduleDate, Client client) {
        if (client.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (client.getEmail() == null || !client.getEmail().contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }
        if (client.getPhone() <= 0) {
            throw new IllegalArgumentException("Invalid phone");
        }
        if (scheduleDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("The date must be after today");
        }
        this.scheduleId = scheduleId;
        this.client = client;
        this.scheduleDate = scheduleDate;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDateTime getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(LocalDateTime scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    @Override
    public String toString() {
        return String.format("%d, %s, %s, %s, %d", scheduleId, client.getName(), client.getEmail(), scheduleDate, client.getPhone());
    }
}

