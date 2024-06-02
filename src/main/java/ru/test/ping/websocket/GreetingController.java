package ru.test.ping.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {

  @Autowired
  private SimpMessagingTemplate template;

  @MessageMapping("/ping")
//  @SendTo("/topic/greetings")
  public void greeting(String domain) throws Exception {
    for (int i = 0; i < 11; i++) {
      Thread.sleep(1000); // simulated delay
      template.convertAndSend("/topic/ping", domain + i);
    }
  }

}
