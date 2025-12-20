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
   
}