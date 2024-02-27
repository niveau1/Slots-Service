package com.mereo.slots.slotsService.entity;

import java.time.LocalDate;

public class Slot {
    private LocalDate dateService;
    private ServiceTime serviceTime;
    public Slot() {
    }
    public Slot(LocalDate  dateService, ServiceTime serviceTime) {
        this.dateService = dateService;
        this.serviceTime = serviceTime;
    }

    public LocalDate  getDateService() {
        return dateService;
    }

    public void setDateService(LocalDate  dateService) {
        this.dateService = dateService;
    }

    public ServiceTime getHeureService() {
        return serviceTime;
    }

    public void setHeureService(ServiceTime serviceTime) {
        this.serviceTime = serviceTime;
    }
}
