package de.imedia24.backend.mapper

import de.imedia24.backend.dto.ProductDto
import de.imedia24.backend.repository.entity.Product
import org.springframework.stereotype.Service

@Service
class ProductMapper(val ratingMapper: RatingMapper) : ObjectMapper<Product, ProductDto>() {

    override fun toDto(e: Product): ProductDto {
        val ratingListDto = ratingMapper.toDtoList(e.ratings)
        val imageList: List<String>
        if(e.images == null || e.images!!.isEmpty()) {
            imageList = listOf()
        } else {
            imageList = e.images!!.split(";")
        }
        return ProductDto(e.id!!, e.title, e.subTitle, e.price, e.description, ratingListDto, imageList)
    }

    override fun toEntity(d: ProductDto): Product {
        val productEntity = toEntity(d, false)
        return productEntity
    }

    override fun toEntity(d: ProductDto, setId: Boolean): Product {
        val productEntity = Product()
        if (setId) productEntity.id = d.id
        val ratingListEntity = ratingMapper.toEntityList(d.ratings, setId)
        val images: String? = d.images?.joinToString(";")
        productEntity.ratings = ratingListEntity
        productEntity.title = d.title
        productEntity.subTitle = d.subTitle
        productEntity.price = d.price
        productEntity.description = d.description
        productEntity.images = images
        return productEntity
    }
}