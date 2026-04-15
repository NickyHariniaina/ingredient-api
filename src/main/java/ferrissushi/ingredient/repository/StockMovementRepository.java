package ferrissushi.ingredient.repository;

import ferrissushi.ingredient.entity.DishIngredient;
import ferrissushi.ingredient.entity.StockMovement;
import ferrissushi.ingredient.entity.StockValue;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class StockMovementRepository {
    Connection connection;

    public List<StockMovement> findAllByIngredientId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        String sql = """
                select id, id_ingredient, quantity, type, unit, creation_datetime
                from stock_movement where id_ingredient = ?;
                """;
        List<StockMovement> stockMovements = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                StockMovement stockMovement = mapToStockMovement(rs);
                stockMovements.add(stockMovement);
            }
            return stockMovements;
        } catch (SQLException error) {
            throw new RuntimeException(error);
        }
    }

    public StockMovement mapToStockMovement(ResultSet rs) throws SQLException {
        StockMovement stockMovement = new StockMovement();
        stockMovement.setId(rs.getInt("id"));
        stockMovement.setValue(new StockValue(rs.getDouble("quantity"), DishIngredient.UnitType.valueOf(rs.getString("unit"))));
        stockMovement.setType(StockMovement.MovementTypeEnum.valueOf(rs.getString("type")));
        stockMovement.setCreationDatetime(rs.getTimestamp("creation_datetime").toInstant());
        return stockMovement;
    }

}
