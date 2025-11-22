package gateway.client;

import gateway.client.dto.user.UsuarioTokenResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "usuario-service", url = "http://localhost:8081/api/auth")
public interface UsuarioClient {

        @GetMapping("/{email}")
        Optional<UsuarioTokenResponseDTO> findOneWithAuthoritiesByEmailIgnoreCase(@PathVariable String email);
}
