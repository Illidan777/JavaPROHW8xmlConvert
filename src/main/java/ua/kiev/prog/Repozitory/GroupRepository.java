package ua.kiev.prog.Repozitory;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.kiev.prog.Models.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
