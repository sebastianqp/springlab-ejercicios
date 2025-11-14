package edu.espe.springlab.service;

import edu.espe.springlab.domain.Student;
import edu.espe.springlab.dto.StudentRequestData;
import edu.espe.springlab.repository.StudentRepository;
import edu.espe.springlab.service.impl.StudentServiceImpl;
import edu.espe.springlab.web.advice.ConflictException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import(StudentServiceImpl.class)
public class StudentServiceTest {

    @Autowired
    private StudentServiceImpl service;

    @Autowired
    private StudentRepository repository;

    @Test
    void shouldNotAllowDuplicateEmail() {
        // Crear y guardar un estudiante existente
        Student existing = new Student();
        existing.setFullName("Existing User");
        existing.setEmail("test@example.com");
        existing.setBirthDate(LocalDate.of(2000, 10, 10));
        existing.setActive(true);

        repository.save(existing);

        // Crear la solicitud con el mismo email
        StudentRequestData req = new StudentRequestData();
        req.setFullName("Another User");
        req.setEmail("test@example.com");
        req.setBirthDate(LocalDate.of(1999, 5, 15));

        // Verificar que el servicio lanza excepción por email duplicado
        assertThatThrownBy(() -> service.create(req)).isInstanceOf(ConflictException.class);


        // Verificar que no se duplique el registro
        var result = repository.findByEmail("test@example.com");

        // Verificar que el resultado existe y coincide
        assertThat(result).isPresent();
        assertThat(result.get().getFullName()).isEqualTo("Test User");
    }
    @Test
    void DesactivarEstudiante() {
        // 1. Crear estudiante activo
        Student s = new Student();
        s.setFullName("Test User");
        s.setEmail("test@example.com");
        s.setBirthDate(LocalDate.of(2000, 10, 10));
        s.setActive(true);

        Student saved = repository.save(s);
        Long id = saved.getId();

        // 2. Llamar al metodo de desactivacion
        service.deactivate(id);

        // 3. Recuperar el estudiante del repositorio
        Student reloaded = repository.findById(id).orElseThrow();

        // 4. Verificar:
        assertThat(reloaded.getActive()).isFalse();
        assertThat(reloaded.getFullName()).isEqualTo("Test User");
        assertThat(reloaded.getEmail()).isEqualTo("test@example.com");
        assertThat(reloaded.getBirthDate()).isEqualTo(LocalDate.of(2000, 10, 10));
    }
}

