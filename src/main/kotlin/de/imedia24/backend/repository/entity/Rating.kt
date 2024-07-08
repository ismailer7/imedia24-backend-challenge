package de.imedia24.backend.repository.entity

import jakarta.persistence.*

@Entity
@Table(name = "rating")
class Rating {

    @Id
    @Column(name = "rating_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null;

    @Column(name = "title")
    var title: String = "";

    @Column(name = "comment")
    var comment: String = "";

    @Column(name = "stars")
    var stars: Int = 0;

}
