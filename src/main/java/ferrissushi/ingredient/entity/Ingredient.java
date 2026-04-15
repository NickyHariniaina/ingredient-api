package ferrissushi.ingredient.entity;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {

    public enum CategoryEnum {
        VEGETABLE, ANIMAL, MARINE, DAIRY, OTHER
    }

    private int id;
    private String name;
    private Double price;
    private CategoryEnum category;
    private List<StockMovement> stockMovementList;

    public StockValue getStockValueAt(Instant t, DishIngredient.UnitType unit) {
        if (stockMovementList == null) return null;
        Map<DishIngredient.UnitType, List<StockMovement>> unitSet = stockMovementList.stream()
                .collect(Collectors.groupingBy(stockMovement -> stockMovement.getValue().getUnit()));
        if (unitSet.keySet().size() > 1) {
            throw new RuntimeException("Multiple unit found and not handle for conversion");
        }

        List<StockMovement> stockMovements = stockMovementList.stream()
                .filter(stockMovement -> !stockMovement.getCreationDatetime().isAfter(t))
                .toList();
        double movementIn = stockMovements.stream()
                .filter(stockMovement -> stockMovement.getType().equals(StockMovement.MovementTypeEnum.IN))
                .flatMapToDouble(stockMovement -> DoubleStream.of(stockMovement.getValue().getQuantity()))
                .sum();
        double movementOut = stockMovements.stream()
                .filter(stockMovement -> stockMovement.getType().equals(StockMovement.MovementTypeEnum.OUT))
                .flatMapToDouble(stockMovement -> DoubleStream.of(stockMovement.getValue().getQuantity()))
                .sum();

        StockValue stockValue = new StockValue();
        stockValue.setQuantity(movementIn - movementOut);
        stockValue.setUnit(unitSet.keySet().stream().findFirst().get());

        return stockValue;
    }

}
