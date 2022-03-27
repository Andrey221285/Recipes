package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;

@RestController
public class RecipesController {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    public RecipesController(@Autowired RecipeRepository recipeRepository,UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
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
        User user = userRepository.findByEmailIgnoreCase(details.getUsername());
        System.out.println("name= " + details.getUsername());
        recipe.setUser(user);
        user.getRecipes().add(recipe);
        recipeRepository.save(recipe);
        userRepository.save(user);
        return new ResponseEntity<>(Map.of("id", recipe.getId()), HttpStatus.OK);
    }

    @DeleteMapping("/api/recipe/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipe(@AuthenticationPrincipal UserDetails details, @PathVariable long id){
        Optional<Recipe> recipe = recipeRepository.findById(id);

        if (recipe.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            User u = recipe.get().getUser();
            if (u.getEmail().equals(details.getUsername())){
                recipeRepository.deleteById(id);
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        }
    }

    @PutMapping("/api/recipe/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRecipe(@AuthenticationPrincipal UserDetails details, @Valid @RequestBody Recipe recipe, @PathVariable long id){

        if (recipeRepository.existsById(id)){
            Recipe r = recipeRepository.findById(id).get();
            User u = userRepository.findByEmailIgnoreCase(details.getUsername());
            if (r.getUser().getEmail().equals(details.getUsername())){
                recipe.setId(id);
                recipe.setUser(u);
                recipeRepository.save(recipe);
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
