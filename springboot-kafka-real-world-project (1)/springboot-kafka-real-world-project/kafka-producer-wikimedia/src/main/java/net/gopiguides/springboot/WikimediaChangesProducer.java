package net.gopiguides.springboot;

import com.launchdarkly.eventsource.ConnectStrategy;
import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import com.launchdarkly.eventsource.background.BackgroundEventSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service // we are making spring bean by adding @Service
public class WikimediaChangesProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaChangesProducer.class);
    private KafkaTemplate<String, String> kafkaTemplate;

    public WikimediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendMessage(){
        String topic="wikimedia_recentchange";
        // In-order to read real time wikimedia stream data we are going to use source
        BackgroundEventHandler eventHandler=new WikimediaChangesHandler(kafkaTemplate,topic);
        String url="https://stream.wikimedia.org/v2/stream/recentchange";
        // we need to pass the url to the event source
        // we need to create event source which will basically connect to the source
        // that is wiki media source and read the all the event data
//        EventSource.Builder builder=new EventSource.Builder(eventHandler,URI.create(url))
        // eventsource fill the all real time data from the above url and trigger the respective handler
        // Handler method we written code to send that event to topic
        BackgroundEventSource eventSource=new BackgroundEventSource.Builder(eventHandler,
                new EventSource.Builder(ConnectStrategy.http(URI.create(url)))).build();
       // start in separate thread
        eventSource.start();
        try{
            TimeUnit.MINUTES.sleep(10);
        }catch(InterruptedException e){
            LOGGER.error(String.format("Error: %e", e.getMessage()));
            throw new RuntimeException("An error has occured");
        }
    }
}
