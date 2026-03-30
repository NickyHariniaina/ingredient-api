package ferrissushi.ingredient.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = DBConfig.class)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:postgresql://localhost:5432/testdb",
        "spring.datasource.username=testuser",
        "spring.datasource.password=testpass",
        "spring.datasource.driver-class-name=org.postgresql.Driver"
})
@DisplayName("DBConfig Tests")
class DBConfigTest {

    @Test
    @DisplayName("DataSource bean should be created")
    void dataSourceBeanShouldBeCreated(DataSource dataSource) {
        assertThat(dataSource).isNotNull();
    }
}
