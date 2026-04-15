package ferrissushi.ingredient.repository;

import ferrissushi.ingredient.entity.Ingredient;
import ferrissushi.ingredient.entity.StockMovement;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class IngredientRepository {
    Connection connection;
    StockMovementRepository stockMovementRepository;

    public Optional<Ingredient> findById(Integer id) {
        String sql = """
                select id, name, price, category from ingredient where id = ?;
                """;
        List<StockMovement> stockMovements = stockMovementRepository.findAllByIngredientId(id);
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Ingredient ingredient = mapToIngredient(rs);
                ingredient.setStockMovementList(stockMovements);
                return Optional.of(ingredient);
            }
            return Optional.empty();
        } catch (SQLException error) {
            throw new RuntimeException(error);
        }
    }

    public Ingredient mapToIngredient(ResultSet rs) throws SQLException {
        Ingredient ingredient = new Ingredient();

        ingredient.setId(rs.getInt("id"));
        ingredient.setName(rs.getString("name"));
        ingredient.setPrice(rs.getDouble("price"));
        ingredient.setCategory(Ingredient.CategoryEnum.valueOf(rs.getString("category")));

        return ingredient;
    }

    public List<Ingredient> findAll() {
        List<Ingredient> Ingredients = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    """
                            select id, name, price, category from ingredient;
                            """);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(resultSet.getInt("id"));
                ingredient.setName(resultSet.getString("name"));
                ingredient.setCategory(Ingredient.CategoryEnum.valueOf(resultSet.getString("category")));
                ingredient.setPrice(resultSet.getDouble("price"));
                Ingredients.add(ingredient);
            }
            return Ingredients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
