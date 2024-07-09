package de.imedia24.backend.service.impl

import de.imedia24.backend.dto.ProductDto
import de.imedia24.backend.dto.RatingDto
import de.imedia24.backend.exception.ProductServiceException
import de.imedia24.backend.mapper.ProductMapper
import de.imedia24.backend.repository.IProductRepository
import de.imedia24.backend.repository.entity.Product
import de.imedia24.backend.repository.entity.Rating
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import kotlin.test.assertFailsWith

@SpringBootTest
class CrudProductServiceImplTest {

    @Mock
    private lateinit var productMapper: ProductMapper

    @Mock
    private lateinit var productRepository: IProductRepository

    @InjectMocks
    private lateinit var service: CrudProductServiceImpl


    private val payload: Product = Product(id = 123L, title = "title", subTitle = "subtitle", price = 123.2, description = "description", ratings = ArrayList(), images = "")
    private val payloadDto: ProductDto = ProductDto(id = 123L, title = "title", subTitle = "subtitle", price = 123.2, description = "description", ratings = ArrayList(), images = ArrayList())

    private val ratings: MutableList<Rating> = ArrayList()

    private val ratingDtoList: List<RatingDto> = listOf(
        RatingDto(id = 1, title = "rating1", comment = "comment for rating1", stars = 5),
        RatingDto(id = 2, title = "rating2", comment = "comment for rating2", stars = 4),
        RatingDto(id = 3, title = "rating3", comment = "comment for rating3", stars = 2),
    )

    private val payload2: Product = Product(id = 111L, title = "title", subTitle = "subtitle", price = 123.2, description = "description", ratings = ratings, images = "uri1;uri2;uri3")
    private val payload2Dto: ProductDto = ProductDto(id = 111L, title = "title", subTitle = "subtitle", price = 123.2, description = "description", ratings = ratingDtoList, images = listOf("uri1", "uri2", "uri3"))

    @BeforeEach
    fun setUp() {

        ratings.add(Rating(id = 1, title = "rating1", comment = "comment for rating1", stars = 5))
        ratings.add(Rating(id = 2, title = "rating2", comment = "comment for rating2", stars = 4))
        ratings.add(Rating(id = 3, title = "rating3", comment = "comment for rating3", stars = 2))

        Mockito.`when`(productRepository.findById(123)).thenReturn(Optional.of(payload))
        Mockito.`when`(productRepository.findById(111)).thenReturn(Optional.of(payload2))
        Mockito.`when`(productMapper.toDto(payload)).thenReturn(payloadDto)
        Mockito.`when`(productMapper.toDto(payload2)).thenReturn(payload2Dto)
        Mockito.`when`(productMapper.toEntity(payload2Dto, true)).thenReturn(payload2)
        Mockito.`when`(productMapper.toEntity(payload2Dto)).thenReturn(payload2)
        Mockito.`when`(productRepository.saveAndFlush(payload2)).thenReturn(payload2)
        Mockito.`when`(productRepository.save(payload2)).thenReturn(payload2)

    }


    @Test
    fun create_withValidRatingAndImages() {
        val id = service.create(payload2Dto)
        assertThat(id).isNotNull
        assertThat(id).isEqualTo(payload2Dto.id)
    }

    @Test
    fun create_withExceptionThrow() {
        Mockito.`when`(productRepository.saveAndFlush(payload2)).thenThrow(ProductServiceException("exception thrown", 500))
        assertFailsWith<ProductServiceException> { service.create(payload2Dto) }
    }

    @Test
    fun read_withEmptyRatingAndIages() {
        val productDto = service.read(123)
        assertThat(productDto.title).isEqualTo(payload.title)
        assertThat(productDto.subTitle).isEqualTo(payload.subTitle)
        assertThat(productDto.price).isEqualTo(payload.price)
        assertThat(productDto.description).isEqualTo(payload.description)
        assertThat(productDto.ratings).isEmpty()
        assertThat(productDto.images).isEmpty()
    }

    @Test
    fun read_withAvailablesRatingAndImages() {
        val productDto = service.read(111)
        assertThat(productDto.title).isEqualTo(payload2.title)
        assertThat(productDto.subTitle).isEqualTo(payload2.subTitle)
        assertThat(productDto.price).isEqualTo(payload2.price)
        assertThat(productDto.description).isEqualTo(payload2.description)
        assertThat(productDto.ratings).hasSize(3)
        assertThat(productDto.images).hasSize(3)
    }

    @Test
    fun read_withExceptionThrown() {
        Mockito.`when`(productRepository.findById(123)).thenThrow(ProductServiceException("exception during read", 500))
        assertFailsWith<ProductServiceException> { service.read(123) }
    }

    @Test
    fun delete_validId() {
        val id = service.delete(123)
        assertThat(id).isNotNull
        assertThat(id).isEqualTo(123)
    }
}