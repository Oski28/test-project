package com.example.testproject.test.web;

import com.example.testproject.shared.BaseService;
import com.example.testproject.test.model_repo.Test;
import com.example.testproject.test.model_repo.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImplementation implements BaseService<Test>, TestService {

    private final TestRepository testRepository;

    @Autowired
    public TestServiceImplementation(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Override
    public Page<Test> getAll(int page, int size, String column, Sort.Direction direction) {
        return null;
    }

    @Override
    public boolean update(Long id, Test entity) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Test save(Test entity) {
        return null;
    }

    @Override
    public Test getById(Long id) {
        if (isExists(id)) {
            return this.testRepository.getById(id);
        } else {
            return null;
        }
    }

    @Override
    public boolean isExists(Long id) {
        return this.testRepository.existsById(id);
    }
}
