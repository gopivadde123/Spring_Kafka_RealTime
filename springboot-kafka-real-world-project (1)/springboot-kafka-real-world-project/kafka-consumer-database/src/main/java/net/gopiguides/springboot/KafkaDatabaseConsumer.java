package net.gopiguides.springboot;

import net.gopiguides.springboot.entity.WikimediaData;
import net.gopiguides.springboot.repository.WikimediaDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service // this makes class as a spring bean
public class KafkaDatabaseConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDatabaseConsumer.class);



    private WikimediaDataRepository dataRepository;
    // No need @Autowire because this 'KafkaDatabaseConsumer' spring bean -
    // contains one parametirized constructor and injected 'WikimediaDataRepository'
    public KafkaDatabaseConsumer(WikimediaDataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }
    @KafkaListener(topics = "wikimedia_recentchange",
    groupId = "myGroup")
    public void consume(String eventMessage){
        LOGGER.info(String.format("EVENT Message received -> %s",eventMessage));
        WikimediaData wikimediaData=new WikimediaData();
        wikimediaData.setWikiEventData(eventMessage);
        dataRepository.save(wikimediaData);
    }
}
