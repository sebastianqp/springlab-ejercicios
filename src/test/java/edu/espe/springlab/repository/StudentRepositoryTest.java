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
    void NoExisteID() {
        long noExistingId = 9999;

        assertThatThrownBy(() -> studentService.getById(noExistingId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("9999");
    }

    @Test
    void prueba6() {
        Student s1 = new Student();
        s1.setFullName("Ana");
        s1.setEmail("ana@example.com");
        s1.setBirthDate(LocalDate.of(2003, 18, 10));
        s1.setActive(true);

        Student s2 = new Student();
        s2.setFullName("Andrea");
        s2.setEmail("andrea@example.com");
        s2.setBirthDate(LocalDate.of(2000, 18, 5));
        s2.setActive(true);

        Student s3 = new Student();
        s3.setFullName("Juan");
        s3.setEmail("juan@example.com");
        s3.setBirthDate(LocalDate.of(2000, 7, 1));
        s3.setActive(true);

        repository.save(s1);
        repository.save(s2);
        repository.save(s3);

        var result = repository.findByFullNameContainingIgnoreCase("an");

        assertThat(result)
                .extracting(Student::getFullName)
                .containsExactlyInAnyOrder("Ana", "Andrea")
                .doesNotContain("Juan");
    }

}
