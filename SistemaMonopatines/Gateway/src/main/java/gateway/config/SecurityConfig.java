package gateway.config;

import gateway.security.AuthorityConstant;
import gateway.security.jwt.JwtFilter;
import gateway.security.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final TokenProvider tokenProvider;

    public SecurityConfig( TokenProvider tokenProvider ) {
        this.tokenProvider = tokenProvider;
    }

    @Bean // Indica que es un componente de spring
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain( final HttpSecurity http ) throws Exception {
        http
            .csrf( AbstractHttpConfigurer::disable );
        http
            .sessionManagement( s -> s.sessionCreationPolicy( SessionCreationPolicy.STATELESS ) );
        http
            .securityMatcher("/api/**" )
            .authorizeHttpRequests( authz -> authz
                    .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/authenticate").permitAll()

                    .requestMatchers(HttpMethod.POST, "/api/usuarios/cuentas").hasAuthority(AuthorityConstant.CLIENTE)
                    .requestMatchers(HttpMethod.GET,"/api/monopatines/cercanos").hasAuthority(AuthorityConstant.CLIENTE)
                    .requestMatchers(HttpMethod.GET,"/api/usuarios/monopatines-cercanos").hasAuthority(AuthorityConstant.CLIENTE)
                    .requestMatchers(HttpMethod.GET,"/api/usuarios/*/uso-monopatines").hasAuthority(AuthorityConstant.CLIENTE)
                    .requestMatchers(HttpMethod.GET,"/api/viajes/uso-monopatin/cantidad").hasAuthority(AuthorityConstant.CLIENTE)

                    .requestMatchers("/api/chat-ia/**").hasAuthority(AuthorityConstant.CLIENTE)
                    .requestMatchers( "/api/monopatines/**").hasAuthority(AuthorityConstant.ADMINISTRADOR)
                    .requestMatchers( "/api/paradas/**").hasAuthority(AuthorityConstant.ADMINISTRADOR)
                    .requestMatchers( "/api/usuarios/**").hasAuthority(AuthorityConstant.ADMINISTRADOR)
                    .requestMatchers( "/api/viajes/**").hasAuthority(AuthorityConstant.ADMINISTRADOR)

                    .anyRequest().authenticated()
            )
            .httpBasic( Customizer.withDefaults() )
            .addFilterBefore( new JwtFilter( this.tokenProvider ), UsernamePasswordAuthenticationFilter.class );
        return http.build();
    }


}
