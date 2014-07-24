package ua.kiev.naiv.drinkit.cocktail.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import ua.kiev.naiv.drinkit.cocktail.common.JsonMixIn;
import ua.kiev.naiv.drinkit.cocktail.common.LoggerUtils;
import ua.kiev.naiv.drinkit.cocktail.persistence.search.Criteria;
import ua.kiev.naiv.drinkit.cocktail.service.RecipeService;
import ua.kiev.naiv.drinkit.cocktail.web.model.Recipe;
import ua.kiev.naiv.drinkit.cocktail.web.model.RecipeSearchResultMixin;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "recipes")
public class RecipeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeController.class);

    @Autowired
    RecipeService recipeService;

    @RequestMapping(value = "/{recipeId}", method = RequestMethod.GET)
    @ResponseBody
    @Transactional(readOnly = true)
    public Recipe getRecipeById(@PathVariable int recipeId) {
        return recipeService.getRecipeById(recipeId);
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
    public HttpEntity<Integer> createRecipe(@RequestBody Recipe recipe) {
        Assert.isNull(recipe.getId());
        LoggerUtils.logOperation("Creating recipe", recipe);
        return new HttpEntity<>(recipeService.save(recipe));
    }


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @Transactional(readOnly = true)
    @JsonMixIn(value = RecipeSearchResultMixin.class, targetClass = Recipe.class)
    public List<Recipe> searchRecipes(@RequestParam(value = "criteria", required = false) String json) {
        List<Recipe> recipes;
        if (json != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            Criteria criteria;
            try {
                criteria = objectMapper.readValue(json, Criteria.class);
            } catch (IOException e) {
                LOGGER.error("Bad criteria", e);
                return null;
            }
            recipes = recipeService.findByCriteria(criteria);
        } else {
            recipes = recipeService.findAll();
        }
        return recipes;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteRecipe(@PathVariable int id) {
        LoggerUtils.logOperation("Deleting recipe", id);
        recipeService.delete(id);
    }

    @RequestMapping(value = "{recipeId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateRecipe(@PathVariable int recipeId, @RequestBody Recipe recipe) {
        Assert.isTrue(recipeId == recipe.getId(), "id from uri and id from json should be identical");
        LoggerUtils.logOperation("Updating recipe", recipe);
        recipeService.save(recipe);
    }

}
