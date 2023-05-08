
import iesfranciscodelosrios.dam.model.connections.Connect;
import iesfranciscodelosrios.dam.model.dao.AppointmentDAO;
import iesfranciscodelosrios.dam.model.dao.ClientDAO;
import iesfranciscodelosrios.dam.model.dao.SpaceDAO;
import iesfranciscodelosrios.dam.model.domain.Appointment;
import iesfranciscodelosrios.dam.model.domain.Client;
import iesfranciscodelosrios.dam.model.domain.Space;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AppointmentDAOTest {
    public static void main(String[] args) {
        System.out.println("Prueba FINDALL");
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

        /*System.out.println("Prueba FINDBYID");

        AppointmentDAO dao = new AppointmentDAO(conn);
        try {
            Appointment appointmentid = dao.findById(1);
            System.out.println(appointmentid);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*System.out.println("Prueba save");
        Connection conn = Connect.getConnect();
        AppointmentDAO dao = new AppointmentDAO(conn);

// Crear un nuevo objeto Appointment para guardar
        Appointment newAppointment = new Appointment();
        newAppointment.setId_appointment(5);
        newAppointment.setStartTime( LocalTime.of(10, 0));
        newAppointment.setEndTime(LocalTime.of(12, 0));
        newAppointment.setDate(LocalDate.of(2023, 5, 9));

// Crear un nuevo objeto Client y asignarlo al nuevo objeto Appointment
        ClientDAO cdao = new ClientDAO(conn);
        Client newClient = null;
        try {
            newClient = cdao.findById(2);
            newAppointment.setClient(newClient);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        newAppointment.setClient(newClient);


// Crear un nuevo objeto Space y asignarlo al nuevo objeto Appointment
        SpaceDAO sdao = new SpaceDAO(conn);
        Space newSpace = null;
        try {
            newSpace = sdao.findById(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        }*/

        /*System.out.println("Prueba update");

        Connection conn = Connect.getConnect();
        AppointmentDAO dao = new AppointmentDAO(conn);

        try {
            // Buscar la cita con id=5 y modificar sus datos
            Appointment appointmentToUpdate = dao.findById(5);
            appointmentToUpdate.setStartTime(LocalTime.of(11, 0));
            appointmentToUpdate.setEndTime(LocalTime.of(13, 0));
            appointmentToUpdate.setDate(LocalDate.of(2023, 5, 10));

            // Guardar los cambios en la base de datos
            dao.update(appointmentToUpdate);

            // Mostrar la cita modificada
            Appointment updatedAppointment = dao.findById(5);
            System.out.println("ID de cita: " + updatedAppointment.getId_appointment());
            System.out.println("Hora de inicio: " + updatedAppointment.getStartTime());
            System.out.println("Hora de fin: " + updatedAppointment.getEndTime());
            System.out.println("Fecha: " + updatedAppointment.getDate());
            System.out.println("ID de cliente: " + updatedAppointment.getClient().getId_person());
            System.out.println("ID de espacio: " + updatedAppointment.getSpace().getId_space());
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        /*System.out.println("Prueba delete");

        Connection conn = Connect.getConnect();
        AppointmentDAO dao = new AppointmentDAO(conn);

// Borrar la cita con id = 5
        Appointment appointmentToDelete = new Appointment();
        appointmentToDelete.setId_appointment(5);
        try {
            dao.delete(appointmentToDelete);
            System.out.println("Cita eliminada con éxito.");
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }
}
