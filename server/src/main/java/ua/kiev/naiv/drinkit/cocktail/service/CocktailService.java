package ua.kiev.naiv.drinkit.cocktail.service;

import org.springframework.security.access.prepost.PreAuthorize;
import ua.kiev.naiv.drinkit.cocktail.persistence.model.CocktailType;
import ua.kiev.naiv.drinkit.cocktail.persistence.model.Ingredient;
import ua.kiev.naiv.drinkit.cocktail.persistence.search.Criteria;
import ua.kiev.naiv.drinkit.cocktail.web.model.Recipe;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Pavel Kolmykov
 * Date: 20.07.13
 * Time: 21:52
 */
public interface CocktailService {

    @PreAuthorize("hasRole(ROLE_ADMIN)")
    int create(Recipe recipe);

    @PreAuthorize("hasRole(ROLE_ADMIN)")
    void delete(int id);

    List<Recipe> findAll();

    List<Recipe> findByCriteria(Criteria criteria);

    @PreAuthorize("hasRole(ROLE_ADMIN)")
    void update(int id, Recipe recipeEntity);

    Recipe getRecipeById(int id);

    CocktailType findCocktailTypeById(int id);

//    List<CocktailType> findAllCocktailType();

    List<Ingredient> getIngredients();

    Ingredient findIngredientById(int id);

//    List<CocktailType> getCocktailTypes();
}
