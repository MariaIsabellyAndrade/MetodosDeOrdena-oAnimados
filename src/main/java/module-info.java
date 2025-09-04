module com.example.heapsort {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.heapsort.Controller to javafx.fxml;
    exports com.example.heapsort;
}