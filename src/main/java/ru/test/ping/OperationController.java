package ru.test.ping;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.test.ping.entity.Operation;
import ru.test.ping.service.OperationService;

@Controller
public class OperationController {

  public OperationController(OperationService operationService) {
    this.operationService = operationService;
  }

  private OperationService operationService;

  @GetMapping("/operations")
  public String getAll(Model model,
      ParamsRequestOperation params,
      @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
    try {
      Pageable paging = PageRequest.of(page - 1, size);

      Page<Operation> pageOperations = operationService.findAll(params, paging);
      List<Operation> operations = pageOperations.getContent();

      model.addAttribute("operations", operations);
      model.addAttribute("currentPage", pageOperations.getNumber() + 1);
      model.addAttribute("totalItems", pageOperations.getTotalElements());
      model.addAttribute("totalPages", pageOperations.getTotalPages());
      model.addAttribute("pageSize", size);
      model.addAttribute("params", params == null ? new ParamsRequestOperation() : params);
    } catch (Exception e) {
      model.addAttribute("message", e.getMessage());
    }

    return "operations";
  }

}
