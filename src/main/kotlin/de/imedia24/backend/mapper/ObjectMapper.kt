package de.imedia24.backend.mapper

abstract  class ObjectMapper<E, D> {
    // map entity to dto and vice versa.

    abstract fun  toDto(e: E): D;

    abstract fun toEntity(d: D): E;

    fun toDtoList(entityList: List<E>): List<D> {
        return entityList.stream().map { entity -> toDto(entity) }.toList()
    }

    fun toEntityList(dtoList: List<D>): List<E> {
        return dtoList.stream().map { dto -> toEntity(dto) }.toList()
    }

}