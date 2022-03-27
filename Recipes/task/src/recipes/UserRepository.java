package recipes;

import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;


public interface UserRepository extends CrudRepository<User, Long> {

   User findByEmailIgnoreCase(String email);
}
