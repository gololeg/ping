package ru.test.ping.controller;

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
import ru.test.ping.params.ParamsRequestOperation;
import ru.test.ping.entity.Operation;
import ru.test.ping.entity.Status;
import ru.test.ping.repo.OperationRepository;
import ru.test.ping.service.OperationService;

@Controller
public class OperationController {

private static final String MESSAGE = "message";
  private static final String OPERATION = "operation";
  private static final String REDIRECT_OPERATIONS = "redirect:/operations";
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
      model.addAttribute(MESSAGE, e.getMessage());
    }

    return "operations";
  }

  @GetMapping("/operations/{id}")
  public String editOperation(@PathVariable("id") Long id, Model model,
      RedirectAttributes redirectAttributes) {
    try {
      Operation operation = operationRepository.getReferenceById(id);

      model.addAttribute(OPERATION, operation);
      model.addAttribute("pageTitle", "Edit Operation (ID: " + id + ")");

      return "operation_form";
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute(MESSAGE, e.getMessage());

      return REDIRECT_OPERATIONS;
    }
  }

  @GetMapping("/operations/delete/{id}")
  public String deleteOperation(@PathVariable("id") Long id, Model model,
      RedirectAttributes redirectAttributes) {
    try {
      operationRepository.deleteById(id);

      redirectAttributes.addFlashAttribute(MESSAGE,
          "The Operation with id=" + id + " has been deleted successfully!");
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute(MESSAGE, e.getMessage());
    }

    return REDIRECT_OPERATIONS;
  }

  @PostMapping(value = "/operations")
  public String edit(Operation operation,
      Model model, RedirectAttributes redirectAttributes) {
    try {
      Operation operationDB = operationRepository.getReferenceById(operation.getId());
      operationDB.setStatus(Status.STATUS_COMPLITED);
      operationDB.setResult(operation.getResult());
      operationDB.setCreateDate(LocalDateTime.now());
      operationRepository.save(operationDB);

      String message = "The operation id=" + operation.getId() + " has been saved";

      redirectAttributes.addFlashAttribute(MESSAGE, message);
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute(MESSAGE, e.getMessage());
    }

    return REDIRECT_OPERATIONS;
  }

  @PatchMapping(value = "/operations/{id}")
  public String editStatus(Model model, @PathVariable("id") Long id) {
    Operation operationDB = operationRepository.getReferenceById(id);
    operationDB.setStatus(Status.STATUS_RUNNING);
    operationRepository.save(operationDB);
    model.addAttribute(OPERATION, operationDB);

    return "operation_form";
  }

  @GetMapping("/operations/new")
  public String newOperation(Model model) {

    model.addAttribute(OPERATION, new Operation());
    return "new_operation_form";
  }
  @PostMapping(value = "/operations/new")
  public String newOp(Operation operation,
      Model model, RedirectAttributes redirectAttributes) {
    try {
      operation.setStatus(Status.STATUS_RUNNABLE);
      operation.setCreateDate(LocalDateTime.now());
      operationRepository.save(operation);

      String message = "The operation domain=" + operation.getDomain() + " has been saved";

      redirectAttributes.addFlashAttribute(MESSAGE, message);
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute(MESSAGE, e.getMessage());
    }

    return REDIRECT_OPERATIONS;
  }
}
