package de.imedia24.backend.mapper

import java.util.stream.Collectors

abstract  class ObjectMapper<E, D> {
    // map entity to dto and vice versa.

    abstract fun  toDto(e: E): D;

    abstract fun toEntity(d: D): E;

    abstract fun toEntity(d: D, setId: Boolean): E;

    abstract fun patch(e: E, patch: D): E;

    fun toDtoList(entityList: List<E>?): List<D> {
        if (entityList != null) {
            return entityList.stream().map { entity -> toDto(entity) }.toList()
        } else {
            return ArrayList()
        }
    }

    fun toEntityList(dtoList: List<D>?, setId: Boolean): MutableList<E>? {
        if (dtoList != null) {
            return dtoList.stream().map { dto -> toEntity(dto, setId) }.collect(Collectors.toList())
        } else {
            return ArrayList()
        }
    }

}