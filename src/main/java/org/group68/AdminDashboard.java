package org.group68;

import java.sql.Connection;

public class AdminDashboard {

    private Connection databaseConnection;

    public AdminDashboard(Connection databaseConnection)
    {
        this.databaseConnection = databaseConnection;
    }
}
