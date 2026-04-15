package ferrissushi.ingredient.service;

import ferrissushi.ingredient.entity.DishIngredient;
import ferrissushi.ingredient.entity.Ingredient;
import ferrissushi.ingredient.entity.StockValue;
import ferrissushi.ingredient.exception.BadRequestException;
import ferrissushi.ingredient.exception.NotFoundException;
import ferrissushi.ingredient.repository.IngredientRepository;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {
    IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    public Ingredient getById(Integer id) {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);
        if (optionalIngredient.isEmpty()) {
            throw new NotFoundException("Ingredient.id=" + id + " is not found");
        }
        return optionalIngredient.get();
    }

    public StockValue getStockValueAt(Integer ingredientId, Instant temporal, DishIngredient.UnitType unit) {
        if (temporal == null || unit == null) {
            throw new BadRequestException("Either mandatory query parameter `at` or `unit` is not provided");
        }
        Ingredient ingredient = getById(ingredientId);
        return ingredient.getStockValueAt(temporal, unit);
    }
}
