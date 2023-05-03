package iesFranciscodelosRios.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Space {
    private int id_space;
    private String name;
    private String serviceType;

    private List<Professional> professionals = new ArrayList<>();
    private List<Appointment> appointments = new ArrayList<>();

    public Space(int id_space, String name, String serviceType) {
        this.id_space = id_space;
        this.name = name;
        this.serviceType = serviceType;
    }

    public Space() {
        this(0,"", "");
    }

    public int getId_space() {
        return id_space;
    }

    public void setId_space(int id_space) {
        this.id_space = id_space;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public List<Professional> getProfessionals() {
        return professionals;
    }

    public void setProfessionals(List<Professional> professionals) {
        this.professionals = professionals;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Space space = (Space) o;
        return id_space == space.id_space;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_space);
    }

    @Override
    public String toString() {
        return "Space" +
                "\n\tid_space=" + id_space +
                "\n\tname='" + name +
                "\n\tserviceType='" + serviceType +
                "\n\tprofessionals=" + professionals +
                "\n\tappointments=" + appointments;
    }
}
