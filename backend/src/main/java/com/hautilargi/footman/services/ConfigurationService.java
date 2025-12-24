package com.hautilargi.footman.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hautilargi.footman.config.model.GlobalConfiguration;
import com.hautilargi.footman.config.repository.GlobalConfigurationRepository;


@Service
public class ConfigurationService {

    @Autowired
    GlobalConfigurationRepository  globalConfiguationRepository;


    public ConfigurationService() {
    }

    public GlobalConfiguration getGlobalConfiguration(){
        if(globalConfiguationRepository.findById(Long.valueOf(1)).isPresent()){
            return globalConfiguationRepository.findById(Long.valueOf(1)).get();
            }
        return null;
    }

    public void setGlobalConfiguration(GlobalConfiguration globalConfiguration){
            if (globalConfiguration != null){
                globalConfiguationRepository.save(globalConfiguration);
            }
            else{
                throw new NullPointerException();
            }
    }

    public int increaseCurrentDay(){
        GlobalConfiguration gc = getGlobalConfiguration();
        gc.setCurrentDay(gc.getCurrentDay()+1);
        globalConfiguationRepository.save(gc);
        return gc.getCurrentDay();
    }

    public long increaseCurrentSeason(){
        GlobalConfiguration gc = getGlobalConfiguration();
        gc.setCurrentSeason(gc.getCurrentSeason()+1);
        globalConfiguationRepository.save(gc);
        return gc.getCurrentSeason();
    }

    public void setCurrentDay(int day){
        GlobalConfiguration gc = getGlobalConfiguration();
        gc.setCurrentDay(day);
        globalConfiguationRepository.save(gc);
    }

    public void setStatus(String status) throws Exception{
        if(status.equals("OK") || status.equals("SLEEP")){
            GlobalConfiguration gc = getGlobalConfiguration();
            gc.setServerStatus(status);
            globalConfiguationRepository.save(gc);
            System.out.println("Set Status to: "+status);
        }
        else throw new Exception("Value not allowed "+status);

     }
}