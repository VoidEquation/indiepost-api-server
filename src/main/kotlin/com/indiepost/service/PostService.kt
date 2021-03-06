package com.indiepost.service

import com.indiepost.dto.PageWithProfile
import com.indiepost.dto.post.PostDto
import com.indiepost.dto.post.PostQuery
import com.indiepost.dto.post.PostSummaryDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.LocalDateTime

/**
 * Created by jake on 7/30/16.
 */
interface PostService {

    fun findOne(postId: Long): PostDto

    fun count(): Long

    fun count(postQuery: PostQuery): Long

    fun findPublicPosts(pageable: Pageable): Page<PostSummaryDto>

    fun findByTagName(tagName: String, pageable: Pageable): Page<PostSummaryDto>

    fun query(postQuery: PostQuery, pageable: Pageable): Page<PostSummaryDto>

    fun fullTextSearch(text: String, pageable: Pageable): Page<PostSummaryDto>

    fun findRelatedPostsById(id: Long, pageable: Pageable): Page<PostSummaryDto>

    fun recommendations(pageable: Pageable): Page<PostSummaryDto>

    fun findReadingHistory(pageable: Pageable): Page<PostSummaryDto>

    fun findBookmarks(pageable: Pageable): Page<PostSummaryDto>

    fun findByIds(ids: List<Long>): List<PostSummaryDto>

    fun findTopRatedPosts(since: LocalDateTime, until: LocalDateTime, limit: Int): List<PostSummaryDto>

    fun findScheduledPosts(): List<PostSummaryDto>

    fun findPickedPosts(): List<PostSummaryDto>

    fun findSplashPost(): PostSummaryDto?

    fun findFeaturePost(): PostSummaryDto?

    fun defaultRecommendations(pageable: Pageable): Page<PostSummaryDto>

    fun findByProfileSlug(slug: String, pageable: Pageable): PageWithProfile<PostSummaryDto>
}