package codesquad.project.baseball.repository;

import codesquad.project.baseball.domain.Game;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameRepository extends CrudRepository<Game, Long> {
    List<Game> findAll();
}
