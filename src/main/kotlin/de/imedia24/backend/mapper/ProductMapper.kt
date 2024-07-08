package de.imedia24.backend.mapper

import de.imedia24.backend.dto.ProductDto
import de.imedia24.backend.exception.ProductServiceException
import de.imedia24.backend.repository.entity.Product
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

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
        if (ratingListEntity != null) {
            productEntity.ratings = ratingListEntity
        }
        productEntity.title = d.title.toString()
        productEntity.subTitle = d.subTitle
        productEntity.price = d.price!!
        productEntity.description = d.description
        productEntity.images = images
        return productEntity
    }


    override fun patch(e: Product, patch: ProductDto): Product {
        e.id = patch.id
        if(patch.title?.isNotEmpty() == true) e.title = patch.title.toString()
        if(patch.description?.isNotEmpty() == true) e.description = patch.description
        if(patch.subTitle?.isNotEmpty() == true) e.subTitle = patch.subTitle
        if(patch.price != null) e.price = patch.price
        if(!patch.images.isNullOrEmpty()) e.images = patch.images.joinToString(";")


        patch.ratings?.forEach {
            rating ->
            run {
                if (rating.id != null) {
                    // update rating
                    val productRating = e.ratings.stream().filter { r -> r.id == rating.id }.findFirst()
                    ratingMapper.patch(productRating.getOrElse { throw  ProductServiceException("Unable to update Rating with id ${rating.id}", 500) }, rating)
                } else {
                    // add rating
                    e.ratings.add(ratingMapper.toEntity(rating, false))
                }
            }
        }
        return e;
    }
}