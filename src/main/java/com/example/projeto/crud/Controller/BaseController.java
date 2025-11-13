package com.example.projeto.crud.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PatchExchange;

import com.example.projeto.crud.Service.BaseService;

import jakarta.validation.Valid;

public abstract class BaseController<D> {

    protected final BaseService<?, D> service;

    protected BaseController(BaseService<?, D> service) {
        this.service = service;
    } 

    @PostMapping
    public D create(@RequestBody @Valid D dto) {
        return service.create(dto);
    }

    @GetMapping("/{id}")
    public D read(@PathVariable Long id) {
        return service.read(id);
    }

    @PutMapping ("/{id}")
    public D update(@PathVariable Long id, @RequestBody @Valid D dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping
    public List<D> list() {
        return service.list();
    }

}
