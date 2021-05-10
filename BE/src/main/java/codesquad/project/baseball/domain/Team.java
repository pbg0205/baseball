package codesquad.project.baseball.domain;

import org.springframework.data.annotation.Id;

public class Team {
    @Id
    private Long teamId;

    private String teamName;

    public Team(Long teamId, String teamName) {
        this.teamId = teamId;
        this.teamName = teamName;
    }

    public Long getTeamId() {
        return teamId;
    }

    public String getTeamName() {
        return teamName;
    }
}
