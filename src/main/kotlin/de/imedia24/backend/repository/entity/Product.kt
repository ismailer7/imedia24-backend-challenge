package de.imedia24.backend.repository.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "product")
data class Product (

    @Id
    @Column(name = "PRODUCT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "title")
    var title: String = "",

    @Column(name = "subTitle")
    var subTitle: String? = "",

    @Column(name = "price")
    var price: Double = 0.0,

    @Column(name = "description")
    var description: String? = "",

    @OneToMany(cascade = [CascadeType.ALL])
    var ratings: MutableList<Rating> = ArrayList(),

    @Column(name = "images")
    var images: String? = ""

)

