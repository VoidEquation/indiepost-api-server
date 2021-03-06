package com.indiepost.model.analytics

import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

/**
 * Created by jake on 17. 4. 13.
 */
@Entity
@Table(name = "Stats", indexes = [Index(columnList = "timestamp", name = "s_timestamp_idx")])
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "class", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Stat")
class Stat(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null,

        @NotNull
        @Size(max = 200)
        var path: String? = null,

        @NotNull
        var timestamp: LocalDateTime? = null,

        @NotNull
        @Column(name = "visitorId", updatable = false, insertable = false, nullable = false)
        var visitorId: Long? = null,

        @ManyToOne(optional = false)
        @JoinColumn(name = "visitorId", nullable = false)
        var visitor: Visitor? = null
)
