package codesquad.project.baseball.service;

import codesquad.project.baseball.controller.GameController;
import codesquad.project.baseball.domain.*;
import codesquad.project.baseball.dto.BallCountDto;
import codesquad.project.baseball.dto.GameDto;
import codesquad.project.baseball.dto.InningDto;
import codesquad.project.baseball.repository.BattingStatusRepository;
import codesquad.project.baseball.repository.GameRepository;
import codesquad.project.baseball.repository.InningRepository;
import codesquad.project.baseball.repository.TeamRepository;
import codesquad.project.baseball.utils.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

    private final GameRepository gameRepository;
    private final InningRepository inningRepository;
    private final TeamRepository teamRepository;
    private final BattingStatusRepository battingStatusRepository;

    public GameService(GameRepository gameRepository, InningRepository inningRepository,
                       TeamRepository teamRepository, BattingStatusRepository battingStatusRepository) {
        this.gameRepository = gameRepository;
        this.inningRepository = inningRepository;
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

    private Long getDepenseTeamId(Game game, Long teamId) {
        if(game.getHomeTeamScore() != teamId) {
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
        if(nextBatterId > teamId * 9) {
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
        BattingStat battingStat = getNowBatterStat(gameId, inningDto.getBatterNumber());
        char ballTypeValue = BallCount.findBallTypeValue(RandomUtils.generateBallCountNumber());

        inningDto.appendBallCount(ballTypeValue);

        LOGGER.debug("ballTypeValue : {}", ballTypeValue);
        LOGGER.debug("ballCount : {}", inningDto.getNowBallCount());

        if(inningDto.hitBall()) {
            inningDto.moveBase();

            battingStat.addHit();
            battingStatusRepository.save(battingStat);

            updateToNextBatter(inningDto);
            return inningDto;
        }

        checkBallCount(game, inningDto, inningDto.getNowBallCount());

        return inningDto;
    }

    private BattingStat getNowBatterStat(Long gameId, Long batterNumber) {
        return battingStatusRepository.findByGameIdAndPlayerId(gameId, batterNumber)
                .orElseThrow(RuntimeException::new);
    }

    private void updateToNextBatter(InningDto inningDto) {
        Inning inning = inningRepository.findById(inningDto.getInningId()).orElseThrow(RuntimeException::new);

        Long nextBatterId = getNextBatterId(inningDto.getTeamId(), inningDto.getBatterId());
        Player nextBatterPlayer = findBatter(inningDto.getTeamId(), nextBatterId);

        LOGGER.debug("nextBatterId : {}", nextBatterId);
        LOGGER.debug("nextBatterPlayer : {}", nextBatterPlayer);

        inning.updateToNextBatterId(nextBatterId);
        inningRepository.save(inning);

        inningDto.updateToNextBatter(nextBatterPlayer);
    }

    private void checkBallCount(Game game, InningDto inningDto, String ballCount) {
        BallCountDto ballCountDto = new BallCountDto(ballCount);
        BattingStat battingStat = getNowBatterStat(game.getGameId(), inningDto.getBatterNumber());

        LOGGER.debug("ballCountDto : {}", ballCountDto);

        if(ballCountDto.isOut()) {
            inningDto.addOutCount();
            checkThreeOut(game, inningDto);
            battingStat.addOut();
        }

        if(ballCountDto.isFourBall()) {
            inningDto.moveBase();
            updateToNextBatter(inningDto);
            battingStat.addHit();
        }

        battingStatusRepository.save(battingStat);
    }

    public List<Inning> getTeamInnings (Game game, Long teamId) {
        List<Inning> inningList = game.getInnings();
        return inningList.stream()
                .filter(inning -> inning.getTeamId() == teamId)
                .collect(Collectors.toList());
    }

    private void checkThreeOut(Game game, InningDto inningDto) {
        if(!inningDto.isAbleToChangeInning()) {
            return;
        }

        Long attackTeamId = inningDto.getTeamId();
        Long depenseTeamId = getDepenseTeamId(game, attackTeamId);
        List<Inning> depenseTeamList = getTeamInnings(game, depenseTeamId);

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
            inningDto.updateInningDto(nextInning, game, nextBatter, pitcher);
            return;
        }

        int prevInningIndex = nextInning.getInningNumber() - 2;
        Inning prevInning = depenseTeamList.get(prevInningIndex);

        nextBatter = findBatter(depenseTeamId, getNextBatterId(depenseTeamId, prevInning.getNowBatterId()));

        inningDto.updateInningDto(nextInning, game, nextBatter, pitcher);
    }
}
