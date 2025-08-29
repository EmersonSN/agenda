package model.entities;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Client {

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private Integer clientId;
    private String name;
    private String email;
    private Long phone;

    private List<Schedule> schedules = new ArrayList<>();

    public Client() {

    }

    public Client(Integer clientId, String name, String email, Long phone) {
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }
        if (phone <= 0) {
            throw new IllegalArgumentException("Invalid phone");
        }
        this.clientId = clientId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    @Override
    public String toString() {
        String schedule = schedules.isEmpty() ? "Sem agendamento" :
                schedules.get(0).getScheduleDate().format(formatter);
        return String.format("%d | %s | %s | %s | %d",
                getClientId(), getName(), getEmail(), schedule, getPhone());
    }
}
