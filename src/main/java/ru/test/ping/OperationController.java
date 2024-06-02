package ru.test.ping;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.test.ping.entity.Operation;
import ru.test.ping.entity.Status;
import ru.test.ping.repo.OperationRepository;
import ru.test.ping.service.OperationService;

@Controller
public class OperationController {

  public OperationController(OperationService operationService,
      OperationRepository operationRepository) {
    this.operationRepository = operationRepository;
    this.operationService = operationService;
  }

  private OperationRepository operationRepository;
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

  @GetMapping("/operations/{id}")
  public String editOperation(@PathVariable("id") Long id, Model model,
      RedirectAttributes redirectAttributes) {
    try {
      Operation operation = operationRepository.findById(id).get();

      model.addAttribute("operation", operation);
      model.addAttribute("pageTitle", "Edit Operation (ID: " + id + ")");

      return "operation_form";
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("message", e.getMessage());

      return "redirect:/operations";
    }
  }

  @GetMapping("/operations/delete/{id}")
  public String deleteOperation(@PathVariable("id") Long id, Model model,
      RedirectAttributes redirectAttributes) {
    try {
      operationRepository.deleteById(id);

      redirectAttributes.addFlashAttribute("message",
          "The Operation with id=" + id + " has been deleted successfully!");
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("message", e.getMessage());
    }

    return "redirect:/operations";
  }

  @PostMapping(value = "/operations")
  public String edit(Operation operation,
      Model model, RedirectAttributes redirectAttributes) {
    try {
      Operation operationDB = operationRepository.findById(operation.getId()).get();
      operationDB.setStatus(Status.STATUS_3);
      operationDB.setResult(operation.getResult());
      operationDB.setCreateDate(LocalDateTime.now());
      operationRepository.save(operationDB);

      String message = "The operation id=" + operation.getId() + " has been saved";

      redirectAttributes.addFlashAttribute("message", message);
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("message", e.getMessage());
    }

    return "redirect:/operations";
  }

  @PatchMapping(value = "/operations/{id}")
  public String editStatus(Model model, @PathVariable("id") Long id) {
    Operation operationDB = operationRepository.findById(id).get();
    operationDB.setStatus(Status.STATUS_2);
    operationRepository.save(operationDB);
    model.addAttribute("operation", operationDB);

    return "operation_form";
  }


}
