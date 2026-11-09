package com.hautilargi.footman.clubs.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hautilargi.footman.FootmanConstants;
import com.hautilargi.footman.clubs.dto.ClubBasicDto;
import com.hautilargi.footman.clubs.dto.ClubDto;
import com.hautilargi.footman.clubs.mapper.ClubMapper;
import com.hautilargi.footman.clubs.model.Squad;
import com.hautilargi.footman.clubs.model.Team;
import com.hautilargi.footman.clubs.repository.TeamRepository;
import com.hautilargi.footman.core.processing.MatchTypes;
import com.hautilargi.footman.core.service.ConfigurationService;
import com.hautilargi.footman.core.util.NameGenerator;
import com.hautilargi.footman.core.util.emum.Formations;
import com.hautilargi.footman.core.util.emum.PlayerStatus;
import com.hautilargi.footman.core.util.emum.Positions;
import com.hautilargi.footman.players.model.Player;
import com.hautilargi.footman.players.repository.PlayerRepository;
import com.hautilargi.footman.users.model.User;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final ConfigurationService cs;
    private final ClubMapper clubMapper;

    public TeamService(TeamRepository teamRepository, PlayerRepository playerRepository,
            ConfigurationService cs, ClubMapper clubMapper) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.cs = cs;
        this.clubMapper = clubMapper;
    }

    public Team addNewTeam(String name, int bias) {
        Team newTeam = new Team();
        Random random = new Random();
        newTeam.setName(name);
        newTeam.setActive(true);
        newTeam.setBalance(FootmanConstants.INITIAL_BALANCE);
        newTeam.setTier(cs.getGlobalConfiguration().getLowestTier());
        List<Player> initialPlayers = new ArrayList<>();
        for (int i = 0; i < FootmanConstants.INITIAL_ROSTER_SIZE; i++) {
            int skill = random.nextInt(FootmanConstants.INITIAL_PLAYERS_LOWER_BOUND + bias,
                    FootmanConstants.INITIAL_PLAYERS_UPPER_BOUND + bias);
            Positions position;
            if (i == 0 || i==11 || i==22)
                position = Positions.GOALKEEPER;
            else if ((i > 0 && i <= 4)||(i > 10 && i <= 14))
                position = Positions.DEFENDER;
            else if ((i > 4 && i <= 8)||(i > 14 && i <= 18))
                position = Positions.MIDFIELDER;
            else if ((i > 8 && i <= 10)||(i > 18 && i <= 21))
                position = Positions.STRIKER;
            else{
                System.err.println("This should not happen. Player generated without target position!");
                position = Positions.SUBSTITUTE;
            }
            Player newPlayer = new Player(NameGenerator.getLastName(), NameGenerator.getFirstName(), skill, 18,
                    position);
            initialPlayers.add(newPlayer);
            newPlayer.setTeam(newTeam);
        }
        newTeam.setPlayers(initialPlayers);
        Map<MatchTypes, Squad> defaultSquad = new HashMap<>();
        defaultSquad.put(MatchTypes.LEAGUE,
                new Squad(Formations.FOUR_FOUR_TWO, newTeam, newTeam.getPlayers().subList(0, 11)));
        newTeam.setSquads(defaultSquad);
        teamRepository.save(newTeam);
        System.out.println("Created new Team with a bias of " + bias);
        return newTeam;
    }

    public Team save(Team team) {
        return teamRepository.save(team);
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public List<Team> getAllTeams(boolean onlyActive, int tier) {
        if (tier == 0 && onlyActive == false) {
            return teamRepository.findAll();
        }
        if (tier != 0 && onlyActive == false) {
            return teamRepository.findByTier(tier);
        }
        if (tier != 0 && onlyActive == true) {
            return teamRepository.findByTierAndActive(tier, onlyActive);
        }
        if (tier != 0 && onlyActive == true) {
            return teamRepository.findByActive(onlyActive);
        }
        throw new UnsupportedOperationException("Unsupported combonation of Query Parameters");
    }

    public List<ClubBasicDto> getAllClubs() {
        return clubMapper.toBasicDtos(teamRepository.findAll());
    }

    public ClubBasicDto getClubBasicById(Long id) {
        return teamRepository.findById(id)
                .map(clubMapper::toBasicDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Optional<Team> findById(Long id) {
        return teamRepository.findById(id);
    }

    public Team getTeamByPlayerId(long playerId) {
        return teamRepository.findByPlayers_id(playerId);
    }

    public Team getTeamOwnedBy(User owner) {
        return teamRepository.findByOwner(owner).getFirst();
    }

    public ClubDto getClubOwnedBy(User owner) {
        return clubMapper.toDto(getTeamOwnedBy(owner));
    }

    public ClubDto getClubById(Long id) {
        return teamRepository.findById(id)
                .map(clubMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Team not found: " + id));
    }

    public void relegateTeam(Team team) {
        if (team.getTier() < cs.getGlobalConfiguration().getLowestTier()) {
            team.setTier(team.getTier() + 1);
            teamRepository.save(team);
        }
    }

    public void advanceTeam(Team team) {
        if (team.getTier() != 1) {
            team.setTier(team.getTier() - 1);
            teamRepository.save(team);
        }
    }

    public void deactivateTeam(Team team) {
        team.setActive(false);
        teamRepository.save(team);
        List<Player> teamPlayers = team.getPlayers();
        for (Player player : teamPlayers) {
            player.setPlayerStatus(PlayerStatus.RETIRED);
        }
        playerRepository.saveAll(teamPlayers);
    }

    @Deprecated
    public Team addPlayerToTeam(Player player, Team team) {
        if (team.getPlayers().size() < FootmanConstants.MAX_TEAMSIZE) {
            player.setTeam(team);
            var newPlayers = team.getPlayers();
            newPlayers.add(player);
            team.setPlayers(newPlayers);
            System.out.println("Added player " + player.getFirstName() + " " + player.getLastname() + " to team "
                    + team.getName());
            teamRepository.save(team);
            return team;
        } else {
            throw new IllegalStateException("Team has reached maximum size of " + FootmanConstants.MAX_TEAMSIZE);
        }
    }
}
