package de.imedia24.backend.service.impl

import de.imedia24.backend.mapper.ProductMapper
import de.imedia24.backend.repository.IProductRepository
import de.imedia24.backend.service.IProductService
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(productRepository: IProductRepository, productMapper: ProductMapper) :
    CrudProductServiceImpl(productRepository, productMapper), IProductService {

        // other product services.

}