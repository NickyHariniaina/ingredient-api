package ferrissushi.ingredient.controller;

import ferrissushi.ingredient.entity.DishIngredient;
import ferrissushi.ingredient.entity.Ingredient;
import ferrissushi.ingredient.entity.StockValue;
import ferrissushi.ingredient.exception.BadRequestException;
import ferrissushi.ingredient.exception.NotFoundException;
import ferrissushi.ingredient.service.IngredientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;

@Controller
@AllArgsConstructor
public class IngredientController {
    IngredientService ingredientService;

    @GetMapping("/ingredients")
    public ResponseEntity<?> getIngredients() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ingredientService.findAll());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/ingredients/{id}")
    public ResponseEntity<?> getIngredientById(@PathVariable(name = "id") Integer ingredientId) {
        try {
            Ingredient ingredient = ingredientService.getById(ingredientId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ingredient);
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/ingredients/{id}/stock")
    public ResponseEntity<?> getIngredientStockById(@PathVariable(name = "id") Integer ingredientId,
                                                    @RequestParam(name = "at") Instant temporal,
                                                    @RequestParam(name = "unit") DishIngredient.UnitType unit) {
        try {
            StockValue stockValue = ingredientService.getStockValueAt(ingredientId, temporal, unit);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(stockValue);
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }


}
