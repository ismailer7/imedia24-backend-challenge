package de.imedia24.backend.controller

import de.imedia24.backend.dto.ProductDto
import de.imedia24.backend.exception.ProductServiceException
import de.imedia24.backend.service.impl.ProductServiceImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(controllers = [ProductController::class])
class ProductControllerTest @Autowired constructor(private val mockMvc: MockMvc) {

    private val validEndpoint: String = "/api/v1/hello"
    private val invalidEndpoint: String= "/invalid"

    private val creatEndpoint: String = "/api/v1/product/add"
    private val readEndpoint: String = "/api/v1/product/123"
    private val updateEndpoint: String = "/api/v1/product/update"
    private val deleteEndpoint: String = "/api/v1/product/delete/123"

    private val payload: ProductDto = ProductDto(id = 123L, title = "title", subTitle = "subtitle", price = 123.2, description = "description", ratings = listOf(), images = listOf())

    private val validBody: String = "{\n" +
            "   \"id\": 123,\n" +
            "   \"title\":\"title\",\n" +
            "   \"subTitle\":\"subtitle\",\n" +
            "   \"price\":123.2,\n" +
            "   \"description\":\"description\",\n" +
            "   \"ratings\":[],\n" +
            "   \"images\": []\n" +
            "}"

    private val invalidBody: String = "{\n";

    private val responseErrorMessageJson = "{\n" +
            "\t\"message\": \"this is an exception thrown.\"\n" +
            "}"

    @MockBean
    private lateinit var productServiceImpl: ProductServiceImpl


    @BeforeEach
    fun setUp() {
        `when`(productServiceImpl.create(payload)).thenReturn(payload.id)
        `when`(productServiceImpl.read(123)).thenReturn(payload)
        `when`(productServiceImpl.update(payload)).thenReturn(payload)
        `when`(productServiceImpl.delete(123)).thenReturn(123)
    }

    @Test
    fun helloMedia24() {
        mockMvc.perform(
            MockMvcRequestBuilders
            .get(validEndpoint))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string("Hello iMedia24!"))
    }

    @Test
    fun helloKotlin_invalidEndpoint() {
        mockMvc.perform(
            MockMvcRequestBuilders
            .get(invalidEndpoint))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun create_validContentBody() {
        mockMvc.perform(MockMvcRequestBuilders.post(creatEndpoint).contentType(MediaType.APPLICATION_JSON_VALUE).content(validBody))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string("123"))
    }

    @Test
    fun create_invalidContentBody() {
        mockMvc.perform(MockMvcRequestBuilders.post(creatEndpoint).contentType(MediaType.APPLICATION_JSON_VALUE).content(invalidBody))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun read_validEndpoint() {
        mockMvc.perform(MockMvcRequestBuilders.get(readEndpoint).contentType(MediaType.APPLICATION_JSON_VALUE))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(validBody))
    }

    @Test
    fun update_validEndpointWithValidMethodAndBody() {
        mockMvc.perform(MockMvcRequestBuilders.put(updateEndpoint).contentType(MediaType.APPLICATION_JSON_VALUE).content(validBody))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(validBody))
    }

    @Test
    fun update_validEndpointWithValidMethodAndInvalidBody() {
        mockMvc.perform(MockMvcRequestBuilders.put(updateEndpoint).contentType(MediaType.APPLICATION_JSON_VALUE).content("invalid Body"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun update_validEndpointWithInvalidMethodAndValidBody() {
        mockMvc.perform(MockMvcRequestBuilders.post(updateEndpoint).contentType(MediaType.APPLICATION_JSON_VALUE).content(validBody))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed)
    }

    @Test
    fun delete_validEndpointWithValidMethod() {
        mockMvc.perform(MockMvcRequestBuilders.delete(deleteEndpoint).contentType(MediaType.APPLICATION_JSON_VALUE))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string("123"))
    }

    @Test
    fun delete_validEndpointWithInvalidMethod() {
        mockMvc.perform(MockMvcRequestBuilders.get(deleteEndpoint).contentType(MediaType.APPLICATION_JSON_VALUE))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed)
    }

    @Test
    fun exceptionHandlerTest() {
        `when`(productServiceImpl.read(123)).thenThrow(ProductServiceException("this is an exception thrown.", 500))
        mockMvc.perform(MockMvcRequestBuilders.get(readEndpoint))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isInternalServerError)
            .andExpect(MockMvcResultMatchers.content().json(responseErrorMessageJson))
    }

}