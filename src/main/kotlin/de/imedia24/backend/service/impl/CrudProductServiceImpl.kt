package de.imedia24.backend.service.impl


import de.imedia24.backend.dto.ProductDto
import de.imedia24.backend.exception.ProductServiceException
import de.imedia24.backend.mapper.ProductMapper
import de.imedia24.backend.repository.IProductRepository
import de.imedia24.backend.repository.entity.Product
import de.imedia24.backend.service.ICRUDService
import org.springframework.http.HttpStatus
import java.util.logging.Logger
import kotlin.jvm.optionals.getOrElse

open class CrudProductServiceImpl(private val productRepository: IProductRepository, private val productMapper: ProductMapper) :
    ICRUDService<Long, ProductDto> {

    companion object {
        val log: Logger = Logger.getLogger(CrudProductServiceImpl::class.java.name)
    }

    override fun create(payload: ProductDto): Long {
        log.info("Create product with title '${payload.title}'")
        val product: Product?
        try {
            product =  productRepository.saveAndFlush(productMapper.toEntity(payload))
        } catch (ex: Exception) {
            throw ProductServiceException(
                "exception during inserting new product with id '${payload.id}'", HttpStatus.INTERNAL_SERVER_ERROR.value())
        }
       return product.id
    }

    override fun read(id: Long): ProductDto {
        log.info("Retrieving product with id '$id'")
        val product = productRepository.findById(id).getOrElse { throw ProductServiceException("No Product Exist in DB with id '${id}'", HttpStatus.INTERNAL_SERVER_ERROR.value()) }
        return productMapper.toDto(product)
    }

    override fun update(payload: ProductDto): ProductDto {
        log.info("updating product with id '${payload.id}'")
        val product: Product?
        try {
            product = productRepository.saveAndFlush(productMapper.toEntity(payload))
        } catch (ex: Exception) {
            throw ProductServiceException("Unable to Update product with id '${payload.id}'", HttpStatus.INTERNAL_SERVER_ERROR.value())
        }
        return productMapper.toDto(product)
    }

    override fun delete(id: Long): Long {
        log.info("deleting product with id '$id'")
        productRepository.deleteById(id)
        return id
    }
}