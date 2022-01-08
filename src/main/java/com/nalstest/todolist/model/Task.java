package com.nalstest.todolist.model;

import com.nalstest.todolist.enums.Status;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100, nullable = false)
    private String nameTask;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Status status;

    private Date startingDate;

    private Date endDate;

    private Boolean deleted;

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Task() {
    }

    public Task(String nameTask, Status status, Date startingDate, Date endDate) {
        this.nameTask = nameTask;
        this.status = status;
        this.startingDate = startingDate;
        this.endDate = endDate;
    }

    public Task(Status status, Date startingDate, Date endDate) {
        this.nameTask = nameTask;
        this.status = status;
        this.startingDate = startingDate;
        this.endDate = endDate;
    }

    public Task(long id, String nameTask, Status status, Date startingDate, Date endDate) {
        this.id = id;
        this.nameTask = nameTask;
        this.status = status;
        this.startingDate = startingDate;
        this.endDate = endDate;
    }
}
