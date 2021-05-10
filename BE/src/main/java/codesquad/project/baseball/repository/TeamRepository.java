package codesquad.project.baseball.repository;

import codesquad.project.baseball.domain.Team;
import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<Team, Long> {
}
