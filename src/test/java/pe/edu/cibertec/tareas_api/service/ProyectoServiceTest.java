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
    }
}
