package src.main.java.iesFranciscodelosRios.model;

import src.main.java.iesFranciscodelosRios.model.*;

import java.util.ArrayList;
import java.util.List;

public class Client extends Person {
    private ColorTestResult colorTestResult;
    private List<Appointment> appointments = new ArrayList<>();

    public Client(int id_person, String name, String surname, String telephone, String email, String password, ColorTestResult colorTestResult) {
        super(id_person, name, surname, telephone, email, password);
        this.colorTestResult = colorTestResult;
    }

    public Client() {
        this(0, "", "", "", "", "",null);
    }

    public ColorTestResult getColorTestResult() {
        return colorTestResult;
    }

    public void setColorTestResult(ColorTestResult colorTestResult) {
        this.colorTestResult = colorTestResult;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public String toString() {
        return "Client" +
                super.toString() +
                "\n\tcolorTestResult=" + colorTestResult +
                "\n\tappointments=" + appointments;
    }
}
