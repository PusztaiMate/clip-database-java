package hu.pumate.clipdb.persistence;

import hu.pumate.clipdb.entity.Clip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClipRepository extends JpaRepository<Clip, Long> {
}
