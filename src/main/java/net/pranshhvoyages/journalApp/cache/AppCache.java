package net.pranshhvoyages.journalApp.cache;

import net.pranshhvoyages.journalApp.entity.ConfigJournalAppApiEntity;
import net.pranshhvoyages.journalApp.repository.ConfigJournalAppApiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {
    public enum keys{
        WEATHER_API;
    }
    @Autowired
    private ConfigJournalAppApiRepository configJournalAppApiRepository;

    public Map<String, String> appCacheList = new HashMap<>();

    @PostConstruct
    public void init(){
        List<ConfigJournalAppApiEntity> all = configJournalAppApiRepository.findAll();
        for(ConfigJournalAppApiEntity configJournalAppApiEntity : all){
            appCacheList.put(configJournalAppApiEntity.getKey(), configJournalAppApiEntity.getValue());
        }
    }
}
