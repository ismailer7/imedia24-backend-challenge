package de.imedia24.backend.exception

class ProductServiceException(override var message: String, var code: Int) : RuntimeException() {

}