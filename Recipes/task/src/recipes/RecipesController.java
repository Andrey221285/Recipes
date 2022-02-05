package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;

@RestController
public class RecipesController {
    private final RecipeRepository recipeRepository;

    public RecipesController(@Autowired RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @GetMapping("/api/recipe/{id}")
    public ResponseEntity<Recipe> getRecipe (@PathVariable long id){
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (recipe.isPresent()){
            return new ResponseEntity<>(recipe.get(), HttpStatus.OK);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/api/recipe/search/" )
    public ResponseEntity<List<Recipe>> getRecipes (@RequestParam (required = false) String category, @RequestParam(required = false) String name){
        if (category!= null && name != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (category != null){
            return new ResponseEntity<>(recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category), HttpStatus.OK);
        } else if(name !=null){
            return new ResponseEntity<>(recipeRepository.findByNameContainingIgnoreCaseOrderByDateDesc(name), HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/api/recipe/new")
    public ResponseEntity<?> addRecipe(@AuthenticationPrincipal UserDetails details, @Valid @RequestBody Recipe recipe){
        if (details == null){
            System.out.println("details==null");
        }else {
            System.out.println(details);
        }

        recipeRepository.save(recipe);
        return new ResponseEntity<>(Map.of("id", recipe.getId()), HttpStatus.OK);
    }

    @DeleteMapping("/api/recipe/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipe(@PathVariable long id){
        if (recipeRepository.existsById(id)){
            recipeRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/api/recipe/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRecipe(@Valid @RequestBody Recipe recipe, @PathVariable long id){
        if (recipeRepository.existsById(id)){
            recipe.setId(id);
            recipeRepository.save(recipe);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
