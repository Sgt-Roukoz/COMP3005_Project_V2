package org.group68;

import javax.swing.*;
import java.sql.Connection;

public class TrainerDashboard extends JFrame {

    private Connection databaseConnection;

    public TrainerDashboard(Connection databaseConnection)
    {
        this.databaseConnection = databaseConnection;

    }
}
