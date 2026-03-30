package ferrissushi.ingredient.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dish {
    public enum DishTypeEnum {
        START, MAIN, DESSERT
    }

    private Integer id;
    private String name;
    private DishTypeEnum dishType;
    private Double sellingPrice;
    private List<DishIngredient> ingredients;

    public Double getPrice() {
        return sellingPrice;
    }

    public Double getDishCost() {
        return ingredients.stream().mapToDouble(DishIngredient::getTotalPrice).sum();
    }

    public Double getGrossMargin() {
        if (this.sellingPrice == null) {
            throw new RuntimeException("Dish does not have a sellingPrice yet");
        }
        return this.sellingPrice - getDishCost();
    }
}
