module org.example.stardewvalley2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.stardewvalley2 to javafx.fxml;
    exports org.example.stardewvalley2;

    opens org.example.stardewvalley2.control to javafx.fxml;
    exports org.example.stardewvalley2.control;

    opens org.example.stardewvalley2.model to javafx.fxml;
    exports org.example.stardewvalley2.model;

    opens org.example.stardewvalley2.screens to javafx.fxml;
    exports org.example.stardewvalley2.screens;

    opens org.example.stardewvalley2.util to javafx.fxml;
    exports org.example.stardewvalley2.util;

}