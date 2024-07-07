package de.imedia24.backend.service.impl

import com.example.demo.service.IProductService
import de.imedia24.backend.mapper.ProductMapper
import de.imedia24.backend.repository.IProductRepository
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(productRepository: IProductRepository,
                         productMapper: ProductMapper
) : CrudProductServiceImpl(productRepository, productMapper), IProductService {
    override fun serviceX(): String {
        println("Some specific service for product management!")
        return "Hello Kotlin!!"
    }
}