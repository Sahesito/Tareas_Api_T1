package pe.edu.cibertec.tareas_api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.cibertec.tareas_api.model.Usuario;
import pe.edu.cibertec.tareas_api.model.Proyecto;
import pe.edu.cibertec.tareas_api.repository.ProyectoRepository;
import pe.edu.cibertec.tareas_api.repository.UsuarioRepository;


import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProyectoServiceTest {

    @Mock
    private ProyectoRepository proyectoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ProyectoService proyectoService;

    private Proyecto proyecto;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Sahesito");
        usuario.setEmail("sahe@gmail.com");
        usuario.setActivo(true);

        proyecto = new Proyecto();
        proyecto.setId(1L);
        proyecto.setNombre("Sistema de gestion");
        proyecto.setDescripcion("Sistema web para gestion de tareas");
        proyecto.setFechaInicio(LocalDate.of(2025,11,26));
        proyecto.setFechaFin(LocalDate.of(2025,11,27));
        proyecto.setUsuario(usuario);
        proyecto.setActivo(true);
    }

    @Test
    void listarTodos() {
        List<Proyecto> proyectos = Arrays.asList(proyecto);
        when(proyectoRepository.findAll()).thenReturn(proyectos);

        List<Proyecto> resultado = proyectoService.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(proyectoRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_Exitoso() {
        when(proyectoRepository.findById(1L)).thenReturn(Optional.of(proyecto));

        Optional<Proyecto> resultado = proyectoService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Sistema de gestion", resultado.get().getNombre());
        verify(proyectoRepository, times(1)).findById(1L);
    }

    @Test
    void crear_Exitoso() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(proyectoRepository.save(any(Proyecto.class))).thenReturn(proyecto);

        Proyecto resultado = proyectoService.crear(proyecto);

        assertNotNull(resultado);
        assertEquals("Sistema de gestion", resultado.getNombre());
        verify(usuarioRepository, times(1)).findById(1L);
        verify(proyectoRepository, times(1)).save(any(Proyecto.class));
    }

    @Test
    void crear_FechaInvalida() {
        proyecto.setFechaInicio(LocalDate.of(2025,11,27));
        proyecto.setFechaFin(LocalDate.of(2025,11,26));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
           proyectoService.crear(proyecto);
        });

        assertEquals("La fecha de fin debe ser mayor a la fecha de inicio", exception.getMessage());

    }
}
