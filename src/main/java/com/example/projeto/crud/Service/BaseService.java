package com.example.projeto.crud.Service;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.example.projeto.crud.Entity.BaseEntity;
import com.example.projeto.crud.Entity.Reserva;
import com.example.projeto.crud.Repository.BaseRepository;

import jakarta.transaction.Transactional;

public abstract class BaseService<E extends BaseEntity, D> {

    private final Class<E> entityClass;
    private final Class<D> dtoClass;
    private final BaseRepository<E, Long> repository;
    

    @SuppressWarnings("unchecked")
    protected BaseService(BaseRepository<E, Long> repository) {
        this.repository = repository;

        ParameterizedType baseSuperClass = (ParameterizedType) getClass().getGenericSuperclass();

        this.dtoClass = (Class<D>) baseSuperClass.getActualTypeArguments()[1];
        this.entityClass = (Class<E>) baseSuperClass.getActualTypeArguments()[0];
    }


    public D toDto(E e) {
            try {
                D dto = dtoClass.getDeclaredConstructor().newInstance();
                BeanUtils.copyProperties(e, dto);

            return dto;
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao converter entidade para DTO", ex);
        }
    }

    public E toEntity(D dto) {
        try {
            E e = entityClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(dto, e);

            return e;
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao converter DTO para entidade", ex);
        }                                               
    }

    @Transactional
    public D create(D dto) {
        E e = toEntity(dto);
        e = repository.save(e);
        return toDto(e);
    }       

    public D read (Long id){
        E e = repository.findById(id).orElseThrow();
        return toDto(e);
    }

    @Transactional
    public D update(Long id, D dto) {
        E e = toEntity(dto);
        e.setId(id);

        return toDto(repository.save(e));
    }

    @Transactional
    public void delete(Long id) {
        repository.softDeleteById(id);
    }

    public List<D> list() {
        List<E> es = repository.findAll();

        List<D> dtos = new ArrayList<>();

        for (E e : es) {
            dtos.add(toDto(e));
        }
        return dtos;
    }
}
