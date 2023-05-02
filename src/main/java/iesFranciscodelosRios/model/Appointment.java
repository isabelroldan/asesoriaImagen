package src.main.java.iesFranciscodelosRios.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class Appointment {
    private int id_appointment;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private Client client;
    private Space space;


    public Appointment(int id_appointment, LocalTime startTime, LocalTime endTime, LocalDate date, Client client, Space space) {
        this.id_appointment = id_appointment;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.client = client;
        this.space = space;
    }

    public Appointment() {
        this.id_appointment = 0;
        this.startTime = LocalTime.parse("00:00:00");
        this.endTime = LocalTime.parse("00:00:00");
        this.date = LocalDate.parse("01/01/1970", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.client = null;
        this.space = null;
    }
}
