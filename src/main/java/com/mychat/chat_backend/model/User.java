package com.mychat.chat_backend.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chat_user")
public class User {

    @Id
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "user_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    @Column(name = "id")
    private long id;

    @Column(unique = true, name = "user_name", nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @Column(unique = true, name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "avatarUrl", length = 100)
    private String avatarUrl;

    @Column(name = "online_status", nullable = false, length = 1)
    private Boolean isOnline;

    @Column(name = "admin_status", nullable = false, length = 1)
    private Boolean isAdmin;

    @ManyToMany(mappedBy = "participants")
    List<Room> currentRooms;

    protected User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isOnline = false;
        this.isAdmin = false;
        this.avatarUrl = "placeholder";
        this.currentRooms = new ArrayList<>();
    }


    public List<Room> getCurrentRooms() {
        return currentRooms;
    }

    public void setCurrentRooms(List<Room> currentRooms) {
        this.currentRooms = currentRooms;
    }

    public void addRoom(Room room) {
        currentRooms.add(room);
    }

    public void removeRoom(Room room) {
        currentRooms.remove(room);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}