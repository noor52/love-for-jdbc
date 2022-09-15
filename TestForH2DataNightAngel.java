package com.example.jdbc;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import java.sql.*;
import org.h2.tools.SimpleResultSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestForH2DataNightAngel.class})
public class TestForH2DataNightAngel {


    @Test
    public static ResultSet executeMyProc() {
        System.out.println("");
        return new SimpleResultSet();
    }

    @Test
    public void test() throws Exception
    {

        Connection connection = DriverManager.getConnection("jdbc:h2:mem:");
        Statement statement = connection.createStatement();
        statement.execute("CREATE ALIAS executeMyProc FOR \"com.example.jdbc.TestForH2DataNightAngel.executeMyProc\"");
        ResultSet resultSet = statement.executeQuery("CALL executeMyProc()");
        assertThat(resultSet.next()).isFalse();
    }
}
