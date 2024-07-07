package de.imedia24.backend.repository

import de.imedia24.backend.repository.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IProductRepository : JpaRepository<Product, Long> {

}