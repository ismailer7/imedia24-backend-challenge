package de.imedia24.backend.dto

data class ProductDto(val id: Long?, val title: String?, val subTitle: String?, val price: Double?, val description: String?, val ratings: List<RatingDto>?, val images: List<String>?)
