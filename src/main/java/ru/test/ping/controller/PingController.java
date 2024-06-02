package ru.test.ping.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class PingController {

  public PingController(SimpMessagingTemplate template) {
    this.template = template;
  }

  private SimpMessagingTemplate template;

  @MessageMapping("/ping")
  public void ping(String domain) throws IOException {
    Runtime rt = Runtime.getRuntime();
    String[] commands = {"ping", domain};
    Process process = rt.exec(commands);

    BufferedReader stdInput = new BufferedReader(new
        InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));

    String s;
    while ((s = stdInput.readLine()) != null) {
      template.convertAndSend("/topic/ping", s);
    }
  }

}
