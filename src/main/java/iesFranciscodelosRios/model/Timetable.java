package iesFranciscodelosRios.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Timetable {
    private String hour;

    public Timetable(String hour) {
        this.hour = hour;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public static List<Timetable> generateSchedules(String startTime, String endTime) {
        List<Timetable> timetables = new ArrayList<>();

        // Convertir la hora de inicio y fin a objetos LocalTime
        LocalTime startTimeLocalTime = LocalTime.parse(startTime);
        LocalTime endTimeLocalTime = LocalTime.parse(endTime);

        // Generar los horarios de media hora
        while (startTimeLocalTime.isBefore(endTimeLocalTime)) {
            Timetable timetable = new Timetable(startTimeLocalTime.toString());
            timetables.add(timetable);
            startTimeLocalTime = startTimeLocalTime.plusMinutes(30);
        }

        return timetables;
    }

    /*public static void main(String[] args) {
        List<Horario> horarios = generarHorarios("10:00", "20:00");

        for (Horario horario : horarios) {
            System.out.println(horario.getHora());
        }
    }*/
}
