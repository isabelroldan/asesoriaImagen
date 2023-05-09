module iesfranciscodelosrios.dam.imageconsulting {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires java.xml.bind;

    opens iesfranciscodelosrios.dam.imageconsulting to javafx.fxml;

    opens iesfranciscodelosrios.dam.model.connections to java.xml.bind;

    exports iesfranciscodelosrios.dam.imageconsulting;
}