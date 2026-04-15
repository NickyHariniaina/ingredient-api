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
    private Double price;
    private List<DishIngredient> dishIngredients;

    public Double getPrice() {
        return price;
    }

    public Double getDishCost() {
        return dishIngredients.stream().mapToDouble(DishIngredient::getTotalPrice).sum();
    }

    public Double getGrossMargin() {
        if (this.price == null) {
            throw new RuntimeException("Dish does not have a sellingPrice yet");
        }
        return this.price - getDishCost();
    }
}
