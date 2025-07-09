package win.doyto.ormcamparison.jpa;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;
import win.doyto.query.core.PageQuery;

@AllArgsConstructor
public abstract class AbstractJpaController<E, ID, Q extends PageQuery> {
    protected JpaRepositoryImplementation<E, ID> repository;

    @GetMapping("/")
    public PagedModel<E> query(Q query) {
        Pageable pageable = PageRequest.of(query.getPageNumber(), query.getPageSize());
        return new PagedModel<>(repository.findAll(toSpecification(query), pageable));
    }

    @DeleteMapping("/")
    public long delete(Q query) {
        return repository.delete(toSpecification(query));
    }

    protected abstract Specification<E> toSpecification(Q query);

    @PostMapping("/")
    public void create(@RequestBody E e) {
        this.repository.save(e);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody E e) {
        this.repository.save(e);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable ID id) {
        this.repository.deleteById(id);
    }

    @GetMapping("/{id}")
    public E get(@PathVariable ID id) {
        return this.repository.getReferenceById(id);
    }

}
