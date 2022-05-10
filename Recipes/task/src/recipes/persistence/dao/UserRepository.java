package recipes.persistence.dao;

import org.springframework.data.repository.CrudRepository;
import recipes.persistence.model.User;


public interface UserRepository extends CrudRepository<User, Long> {

   User findByEmailIgnoreCase(String email);
}
