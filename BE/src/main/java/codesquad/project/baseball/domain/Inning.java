package codesquad.project.baseball.domain;

import codesquad.project.baseball.dto.InningDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Table("INNING")
public class Inning {
    @Id
    private Long inningId;

    private Long gameId;

    private Long teamId;

    private Long nowBatterId;

    private Long nowPitcherId;

    private int inningNumber;

    private int score;

    private String nowBallCount;

    private int nowOutCount;

    private String nowBaseStatus;

    public Inning(Long inningId, Long gameId, Long teamId, Long nowBatterId, Long nowPitcherId, int inningNumber,
                  int score, String nowBallCount, int nowOutCount, String nowBaseStatus) {
        this.inningId = inningId;
        this.gameId = gameId;
        this.teamId = teamId;
        this.nowBatterId = nowBatterId;
        this.nowPitcherId = nowPitcherId;
        this.inningNumber = inningNumber;
        this.score = score;
        this.nowBallCount = nowBallCount;
        this.nowOutCount = nowOutCount;
        this.nowBaseStatus = nowBaseStatus;
    }

    public Long getInningId() {
        return inningId;
    }

    public Long getGameId() {
        return gameId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public Long getNowBatterId() {
        return nowBatterId;
    }

    public Long getNowPitcherId() {
        return nowPitcherId;
    }

    public int getInningNumber() {
        return inningNumber;
    }

    public int getScore() {
        return score;
    }

    public String getNowBallCount() {
        return nowBallCount;
    }

    public int getNowOutCount() {
        return nowOutCount;
    }

    public String getNowBaseStatus() {
        return nowBaseStatus;
    }

    public boolean isSameInning(Long inningId) {
        return this.inningId == inningId;
    }

    public void updateFromInningDto(InningDto inningDto) {
        this.inningId = inningDto.getInningId();
        this.gameId = inningDto.getGameId();
        this.teamId = inningDto.getTeamId();
        this.nowBatterId = inningDto.getBatterId();
        this.nowPitcherId = inningDto.getPitchId();
        this.inningNumber = inningDto.getInningNumber();
        this.score = inningDto.getScore();
        this.nowBallCount = inningDto.getNowBallCount();
        this.nowOutCount = inningDto.getNowOutCount();
        this.nowBaseStatus = convertBaseStatusString(inningDto.getBaseStatus());
    }

    private String convertBaseStatusString(List<String> baseStatus) {
        return String.join("", baseStatus);
    }

    @Override
    public String toString() {
        return "Inning{" +
                "inningId=" + inningId +
                ", gameId=" + gameId +
                ", nowBatterId=" + nowBatterId +
                ", nowPitcherId=" + nowPitcherId +
                ", inningNumber=" + inningNumber +
                ", score=" + score +
                ", nowBallCount='" + nowBallCount + '\'' +
                ", nowOutCount=" + nowOutCount +
                ", nowBaseStatus=" + nowBaseStatus +
                '}';
    }
}
