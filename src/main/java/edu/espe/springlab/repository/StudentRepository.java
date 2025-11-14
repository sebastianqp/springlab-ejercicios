package edu.espe.springlab.repository;

import edu.espe.springlab.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    //Buscar un estudiante por email
    List<Student> findByFullNameContainingIgnoreCase(String partialName);

    Optional<Student> findByEmail(String email);
    //Responder si existe el estudiante con ese email
    boolean existsByEmail(String email);
}
