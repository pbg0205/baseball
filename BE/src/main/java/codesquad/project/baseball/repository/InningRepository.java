package codesquad.project.baseball.repository;

import codesquad.project.baseball.domain.Inning;
import org.springframework.data.repository.CrudRepository;

public interface InningRepository extends CrudRepository<Inning, Long> {
}
