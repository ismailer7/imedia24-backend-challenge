package de.imedia24.backend.mapper

import de.imedia24.backend.dto.RatingDto
import de.imedia24.backend.repository.entity.Rating
import org.springframework.stereotype.Service

@Service
class RatingMapper : ObjectMapper<Rating, RatingDto>() {

    override fun toDto(e: Rating): RatingDto {
        return RatingDto(e.id, e.title, e.comment, e.stars)
    }

    override fun toEntity(d: RatingDto): Rating {
        return Rating(d.id, d.title, d.comment, d.stars)
    }

}