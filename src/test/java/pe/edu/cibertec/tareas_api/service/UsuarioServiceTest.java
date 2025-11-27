package pe.edu.cibertec.tareas_api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.cibertec.tareas_api.model.Usuario;
import pe.edu.cibertec.tareas_api.repository.UsuarioRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Sahesito");
        usuario.setEmail("sahe@gmail.com");
        usuario.setActivo(true);
    }

    @Test
    void listarTodos() {

        List<Usuario> usuarios = Arrays.asList(usuario);
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<Usuario> resultado = usuarioService.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_Exitoso() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = usuarioService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Sahesito", resultado.get().getNombre());
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    void crear_Exitoso() {
        when(usuarioRepository.existsByEmail(usuario.getEmail())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.crear(usuario);

        assertNotNull(resultado);
        assertEquals("Sahesito", resultado.getNombre());
        assertEquals("sahe@gmail.com", resultado.getEmail());
        verify(usuarioRepository, times(1)).existsByEmail(usuario.getEmail());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }
}
