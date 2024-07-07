package de.imedia24.backend.repository.entity

import jakarta.persistence.*

@Entity
@Table(name = "product")
data class Product (

    @Id
    @Column(name = "PRODUCT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1L,

    @Column(name = "title")
    var title: String = "",

    @Column(name = "subTitle")
    var subTitle: String? = "",

    @Column(name = "price")
    var price: Double = 0.0,

    @Column(name = "description")
    var description: String? = "",

    @OneToMany
    var ratings: List<Rating> = listOf(),

    @Column(name = "images")
    var images: String? = ""

)
