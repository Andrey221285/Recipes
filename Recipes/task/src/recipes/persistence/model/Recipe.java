package recipes.persistence.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "recipe")
public class Recipe {

    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column
    @NotBlank
    private String name;
    @Column
    @NotBlank
    private String description;

    @Column
    @NotBlank
    private String category;

    @PrePersist
    public void onPrepersist(){
        date = LocalDateTime.now();
    }

    @PreUpdate
    public void onPreUpdate(){
        date = LocalDateTime.now();
    }

    @Column
    private LocalDateTime date;

    @ElementCollection(targetClass=String.class)
    @Size(min = 1)
    @NotNull
    private List<String> ingredients;

    @ElementCollection(targetClass=String.class)
    @Size(min = 1)
    @NotNull
    private List<String> directions;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
}
