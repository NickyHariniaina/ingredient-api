package ferrissushi.ingredient.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DishIngredient {
    private Integer id;
    private Dish dish;
    private Ingredient ingredient;
    private Double quantity;
    private UnitType unit;

    public enum UnitType {
        PCS, KG, L
    }

    public Double getTotalPrice() {
        return quantity * ingredient.getPrice();
    }
}
