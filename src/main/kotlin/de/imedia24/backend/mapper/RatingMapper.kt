package de.imedia24.backend.mapper

import de.imedia24.backend.dto.RatingDto
import de.imedia24.backend.repository.entity.Rating
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
        rating.title = d.title.toString()
        rating.comment = d.comment.toString()
        rating.stars = d.stars!!
        return rating
    }

    override fun patch(e: Rating, patch: RatingDto): Rating {
        e.id = patch.id
        if(patch.title?.isNotEmpty() == true) e.title = patch.title.toString()
        if(patch.comment?.isNotEmpty() == true) e.comment = patch.comment.toString()
        if(patch.stars != null ) e.stars = patch.stars
        return e
    }

}