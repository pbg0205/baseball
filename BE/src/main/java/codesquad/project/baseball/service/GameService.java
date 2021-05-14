package codesquad.project.baseball.service;

import codesquad.project.baseball.controller.GameController;
import codesquad.project.baseball.domain.*;
import codesquad.project.baseball.dto.BallCountDto;
import codesquad.project.baseball.dto.GameDto;
import codesquad.project.baseball.dto.InningDto;
import codesquad.project.baseball.repository.BattingStatusRepository;
import codesquad.project.baseball.repository.GameRepository;
import codesquad.project.baseball.repository.TeamRepository;
import codesquad.project.baseball.utils.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

    private final GameRepository gameRepository;
    private final TeamRepository teamRepository;
    private final BattingStatusRepository battingStatusRepository;

    public GameService(GameRepository gameRepository, TeamRepository teamRepository,
                       BattingStatusRepository battingStatusRepository) {
        this.gameRepository = gameRepository;
        this.teamRepository = teamRepository;
        this.battingStatusRepository = battingStatusRepository;
    }

    public List<GameDto> getGameList() {
        return gameRepository.findAll().stream()
                .map(game -> new GameDto(game,
                        findTeam(game.getHomeTeamId()),
                        findTeam(game.getAwayTeamId())))
                .collect(Collectors.toList());
    }

    public InningDto getInningDtoInGame(Long gameId, Long inningId) {
        Game game = gameRepository.findById(gameId).orElseThrow(RuntimeException::new);
        Inning nowInning = findInningFromInnings(game.getInnings(), inningId);
        Long nowAttackTeamId = nowInning.getTeamId();

        LOGGER.debug("nowInning : {}", nowInning);

        Player nowBatter = findBatter(nowAttackTeamId, nowInning.getNowBatterId());
        Player nowPitcher = findPitcher(getDepenseTeamId(game, nowAttackTeamId));

        LOGGER.debug("now batter : {}", nowBatter);
        LOGGER.debug("now pitcher : {}", nowPitcher);

        return new InningDto(nowInning, game, nowBatter, nowPitcher);
    }

    private Long getDepenseTeamId(Game game, Long attackTeamId) {
        if (game.getHomeTeamId() == attackTeamId) {
            return game.getAwayTeamId();
        }
        return game.getHomeTeamId();
    }

    public Inning findInningFromInnings(List<Inning> innings, Long inningId) {
        return innings.stream()
                .filter(inning -> inning.isSameInning(inningId))
                .findFirst().orElseThrow(RuntimeException::new);
    }

    public Team findTeam(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(RuntimeException::new);
    }

    public Player findBatter(Long teamId, Long playerId) {
        return findTeam(teamId).getPlayer(playerId);
    }

    private Long getNextBatterId(Long teamId, Long batterId) {
        Long nextBatterId = batterId + 1;
        if (nextBatterId > teamId * 9) {
            nextBatterId -= 9;
        }
        return nextBatterId;
    }

    public Player findPitcher(Long teamId) {
        return findTeam(teamId).getPlayers().stream().filter(player -> player.isPitcher())
                .findFirst().orElseThrow(RuntimeException::new);
    }

    public InningDto pitch(Long gameId, Long inningId, InningDto inningDto) {
        Game game = gameRepository.findById(gameId).orElseThrow(RuntimeException::new);
        Inning inning = game.getInningByInningId(inningId);
        BattingStat battingStat = getNowBatterStat(gameId, inningDto.getBatterId());
        String ballTypeValue = BallCount.findBallTypeValue(RandomUtils.generateBallCountNumber());

        inningDto.appendBallCount(ballTypeValue);

        LOGGER.debug("ballTypeValue : {}", ballTypeValue);
        LOGGER.debug("ballCount : {}", inningDto.getNowBallCount());

        if (inningDto.hitBall()) {
            inningDto.moveBase(game, inningDto.getTeamId());

            battingStat.addHit();
            battingStatusRepository.save(battingStat);

            updateToNextBatter(inning, inningDto);
            gameRepository.save(game);
            return inningDto;
        }

        LOGGER.debug("notHitInningDto : {}", inningDto);

        checkBallCount(game, inning, inningDto, inningDto.getNowBallCount());

        gameRepository.save(game);

        return inningDto;
    }

    private BattingStat getNowBatterStat(Long gameId, Long batterNumber) {
        return battingStatusRepository.findByGameIdAndPlayerId(gameId, batterNumber)
                .orElseThrow(RuntimeException::new);
    }

    private void updateToNextBatter(Inning inning, InningDto inningDto) {
        Long nextBatterId = getNextBatterId(inningDto.getTeamId(), inningDto.getBatterId());
        Player nextBatterPlayer = findBatter(inningDto.getTeamId(), nextBatterId);

        LOGGER.debug("nextBatterId : {}", nextBatterId);
        LOGGER.debug("nextBatterPlayer : {}", nextBatterPlayer);

        inningDto.updateToBatter(nextBatterPlayer);
        inning.updateFromInningDto(inningDto);

        inningDto.updateToBatter(nextBatterPlayer);
    }

    private void checkBallCount(Game game, Inning inning, InningDto inningDto, List<String> ballCount) {
        BallCountDto ballCountDto = new BallCountDto(ballCount);
        BattingStat battingStat = getNowBatterStat(game.getGameId(), inningDto.getBatterId());

        LOGGER.debug("ballCountDto : {}", ballCountDto);

        if (ballCountDto.isOut()) {
            inningDto.addOutCount();
            updateToNextBatter(inning, inningDto);
            battingStat.addOut();
        }

        if (ballCountDto.isFourBall()) {
            inningDto.moveBase(game, inningDto.getTeamId());
            updateToNextBatter(inning, inningDto);
            battingStat.addHit();
        }

        inning.updateFromInningDto(inningDto);

        LOGGER.debug("updatedInning : {}", inning);

        battingStatusRepository.save(battingStat);

        if (inningDto.isThreeOut()) {
            changeNextInning(game, inningDto);
        }
    }

    public List<Inning> getTeamInnings(Game game, Long teamId) {
        List<Inning> inningList = game.getInnings();
        return inningList.stream()
                .filter(inning -> inning.getTeamId() == teamId)
                .collect(Collectors.toList());
    }

    private void changeNextInning(Game game, InningDto inningDto) {
        Long attackTeamId = inningDto.getTeamId();
        Long depenseTeamId = getDepenseTeamId(game, attackTeamId);
        List<Inning> depenseTeamList = getTeamInnings(game, depenseTeamId);
        depenseTeamList.sort(Comparator.comparingInt(Inning::getInningNumber));


        LOGGER.debug("attackTeam : {}", attackTeamId);
        LOGGER.debug("depenseTeam : {}", depenseTeamId);

        //TODO 9회말에 경기종료 조건 추가하기

        Inning nextInning = depenseTeamList.stream().filter(inning -> inning.getNowOutCount() == 0)
                .findFirst().orElseThrow(RuntimeException::new);

        LOGGER.debug("nextInning: {}", nextInning);

        Player pitcher = findPitcher(attackTeamId);
        Player nextBatter = findBatter(depenseTeamId, getNextBatterId(depenseTeamId, depenseTeamId * 9));

        LOGGER.debug("pitcher : {}", pitcher);
        LOGGER.debug("nextBatter : {}", nextBatter);

        if (nextInning.getInningNumber() == 1) {
            inningDto.updateNextInningStatus(nextInning, nextBatter, pitcher, depenseTeamId);
            LOGGER.debug("nextBatter : {}", inningDto);
            return;
        }

        int prevInningIndex = nextInning.getInningNumber() - 2;
        Inning prevInning = depenseTeamList.get(prevInningIndex);

        LOGGER.debug("prevInningIndex : {}", prevInningIndex);
        LOGGER.debug("list : {}", depenseTeamList);
        LOGGER.debug("prevInning : {}", prevInning);

        nextBatter = findBatter(depenseTeamId, getNextBatterId(depenseTeamId, prevInning.getNowBatterId()));

        inningDto.updateNextInningStatus(nextInning, nextBatter, pitcher, depenseTeamId);
    }
}
