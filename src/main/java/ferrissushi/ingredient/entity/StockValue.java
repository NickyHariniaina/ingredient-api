package ferrissushi.ingredient.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockValue {
    private Double quantity;
    private DishIngredient.UnitType unit;
}
