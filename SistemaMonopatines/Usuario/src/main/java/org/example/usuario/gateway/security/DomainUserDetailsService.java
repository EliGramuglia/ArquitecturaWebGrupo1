package org.example.usuario.gateway.security;

import org.example.usuario.entity.Authority;
import org.example.usuario.entity.Usuario;
import org.example.usuario.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UsuarioRepository usuarioRepository;

    public DomainUserDetailsService(UsuarioRepository userRepository) {
        this.usuarioRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username) {
        log.debug("Authenticating {}", username);

        //final var user = this.userRepository.findOneWithAuthoritiesByUsernameIgnoreCase( username ).orElseThrow();
        //return this.createSpringSecurityUser( user );

        return usuarioRepository
            .findOneWithAuthoritiesByEmailIgnoreCase(username.toLowerCase())
            .map( this::createSpringSecurityUser )
            .orElseThrow( () -> new UsernameNotFoundException( "El usuario " + username + " no existe" ) );
    }

    private UserDetails createSpringSecurityUser(Usuario user) {
        List<GrantedAuthority> grantedAuthorities = user
                .getAuthorities()
                .stream()
                .map( Authority::getName )
                .map( SimpleGrantedAuthority::new )
                .collect( Collectors.toList() );

        return new User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }

}
