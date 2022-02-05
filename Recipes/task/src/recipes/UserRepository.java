package recipes;

import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> {

   User findByEmailIgnoreCase(String email);
}
