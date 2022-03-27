package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
public class RegistrationController {
    @Autowired
    UserRepository userRepo;
    @Autowired
    PasswordEncoder encoder;


    @PostMapping("/api/register")
    public void register(@Valid @RequestBody User user) {
        // input validation omitted for brevity
        User userOld = userRepo.findByEmailIgnoreCase(user.getEmail());
        if (userOld == null){
            user.setPassword(encoder.encode(user.getPassword()));
            userRepo.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
