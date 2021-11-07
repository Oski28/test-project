package com.example.testproject.shared;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.function.Function;

@RequiredArgsConstructor
public abstract class BaseController<E extends BaseEntity> {

    private final BaseService<E> service;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, readOnly = true)
    public <T> ResponseEntity<Page<T>> getAll(final int page,
                                              final int size,
                                              final String column,
                                              final String direction,
                                              final Function<E, T> converter) {
        Sort.Direction sortDirection = direction.equals("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Page<E> entityPage = this.service.getAll(page, size, column, sortDirection);
        return ResponseEntity.ok(entityPage.map(converter));
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, readOnly = true)
    public <T> ResponseEntity<T> getOne(final Long id, Function<E, T> converter) {
        E entity = this.service.getById(id);
        if (entity != null)
            return ResponseEntity.ok(converter.apply(entity));
        else
            return ResponseEntity.notFound().build();
    }

    public <T> ResponseEntity<Void> create(final E entity) {
        E saved = this.service.save(entity);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    public <T> ResponseEntity<Void> update(final Long id, final E entity)  {
        return this.service.update(id, entity) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> delete(final Long id) {
        if (this.service.isExists(id)) {
            this.service.delete(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
