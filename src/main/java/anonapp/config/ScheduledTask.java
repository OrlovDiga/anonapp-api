package anonapp.config;

import anonapp.service.websocket.ChatWebSocketConnectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Orlov Diga
 */
@Component
@EnableScheduling
public class ScheduledTask {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduledTask.class);
    private ChatWebSocketConnectService connectService;

    @Autowired
    public ScheduledTask(ChatWebSocketConnectService connectService) {
        this.connectService = connectService;
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 5000)
    public void createDialog() throws IOException {
        LOG.info("Search...");
        connectService.getConnectPair();
    }


}
