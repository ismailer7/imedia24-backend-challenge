package de.imedia24.backend.mapper

import de.imedia24.backend.dto.ProductDto
import de.imedia24.backend.repository.entity.Product
import org.springframework.stereotype.Service

@Service
class ProductMapper(val ratingMapper: RatingMapper) : ObjectMapper<Product, ProductDto>() {

    override fun toDto(e: Product): ProductDto {
        val ratingListDto = ratingMapper.toDtoList(e.ratings)
        val images = e.images?.split(";")
        return ProductDto(e.id, e.title, e.subTitle, e.price, e.description, ratingListDto, images)
    }

    override fun toEntity(d: ProductDto): Product {
        val ratingListEntity = ratingMapper.toEntityList(d.ratings)
        val images: String? = d.images?.joinToString(";")
        val productEntity = Product()
        productEntity.title = d.title
        productEntity.subTitle = d.subTitle
        productEntity.price = d.price
        productEntity.description = d.description
        productEntity.ratings = ratingListEntity
        productEntity.images = images

        return productEntity
    }

}