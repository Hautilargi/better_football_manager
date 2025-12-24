package com.hautilargi.footman.core;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hautilargi.footman.clubs.model.Team;
import com.hautilargi.footman.services.ConfigurationService;
import com.hautilargi.footman.services.LeagueService;
import com.hautilargi.footman.services.RepositoryService;

@Component
public class Scheduler {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Autowired
    LeagueService ls;

    @Autowired
    ConfigurationService cs;

    @Autowired
    RepositoryService rs;

	@Scheduled(cron = "*/5 * * * * *", zone = "Europe/Berlin")
	public void dayChangeProcessor(){

		if(cs.getGlobalConfiguration().getCurrentDay()>35 && cs.getGlobalConfiguration().getCurrentSeason()>=3){
			System.out.println("End of S3 reached. Pausing myself");
			try {
				cs.setStatus("SLEEP");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		if(cs.getGlobalConfiguration().getServerStatus().equals("OK")){
		//update day
		System.out.println("Processing day "+cs.getGlobalConfiguration().getCurrentDay());
		int currentDay = cs.increaseCurrentDay();
		//process new matches && Generate new Tables
		//TODO Seperate Steps?
		if(currentDay>0 && currentDay<35){
				ls.playMatchDay(currentDay);
				//generate new tables
			}
		if(currentDay>=35 && currentDay <=40){
						System.out.println("Summerbreak, nothing happens");
		}
		if(currentDay>40){
			System.out.println("Season wrap up");
			System.out.println("Performing relegation and Cleanup");
			performRelegation();
			System.out.println("Relegation compplete. Increasing season number");
			cs.increaseCurrentSeason();
			for(int i=1; i <= cs.getGlobalConfiguration().getLowestTier();i++){
				System.out.println("Creating new schedule for league tier "+i);
				initNewLeagueTier(cs.getGlobalConfiguration().getCurrentSeason(),i);
			}
			cs.setCurrentDay(0);
			System.out.println("Next Season starts now");
			
		}
	}
}

	private void performRelegation(){
					List<Team> allTeams= rs.getAllTeams(false,0);
					for(Team nextTeam:allTeams){
						if(nextTeam.getName().startsWith("Placeholder")){
						rs.deactivateTeam(nextTeam);	
					}
				}
	}

	private void initNewLeagueTier(long season, int tier){
			List<Team> allTeams= rs.getAllTeams(true,tier);
			Collections.shuffle(allTeams);
			int teamcount=allTeams.size();
			int noProcessedTeams = 0;
			int leagueIndex=1;
			List<Team> nextLeague=new ArrayList<>();
			System.out.println(String.format("Found %s Teams in tier", allTeams.size()));
			while (noProcessedTeams < teamcount){
				nextLeague.add(allTeams.get(noProcessedTeams++));	
				if(nextLeague.size()==18  || noProcessedTeams==teamcount){
					ls.addLeague(season, nextLeague, tier, leagueIndex++);
					nextLeague.clear();
				}
			}
			if(noProcessedTeams!=teamcount){
				System.err.println("Something went wrong. A team got missing for next season");
			}

	}
}