package ru.test.ping.websocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {

  @Autowired
  private SimpMessagingTemplate template;

  @MessageMapping("/ping")
  public void ping(String domain) throws Exception {
    Runtime rt = Runtime.getRuntime();
    String[] commands = {"ping", domain};
    Process process = rt.exec(commands);

    BufferedReader stdInput = new BufferedReader(new
        InputStreamReader(process.getInputStream(), "UTF-8"));

    BufferedReader stdError = new BufferedReader(new
        InputStreamReader(process.getErrorStream()));

    String s = null;
    while ((s = stdInput.readLine()) != null) {
      template.convertAndSend("/topic/ping", s);
    }
  }

}
