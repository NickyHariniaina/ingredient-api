package ferrissushi.ingredient.entity;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
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

    public StockValue getStockValueAt(Instant t) {
        List<StockMovement> sortedStockMovement = this.stockMovementList.stream()
                .sorted(Comparator.comparing(StockMovement::getCreationDatetime))
                .toList();
        StockValue remainingStockValue = StockValue.builder()
                .unit(sortedStockMovement.get(0).getValue().getUnit())
                .build();
        Double remainingQuantity = 0.00;

        for (StockMovement sortedStockMovementElement : sortedStockMovement) {
            if (sortedStockMovementElement.getCreationDatetime().isBefore(t)
                    || sortedStockMovementElement.getCreationDatetime().equals(t)) {
                if (sortedStockMovementElement.getType() == StockMovement.MovementTypeEnum.IN) {
                    remainingQuantity += sortedStockMovementElement.getValue().getQuantity();
                } else if (sortedStockMovementElement.getType() == StockMovement.MovementTypeEnum.OUT) {
                    remainingQuantity -= sortedStockMovementElement.getValue().getQuantity();
                }
            }
        }
        remainingStockValue.setQuantity(remainingQuantity);
        return remainingStockValue;
    }
}
