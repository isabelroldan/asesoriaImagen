package iesFranciscodelosRios.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

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

    public int getId_appointment() {
        return id_appointment;
    }

    public void setId_appointment(int id_appointment) {
        this.id_appointment = id_appointment;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        this.space = space;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return id_appointment == that.id_appointment;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_appointment);
    }

    @Override
    public String toString() {
        return "Appointment" +
                "\n\tid_appointment=" + id_appointment +
                "\n\tstartTime=" + startTime +
                "\n\tendTime=" + endTime +
                "\n\tdate=" + date +
                "\n\tclient=" + client +
                "\n\tspace=" + space;
    }
}
