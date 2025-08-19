package model.entities;

import java.time.LocalDateTime;

public class Schedule {
    private final Integer id;
    private String name;
    private String email;
    private LocalDateTime data;
    private long phone;

    public Schedule(Integer id, String name, String email, LocalDateTime data, long phone) {
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }
        if (phone <= 0) {
            throw new IllegalArgumentException("Invalid phone");
        }
        if (data.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("The date must be after today");
        }
        this.id = id;
        this.name = name;
        this.email = email;
        this.data = data;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
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

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return String.format("%d, %s, %s, %s, %d", id, name, email, data, phone);
    }
}

