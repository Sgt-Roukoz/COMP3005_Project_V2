package org.group68;

import java.sql.Connection;

public class MemberDashboard {

    private Connection databaseConnection;

    public MemberDashboard(Connection databaseConnection)
    {
        this.databaseConnection = databaseConnection;
    }
}
