package com.example.testproject.test.web;

import com.example.testproject.exceptions.CollectionSizeException;
import com.example.testproject.shared.BaseController;
import com.example.testproject.shared.BaseService;
import com.example.testproject.test.converter.TestConverter;
import com.example.testproject.test.converter.TestShowConverter;
import com.example.testproject.test.dto.*;
import com.example.testproject.test.model_repo.Test;
import com.example.testproject.user.model_repo.User;
import com.example.testproject.user.web.UserServiceImplementation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.DateTimeException;
import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "/api/tests")
@CrossOrigin
public class TestController extends BaseController<Test> {

    private final TestServiceImplementation testService;
    private final TestShowConverter testShowConverter;
    private final TestConverter testConverter;
    private final UserServiceImplementation userService;

    public TestController(BaseService<Test> service, TestServiceImplementation testService,
                          TestShowConverter testShowConverter, TestConverter testConverter,
                          UserServiceImplementation userService) {
        super(service);
        this.testService = testService;
        this.testShowConverter = testShowConverter;
        this.testConverter = testConverter;
        this.userService = userService;
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
    @PostMapping("")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> create(@RequestBody @Valid final TestDto dto) {
        if (dto.getNumberOfQuestions() > dto.getQuestionsId().size()) {
            System.out.println(dto.getNumberOfQuestions());
            System.out.println(dto.getQuestionsId().size());
            throw new CollectionSizeException("Ilość pytań nie może być mniejsza niż zbiór dostępnych pytań.");
        }
        if (dto.getStartDate().isAfter(dto.getEndDate()) || LocalDateTime.now().isAfter(dto.getStartDate())) {
            throw new DateTimeException("Data rozpoczęcia i zakończenia muszą być z przyszłości " +
                    "oraz data rozpoczęcia musi być wcześniej niż data zakończenia");
        }
        User user = this.userService.getAuthUser();
        Test test = this.testConverter.toEntity().apply(dto);
        test.setUser(user);
        test = this.testService.addQuestionsToTest(dto.getQuestionsId(), test);
        test = this.testService.addUsersToTest(dto.getUsersId(), test);

        Test savedTest = this.testService.save(test);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedTest.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    /* PATCH */
    @PatchMapping("/{id}/name")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Void> updateName(@PathVariable final Long id, @RequestBody @Valid final TestNameDto dto) {
        if (this.testService.updateName(id, dto.getName())) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/numberOfQuestions")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Void> updateNumberOfQuestions(@PathVariable final Long id, @RequestBody @Valid final TestNumberDto dto) {
        if (this.testService.updateNumber(id, dto.getNumberOfQuestions())) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/time")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Void> updateTime(@PathVariable final Long id, @RequestBody @Valid final TestTimeDto dto) {
        if (this.testService.updateTime(id, dto.getTime())) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/startDate")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Void> updateStartDate(@PathVariable final Long id, @RequestBody @Valid final TestDateDto dto) {
        if (this.testService.updateStartDate(id, dto.getDate())) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/endDate")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Void> updateEndDate(@PathVariable final Long id, @RequestBody @Valid final TestDateDto dto) {
        if (this.testService.updateEndDate(id, dto.getDate())) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /* PUT */

    /* DELETE */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (this.testService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
