package de.imedia24.backend.service

interface ICRUDService<L, D> {
    fun create(payload: D): L
    fun read(id: L): D
    fun update(payload: D): D
    fun delete(id: L): L
}