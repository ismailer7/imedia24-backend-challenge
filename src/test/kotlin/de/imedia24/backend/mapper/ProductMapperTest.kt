package de.imedia24.backend.mapper

import de.imedia24.backend.dto.ProductDto
import de.imedia24.backend.dto.RatingDto
import de.imedia24.backend.repository.entity.Product
import de.imedia24.backend.repository.entity.Rating
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.collections.ArrayList

class ProductMapperTest {


    private val ratingMap = RatingMapper()

    private val productMapper = ProductMapper(ratingMap)

    private val ratings: MutableList<Rating> = ArrayList()
    private val product: Product = Product(id = 111L, title = "title", subTitle = "subtitle", price = 123.2, description = "description", ratings = ArrayList(), images = "")

    private val product2: Product = Product(id = 111L, title = "title", subTitle = "subtitle", price = 123.2, description = "description", ratings = ratings, images = "uri1;uri2;uri3")


    private val productDto: ProductDto = ProductDto(id = 123L, title = "title", subTitle = "subtitle", price = 123.2, description = "description", ratings = listOf(), images = listOf())


    // ----

    private val ratingDtoList: List<RatingDto> = listOf(
        RatingDto(id = 1, title = "rating1", comment = "comment for rating1", stars = 5),
        RatingDto(id = 2, title = "rating2", comment = "comment for rating2", stars = 4),
        RatingDto(id = 3, title = "rating3", comment = "comment for rating3", stars = 2),
    )

    @BeforeEach
    fun setUp() {
        ratings.add(Rating(id = 1, title = "rating1", comment = "comment for rating1", stars = 5))
        ratings.add(Rating(id = 2, title = "rating2", comment = "comment for rating2", stars = 4))
        ratings.add(Rating(id = 3, title = "rating3", comment = "comment for rating3", stars = 2))
    }

    private val payload2Dto: ProductDto = ProductDto(id = 111L, title = "title", subTitle = "subtitle", price = 123.2, description = "description", ratings = ratingDtoList, images = listOf("uri1", "uri2", "uri3"))

    @Test
    fun toDto_withEmptyRating() {
        val productDto = productMapper.toDto(product)
        assertThat(productDto).isNotNull
        assertThat(productDto.id).isEqualTo(product.id)
        assertThat(productDto.ratings).isEmpty()
        assertThat(productDto.images).isEmpty()
    }

    @Test
    fun toDto_withNotEmptyRatingAndImages() {
        val productDto = productMapper.toDto(product2)
        assertThat(productDto).isNotNull
        assertThat(productDto.id).isEqualTo(product2.id)
        assertThat(productDto.ratings).isNotEmpty().hasSize(3)
    }

    @Test
    fun toEntity_withEmtyRatingAndImages() {
        val product = productMapper.toEntity(productDto)
        assertThat(product).isNotNull
        assertThat(product.title).isEqualTo(productDto.title)
        assertThat(product.ratings).isEmpty()
        assertThat(product.images).isEmpty()
    }

    @Test
    fun toEntity_withRatingAndImages() {
        val product = productMapper.toEntity(payload2Dto)
        assertThat(product).isNotNull
        assertThat(product.title).isEqualTo(payload2Dto.title)
        assertThat(product.ratings).isNotEmpty().hasSize(3)
        assertThat(product.images).isEqualTo("uri1;uri2;uri3")
    }
}