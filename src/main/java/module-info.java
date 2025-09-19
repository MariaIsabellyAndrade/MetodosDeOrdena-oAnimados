module com.example.heapsort {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.example.heapsort to javafx.fxml;
    opens com.example.heapsort.Controller to javafx.fxml;
    exports com.example.heapsort;
}