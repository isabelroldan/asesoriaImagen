import com.mysql.cj.xdevapi.Client;
import iesfranciscodelosrios.dam.model.connections.Connect;
import iesfranciscodelosrios.dam.model.dao.AppointmentDAO;
import iesfranciscodelosrios.dam.model.dao.ClientDAO;
import iesfranciscodelosrios.dam.model.domain.Appointment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AppointmentDAOTest {
    public static void main(String[] args) {
        /*System.out.println("Prueba FINDALL");
        Connection conn = Connect.getConnect();
        AppointmentDAO appointmentDao = new AppointmentDAO(conn);
        List<Appointment> appointments = null;
        try {
            appointments = appointmentDao.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (Appointment appointment : appointments) {
            System.out.println("ID de cita: " + appointment.getId_appointment());
            System.out.println("Hora de inicio: " + appointment.getStartTime());
            System.out.println("Hora de fin: " + appointment.getEndTime());
            System.out.println("Fecha: " + appointment.getDate());
            System.out.println("ID de cliente: " + appointment.getClient().getId_person());
            System.out.println("ID de espacio: " + appointment.getSpace().getId_space());
            System.out.println();
        }

        System.out.println("Prueba FINDBYID");

        AppointmentDAO dao = new AppointmentDAO(conn);
        try {
            Appointment appointmentid = dao.findById(1);
            System.out.println(appointmentid);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        System.out.println("Prueba save");
        Connection conn = Connect.getConnect();
        AppointmentDAO dao = new AppointmentDAO(conn);

// Crear un nuevo objeto Appointment para guardar
        Appointment newAppointment = new Appointment();
        newAppointment.setId_appointment(5);
        newAppointment.setStartTime( LocalTime.of(10, 0));
        newAppointment.setEndTime(LocalTime.of(12, 0));
        newAppointment.setDate(LocalDate.of(2023, 5, 9));

// Crear un nuevo objeto Client y asignarlo al nuevo objeto Appointment
        Client newClient = new Client();
        newClient.setId_person(2);
        newClient.setName("Juan");
        newClient.setSurname("Pérez");
        newAppointment.setClient(newClient);

// Crear un nuevo objeto Space y asignarlo al nuevo objeto Appointment
        Space newSpace = new Space();
        newSpace.setId_space(1);
        newSpace.setCapacity(10);
        newSpace.setFloor(1);
        newAppointment.setSpace(newSpace);

        try {
            // Guardar el nuevo objeto Appointment en la base de datos
            dao.save(newAppointment);

            // Mostrar el objeto Appointment guardado en la base de datos
            Appointment savedAppointment = dao.findById(5);
            System.out.println("ID de cita: " + savedAppointment.getId_appointment());
            System.out.println("Hora de inicio: " + savedAppointment.getStartTime());
            System.out.println("Hora de fin: " + savedAppointment.getEndTime());
            System.out.println("Fecha: " + savedAppointment.getDate());
            System.out.println("ID de cliente: " + savedAppointment.getClient().getId_person());
            System.out.println("ID de espacio: " + savedAppointment.getSpace().getId_space());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
