package com.indiepost.service;

import com.indiepost.dto.FullTextSearchQuery;
import com.indiepost.dto.post.PostDto;
import com.indiepost.dto.post.PostQuery;
import com.indiepost.dto.post.PostSummaryDto;
import com.indiepost.dto.post.RelatedPostResponseDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by jake on 7/30/16.
 */
public interface PostService {

    PostDto findOne(Long id);

    PostDto findOneByLegacyId(Long id);

    Long count();

    Long count(PostQuery query);

    List<PostSummaryDto> findByIds(List<Long> ids);

    List<PostSummaryDto> find(int page, int maxResults, boolean isDesc);

    List<PostSummaryDto> findByCategoryId(Long categoryId, int page, int maxResults, boolean isDesc);

    List<PostSummaryDto> findByTagName(String tagName, int page, int maxResults, boolean isDesc);

    List<RelatedPostResponseDto> findRelatedPosts(List<Long> ids, boolean isLegacy, boolean isMobile);

    List<PostSummaryDto> findTopRatedPosts(LocalDateTime since, LocalDateTime until, Long limit);

    List<PostSummaryDto> findScheduledPosts();

    List<PostSummaryDto> search(PostQuery query, int page, int maxResults, boolean isDesc);

    List<PostSummaryDto> fullTextSearch(FullTextSearchQuery query);

    PostSummary findSplashPost();

    PostSummaryDto findFeaturePost();

    List<PostSummaryDto> findPickedPosts();
}