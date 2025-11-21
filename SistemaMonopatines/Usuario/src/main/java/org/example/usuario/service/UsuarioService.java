package org.example.usuario.service;

import lombok.AllArgsConstructor;
import org.example.usuario.client.MonopatinFeignClient;
import org.example.usuario.client.ViajeFeignClient;
import org.example.usuario.client.monopatin.dto.request.MonopatinRequestDTO;
import org.example.usuario.client.monopatin.dto.response.MonopatinResponseDTO;
import org.example.usuario.client.viaje.dto.UsoMonopatinUsuarioDTO;
import org.example.usuario.dto.request.UsuarioRequestDTO;
import org.example.usuario.dto.response.UsoMonopatinCuentaDTO;
import org.example.usuario.dto.response.UsuarioResponseDTO;
import org.example.usuario.entity.Authority;
import org.example.usuario.entity.Usuario;
import org.example.usuario.mapper.UsuarioMapper;
import org.example.usuario.repository.AuthorityRepository;
import org.example.usuario.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional(readOnly = true)
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final MonopatinFeignClient monopatinFeignClient;
    private final ViajeFeignClient viajeFeignClient;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;

    /*-------------------------- MÉTODOS PARA EL CRUD --------------------------*/
    @Transactional
    public UsuarioResponseDTO save(UsuarioRequestDTO request) {
        usuarioRepository.findByEmail(request.getEmail())
                .ifPresent(u -> {
                    throw new IllegalArgumentException("Ya hay un usuario registrado con ese email");
                });

        // 1) encriptar password
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        request.setPassword(encodedPassword);

        // 2) obtener los nombres de las authorities desde el request
        Set<String> authorityNames = request.getAuthorities();

        // 3) buscar las Authority en la BD
        List<Authority> authorities = authorityRepository.findAllById(authorityNames);

        if (authorities.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron authorities para: " + authorityNames);
        }

        // 4) mapear DTO -> Entity (sin authorities todavía)
        Usuario nuevo = UsuarioMapper.convertToEntity(request);

        // 5) setear el Set<Authority> en la entidad
        nuevo.setAuthorities(new HashSet<>(authorities));

        // 6) persistir
        Usuario creado = usuarioRepository.save(nuevo);

        // 7) devolver DTO
        return UsuarioMapper.convertToDTO(creado);
    }

    public List<UsuarioResponseDTO> findAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioMapper::convertToDTO)
                .toList();
    }

    public UsuarioResponseDTO findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el usuario con el id: " + id));
        return UsuarioMapper.convertToDTO(usuario);
    }

    @Transactional
    public UsuarioResponseDTO update(Long id, UsuarioRequestDTO usuario) {
        Usuario usuarioEditar = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el usuario con id: " + id));

        usuarioEditar.setEmail(usuario.getEmail());
        usuarioEditar.setNombre(usuario.getNombre());
        usuarioEditar.setApellido(usuario.getApellido());
        usuarioEditar.setNroCelular(usuario.getNroCelular());
        //usuarioEditar.setAuthorities(usuario.getAuthorities());
        usuarioRepository.save(usuarioEditar);
        return UsuarioMapper.convertToDTO(usuarioEditar);
    }

    @Transactional
    public void delete(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un usuario con el id: "+ id));
        usuarioRepository.delete(usuario);
    }

    /*-------------------- ENDPOINTS PARA LOS SERVICIOS -----------------------*/
    @Transactional
    public MonopatinResponseDTO saveMonopatin(MonopatinRequestDTO monopatin) {
        return monopatinFeignClient.create(monopatin).getBody();
    }

    public List<MonopatinResponseDTO> buscarMonopatinesCercanos(double latitud, double longitud) {
        return monopatinFeignClient.getMonopatinesCercanos(latitud, longitud);
    }

    public UsoMonopatinCuentaDTO obtenerUsoMonopatines(Long idUsuario, LocalDate inicio, LocalDate fin) {
        // Buscar usuario principal
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Obtener los IDs de las cuentas del usuario
        List<Long> cuentasIds = usuario.getCuentas().stream()
                .map(c -> c.getNroCuenta())
                .collect(Collectors.toList());

        // Obtener los IDs de los usuarios que comparten esas cuentas
        List<Long> usuariosRelacionados = usuarioRepository.findIdsByCuentasIdExcepto(cuentasIds, idUsuario);

        // Consultar al microservicio "viaje" para contar los viajes del usuario
        UsoMonopatinUsuarioDTO viajesUsuario =
                viajeFeignClient.contarViajesPorUsuario(
                        idUsuario,
                        inicio.toString(), // convierte "2025-11-06"
                        fin.toString()     // convierte "2025-11-30"
                );

        // Armar DTO final
        return new UsoMonopatinCuentaDTO(
                idUsuario,
                viajesUsuario.getCantidadViajes(),
                usuariosRelacionados
        );
    }
}
