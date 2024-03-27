package org.group68;

import javax.swing.*;
import java.sql.Connection;

public class AdminDashboard extends JFrame {

    private Connection databaseConnection;

    public AdminDashboard(Connection databaseConnection)
    {
        this.databaseConnection = databaseConnection;
    }
}
