package de.imedia24.backend.mapper

import de.imedia24.backend.dto.RatingDto
import de.imedia24.backend.repository.entity.Rating
import de.imedia24.backend.service.impl.ProductServiceImpl
import org.springframework.stereotype.Service

@Service
class RatingMapper : ObjectMapper<Rating, RatingDto>() {

    override fun toDto(e: Rating): RatingDto {
        return RatingDto(e.id!!, e.title, e.comment, e.stars)
    }

    override fun toEntity(d: RatingDto): Rating {
        return toEntity(d, false)
    }

    override fun toEntity(d: RatingDto, setId: Boolean): Rating {
        val rating = Rating()
        if(setId) rating.id = d.id
        rating.title = d.title
        rating.comment = d.comment
        rating.stars = d.stars
        return rating
    }

}