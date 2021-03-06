package recipes.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import recipes.persistence.model.User;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {
    private final String email;
    private final String password;
   // private final List<GrantedAuthority> rolesAndAuthorities;

    public UserDetailsImpl(User user) {
        email = user.getEmail();
        password = user.getPassword();
        //rolesAndAuthorities = List.of(new SimpleGrantedAuthority(user.getRole()));
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
