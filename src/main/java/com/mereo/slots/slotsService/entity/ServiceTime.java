package com.mereo.slots.slotsService.entity;

import java.time.LocalTime;
public class ServiceTime {
    private LocalTime beginService;
    private LocalTime endService;
    public ServiceTime() {
    }
    public ServiceTime(LocalTime beginService, LocalTime endService) {
        this.beginService = beginService;
        this.endService = endService;
    }
    public LocalTime getBeginService() {
        return beginService;
    }

    public void setBeginService(LocalTime beginService) {
        this.beginService = beginService;
    }

    public LocalTime getEndService() {
        return endService;
    }

    public void setEndService(LocalTime endService) {
        this.endService = endService;
    }



}
