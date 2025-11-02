package org.example.usuario.service;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.usuario.dto.request.UsuarioRequestDTO;
import org.example.usuario.dto.response.UsuarioResponseDTO;
import org.example.usuario.entity.Usuario;
import org.example.usuario.mapper.UsuarioMapper;
import org.example.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;


    /*-------------------------- MÉTODOS PARA EL CRUD --------------------------*/
    public UsuarioResponseDTO save(UsuarioRequestDTO request) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(request.getEmail());
        if (usuario.isPresent()) {
            throw new IllegalArgumentException("Ya hay un usuario registrado con ese email");
        }

        Usuario nuevo = UsuarioMapper.convertToEntity(request);
        Usuario creado = usuarioRepository.save(nuevo);
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

    public UsuarioResponseDTO update(Long id, UsuarioRequestDTO usuario) {
        Usuario usuarioEditar = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el usuario con id: " + id));

        usuarioEditar.setEmail(usuario.getEmail());
        usuarioEditar.setNombre(usuario.getNombre());
        usuarioEditar.setApellido(usuario.getApellido());
        usuarioEditar.setNroCelular(usuario.getNroCelular());

        Usuario usuarioPersistido = usuarioRepository.save(usuarioEditar);
        return UsuarioMapper.convertToDTO(usuarioPersistido);
    }

    public void delete(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un usuario con el id: "+ id));
        usuarioRepository.delete(usuario);
    }
}
