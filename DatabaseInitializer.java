package com.concretepage.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@EnableAutoConfiguration
@Slf4j
public class DatabaseInitializer {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Bean
    CommandLineRunner loadDatabase() {
        return new CommandLineRunner() {

            @Override
            public void run(String... args) throws Exception {

                jdbcTemplate.execute("create table student (id int primary key "
                        + "auto_increment, name varchar(30), email varchar(30))");

                jdbcTemplate.execute("insert into student (name, email) "
                        + "values ('Will Smith', 'will.smith@holywood.com')");


                jdbcTemplate.execute("insert into student (name, email) "
                        + "values ('Hanse ', 'hanse@holywood.com')");


                String procedureSql = "CREATE ALIAS MYFUNCTION AS $$\n" +
                        "String getTableContent(java.sql.Connection con) throws Exception {\n" +
                        "\n" +
                        "    String resultValue=null;\n" +
                        "    java.sql.ResultSet rs = con.createStatement().executeQuery(\n" +
                        "    \" SELECT email FROM student\");\n" +
                        "       while(rs.next())\n" +
                        "       {\n" +
                        "        resultValue=rs.getString(1);\n" +
                        "         System.out.print(rs.getString(1));\n" +
                        "\n" +
                        "\n" +
                        "       }\n" +
                        "    return resultValue;\n" +
                        "}\n" +
                        "$$;";
                jdbcTemplate.execute(procedureSql);
                jdbcTemplate.execute(" call MYFUNCTION()");
            }
        };
    }

}