package recipes.persistence.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import recipes.persistence.model.Recipe;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
public class User {
    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column
    @NotBlank
    @Email
    @Size(min = 8)
    @Pattern(regexp = ".+@.+\\..+")
    private String email;
    @Column
    @NotBlank
    @Size(min = 8)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Recipe> recipes = new ArrayList<>();

}
