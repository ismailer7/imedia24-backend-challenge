package de.imedia24.backend.controller

import de.imedia24.backend.Imedia24BackendChallengeConstants
import de.imedia24.backend.dto.ProductDto
import de.imedia24.backend.dto.Response
import de.imedia24.backend.exception.ProductServiceException
import de.imedia24.backend.service.impl.ProductServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(Imedia24BackendChallengeConstants.API_VERSION)
class ProductController(val productService: ProductServiceImpl) {


    @GetMapping(value = ["/imedia", "/imedia24", "/hello"])
    fun helloMedia24(): String {
        return "Hello iMedia24!"
    }

    @PostMapping("/product/add")
    fun create(@RequestBody payload: ProductDto) : ResponseEntity<Long> {
        return ResponseEntity.ok(productService.create(payload))
    }

    @GetMapping("/product/{id}")
    fun read(@PathVariable id: Long) : ResponseEntity<ProductDto> {
        return ResponseEntity.ok(productService.read(id))
    }

    @PutMapping("/product/update")
    fun update(@RequestBody payload: ProductDto) : ResponseEntity<ProductDto> {
        return ResponseEntity.ok(productService.update(payload))
    }

    @DeleteMapping("/product/delete/{id}")
    fun delete(@PathVariable id: Long) : ResponseEntity<Long> {
        return ResponseEntity.ok(productService.delete(id))
    }

    @ExceptionHandler(value = [ProductServiceException::class])
    fun handleProductServiceException(ex: ProductServiceException) : ResponseEntity<Response> {
        val response = Response(ex.message)
        return ResponseEntity(response, HttpStatus.valueOf(ex.code));
    }

}