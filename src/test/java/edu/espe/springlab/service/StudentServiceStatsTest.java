package edu.espe.springlab.service;

import edu.espe.springlab.domain.Student;
import edu.espe.springlab.repository.StudentRepository;
import edu.espe.springlab.service.impl.StudentServiceImpl;
import edu.espe.springlab.dto.StudentStats;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(StudentServiceImpl.class)
public class StudentServiceStatsTest {

    @Autowired
    private StudentServiceImpl service;

    @Autowired
    private StudentRepository repository;

    @Test
    void shouldReturnCorrectStats() {
        // 1. Crear 3 estudiantes
        Student s1 = new Student();
        s1.setFullName("Estudiante 1");
        s1.setEmail("estudiante1@example.com");
        s1.setBirthDate(LocalDate.of(2005, 3, 7));
        s1.setActive(true);

        Student s2 = new Student();
        s2.setFullName("Estudiante 2");
        s2.setEmail("estudiante2@example.com");
        s2.setBirthDate(LocalDate.of(2013, 1, 1));
        s2.setActive(true);

        Student s3 = new Student();
        s3.setFullName("Estudiante 3");
        s3.setEmail("estudiante3@example.com");
        s3.setBirthDate(LocalDate.of(2003, 18, 10));
        s3.setActive(false);

        repository.save(s1);
        repository.save(s2);
        repository.save(s3);

        // 2. Llamar al servicio getStats()
        StudentStats stats = service.getById(1);

        // 3. Verificar
        assertThat(stats.getTotal()).isEqualTo(3);
        assertThat(stats.getActive()).isEqualTo(2);
        assertThat(stats.getInactive()).isEqualTo(1);
    }
}
