package ferrissushi.ingredient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

@Configuration
public class DataSource {
    private final String jdbcUrl = "jdbc:postgresql://localhost:5432/mini_dish_db";
    private final String USER = "mini_dish_db_manager";
    private final String PASSWORD = "123456";

    @Bean
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(jdbcUrl, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
