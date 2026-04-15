package ferrissushi.ingredient.entity.dto;

import ferrissushi.ingredient.entity.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DishIngredientRest {
    private Integer id;
    private String name;
    private Ingredient.CategoryEnum category;
    private Double price;
}
