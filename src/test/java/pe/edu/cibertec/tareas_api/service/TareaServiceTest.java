package pe.edu.cibertec.tareas_api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.cibertec.tareas_api.model.Proyecto;
import pe.edu.cibertec.tareas_api.model.Tarea;
import pe.edu.cibertec.tareas_api.model.Usuario;
import pe.edu.cibertec.tareas_api.repository.ProyectoRepository;
import pe.edu.cibertec.tareas_api.repository.TareaRepository;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TareaServiceTest {

    @Mock
    private TareaRepository tareaRepository;

    @Mock
    private ProyectoRepository proyectoRepository;

    @InjectMocks
    private TareaService tareaService;

    private Tarea tarea;
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
        proyecto.setFechaInicio(LocalDate.of(2025, 1, 15));
        proyecto.setFechaFin(LocalDate.of(2025, 6, 30));
        proyecto.setUsuario(usuario);
        proyecto.setActivo(true);

        tarea = new Tarea();
        tarea.setId(1L);
        tarea.setTitulo("Diseño de base de datos");
        tarea.setDescripcion("Crear el modelo entidad-relacion");
        tarea.setEstado("EN_PROGRESO");
        tarea.setPrioridad("ALTA");
        tarea.setProyecto(proyecto);
        tarea.setActivo(true);
    }

    @Test
    void listarTodos() {
        List<Tarea> tareas = Arrays.asList(tarea);
        when(tareaRepository.findAll()).thenReturn(tareas);

        List<Tarea> resultado = tareaService.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(tareaRepository, times(1)).findAll();
    }

    @Test
    void crear_Exitoso() {
        when(proyectoRepository.findById(1L)).thenReturn(Optional.of(proyecto));
        when(tareaRepository.save(any(Tarea.class))).thenReturn(tarea);

        Tarea resultado = tareaService.crear(tarea);

        assertNotNull(resultado);
        assertEquals("Diseño de base de datos", resultado.getTitulo());
        assertEquals("EN_PROGRESO", resultado.getEstado());
        verify(proyectoRepository, times(1)).findById(1L);
        verify(tareaRepository, times(1)).save(any(Tarea.class));
    }

    @Test
    void crear_EstadoInvalido() {
        tarea.setEstado("ESTADO_INVALIDO");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            tareaService.crear(tarea);
        });

        assertEquals("Proyecto no encontrado", exception.getMessage());
    }
}

