package model.entities;

import java.time.LocalDateTime;

public class Schedule {
    private Integer scheduleId;
    private LocalDateTime scheduleDate;

    public Schedule(Integer scheduleId, LocalDateTime scheduleDate) {
        if (scheduleDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("The date must be after today");
        }
        this.scheduleId = scheduleId;
        this.scheduleDate = scheduleDate;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public LocalDateTime getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(LocalDateTime scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

}

