module iesfranciscodelosrios.dam.imageconsulting {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires java.xml.bind;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;

    opens iesfranciscodelosrios.dam.imageconsulting to javafx.fxml;

    opens iesfranciscodelosrios.dam.model.connections to java.xml.bind;

    opens iesfranciscodelosrios.dam.model.domain to javafx.base;

    exports iesfranciscodelosrios.dam.imageconsulting;
    exports iesfranciscodelosrios.dam.imageconsulting.utils;
    opens iesfranciscodelosrios.dam.imageconsulting.utils to javafx.fxml;
}