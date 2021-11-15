package com.example.testproject.test.web;

import com.example.testproject.shared.BaseController;
import com.example.testproject.shared.BaseService;
import com.example.testproject.test.converter.TestShowConverter;
import com.example.testproject.test.dto.TestShowDto;
import com.example.testproject.test.model_repo.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/tests")
@CrossOrigin
public class TestController extends BaseController<Test> {

    private final TestServiceImplementation testService;
    private final TestShowConverter testShowConverter;

    public TestController(BaseService<Test> service, TestServiceImplementation testService,
                          TestShowConverter testShowConverter) {
        super(service);
        this.testService = testService;
        this.testShowConverter = testShowConverter;
    }

    /* GET */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<TestShowDto> getOne(@PathVariable final Long id) {
        return super.getOne(id, this.testShowConverter.toDto());
    }

    @GetMapping("/available")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<Page<TestShowDto>> getAllAvailableTestsForAuthUser(@RequestParam(defaultValue = "0") final int page,
                                                                      @RequestParam(defaultValue = "20") final int size,
                                                                      @RequestParam(defaultValue = "id") final String column,
                                                                      @RequestParam(defaultValue = "ASC") final String direction) {
        Sort.Direction sortDir = direction.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(this.testService.getAllAvailableTestsForAuthUser(page, size, column, sortDir)
                .map(this.testShowConverter.toDto()));
    }

    @GetMapping("/active")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<Page<TestShowDto>> getActiveTestsForAuthUser(@RequestParam(defaultValue = "0") final int page,
                                                                @RequestParam(defaultValue = "20") final int size,
                                                                @RequestParam(defaultValue = "id") final String column,
                                                                @RequestParam(defaultValue = "ASC") final String direction) {
        Sort.Direction sortDir = direction.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(this.testService.getAllActiveTestsForAuthUser(page, size, column, sortDir)
                .map(this.testShowConverter.toDto()));
    }

    @GetMapping("/nonactive")
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<Page<TestShowDto>> getNonactiveTestsForAuthUser(@RequestParam(defaultValue = "0") final int page,
                                                                   @RequestParam(defaultValue = "20") final int size,
                                                                   @RequestParam(defaultValue = "id") final String column,
                                                                   @RequestParam(defaultValue = "ASC") final String direction) {
        Sort.Direction sortDir = direction.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(this.testService.getAllNonactiveTestsForAuthUser(page, size, column, sortDir)
                .map(this.testShowConverter.toDto()));
    }

    @GetMapping("/created")
    @PreAuthorize("hasRole('TEACHER')")
    ResponseEntity<Page<TestShowDto>> getAllCreatedTestsForAuthUser(@RequestParam(defaultValue = "0") final int page,
                                                                    @RequestParam(defaultValue = "20") final int size,
                                                                    @RequestParam(defaultValue = "id") final String column,
                                                                    @RequestParam(defaultValue = "ASC") final String direction) {
        Sort.Direction sortDir = direction.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(this.testService.getAllCreatedTestsForAuthUser(page, size, column, sortDir)
                .map(this.testShowConverter.toDto()));
    }

    @GetMapping("/createdActive")
    @PreAuthorize("hasRole('TEACHER')")
    ResponseEntity<Page<TestShowDto>> getActiveCreatedTestsForAuthUser(@RequestParam(defaultValue = "0") final int page,
                                                                       @RequestParam(defaultValue = "20") final int size,
                                                                       @RequestParam(defaultValue = "id") final String column,
                                                                       @RequestParam(defaultValue = "ASC") final String direction) {
        Sort.Direction sortDir = direction.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(this.testService.getActiveCreatedTestsForAuthUser(page, size, column, sortDir)
                .map(this.testShowConverter.toDto()));
    }

    @GetMapping("/createdNonActive")
    @PreAuthorize("hasRole('TEACHER')")
    ResponseEntity<Page<TestShowDto>> getNonactiveCreatedTestsForAuthUser(@RequestParam(defaultValue = "0") final int page,
                                                                          @RequestParam(defaultValue = "20") final int size,
                                                                          @RequestParam(defaultValue = "id") final String column,
                                                                          @RequestParam(defaultValue = "ASC") final String direction) {
        Sort.Direction sortDir = direction.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return ResponseEntity.ok(this.testService.getNonactiveCreatedTestsForAuthUser(page, size, column, sortDir)
                .map(this.testShowConverter.toDto()));
    }

    /* POST */

    /* PATCH */

    /* PUT */

    /* DELETE */
}
