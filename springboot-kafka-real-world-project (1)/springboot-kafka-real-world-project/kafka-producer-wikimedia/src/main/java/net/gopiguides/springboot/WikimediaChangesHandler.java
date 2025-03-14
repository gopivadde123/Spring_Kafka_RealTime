package net.gopiguides.springboot;

//import com.launchdarkly.eventsource.EventHandler;

import com.launchdarkly.eventsource.MessageEvent;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;


// This triggers when ever their is new event in wikimedia
public class WikimediaChangesHandler implements BackgroundEventHandler {
    private static final Logger LOGGER= LoggerFactory.getLogger(WikimediaChangesHandler.class);
    private KafkaTemplate<String, String> kafkaTemplate;
    private String topic;
    // Constructor dependency
    public WikimediaChangesHandler(KafkaTemplate<String, String> kafkaTemplate, String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void onOpen() throws Exception {

    }

    @Override
    public void onClosed() throws Exception {

    }
    // When there is new event in wiki media then this onMessage will trigger and will read the event
    // Here we call the kafka template and this handler method
    @Override
    public void onMessage(String s, MessageEvent messageEvent) throws Exception {
      LOGGER.info(String.format("event data -> %s",messageEvent.getData()));
      // this sent event to the topic
      kafkaTemplate.send(topic, messageEvent.getData());
    }

    @Override
    public void onComment(String s) throws Exception {

    }

    @Override
    public void onError(Throwable throwable) {

    }
}
