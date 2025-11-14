package edu.espe.springlab.repository;

import edu.espe.springlab.domain.Student;
import edu.espe.springlab.service.StudentService;
import edu.espe.springlab.web.advice.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository repository;
    @Autowired
    private StudentService studentService;

    @Test
    void shouldSaveAndFindStudentByEmail() {
        // Crear el estudiante correctamente
        Student s = new Student();
        s.setFullName("Test User");
        s.setEmail("test@example.com");
        s.setBirthDate(LocalDate.of(2000, 10, 10));
        s.setActive(true);

        // Guardar en la base de datos en memoria (H2)
        repository.save(s);

        // Buscar por email
        var result = repository.findByEmail("test@example.com");

        assertThat(result).isPresent();
        assertThat(result.get().getFullName()).isEqualTo("Existing User");



    }
    @Test
    void shouldThrowNotFoundWhenIdDoesNotExist() {
        long nonExistingId = 9999L;

        assertThatThrownBy(() -> studentService.getById(nonExistingId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("9999");
    }

}
