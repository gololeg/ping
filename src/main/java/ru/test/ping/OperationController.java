package ru.test.ping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.test.ping.entities.Operation;
import ru.test.ping.entities.Status;

@Controller
public class OperationController {

  @GetMapping("/operations")
  public String getAll(Model model, @RequestParam(required = false) String domain,
      @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
    try {
      List<Operation> operations = new ArrayList<>();
      for(int i=0;i<11;i++){
        operations.add(Operation.builder().domain("domain" +i).createDate(LocalDateTime.now())
                .status(Status.builder().name("Test").build())
                .result("res")
            .build());
      }
      Pageable paging = PageRequest.of(page - 1, size);

      model.addAttribute("operations", operations);
      model.addAttribute("currentPage", 1);
      model.addAttribute("totalItems", 5);
      model.addAttribute("totalPages", 3);
      model.addAttribute("pageSize", size);
    } catch (Exception e) {
      model.addAttribute("message", e.getMessage());
    }

    return "operations";
  }

}
