package com.indiepost.controller.api;

import com.indiepost.dto.FullTextSearchQuery;
import com.indiepost.dto.PostImageSetListDto;
import com.indiepost.dto.post.PostDto;
import com.indiepost.dto.post.PostQuery;
import com.indiepost.dto.post.PostSummaryDto;
import com.indiepost.dto.post.RelatedPostsRequestDto;
import com.indiepost.service.ImageService;
import com.indiepost.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by jake on 7/31/16.
 */
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    private final ImageService imageService;

    @Autowired
    public PostController(PostService postService, ImageService imageService) {
        this.postService = postService;
        this.imageService = imageService;
    }

    @GetMapping("/{id}")
    public PostDto getPost(@PathVariable Long id) {
        return postService.findOne(id);
    }

    @GetMapping
    public List<PostSummaryDto> getPosts(Pageable pageable) {
        return postService.find(pageable);
    }

    @PostMapping
    public List<PostSummaryDto> getPosts(@RequestBody PostQuery query) {
        return postService.search(query, query.getPage(), query.getMaxResults());
    }

    @GetMapping("/category/{slug}")
    public List<PostSummaryDto> getPostsByCategoryName(@PathVariable String slug, Pageable pageable) {
        return postService.findByCategorySlug(slug, pageable);
    }

    @GetMapping("/tag/{tagName}")
    public List<PostSummaryDto> getPostsByTagName(@PathVariable String tagName, Pageable pageable) {
        return postService.findByTagName(tagName, pageable);
    }

    @GetMapping("/contributor/{fullName}")
    public List<PostSummaryDto> getPostsByContributorName(@PathVariable String fullName, Pageable pageable) {
        return postService.findByContributorFullName(fullName, pageable);
    }

    @PostMapping("/search")
    public List<PostSummaryDto> fullTextSearch(@RequestBody FullTextSearchQuery query) {
        return postService.fullTextSearch(query);
    }

    @PostMapping("/related")
    public List<PostSummaryDto> getPostByIds(@RequestBody RelatedPostsRequestDto dto) {
        return postService.findByIds(dto.getPostIds());
    }

    @GetMapping(value = "/images/{id}")
    public PostImageSetListDto getImagesOnPost(@PathVariable Long id) {
        return imageService.findImagesOnPost(id);
    }

    @GetMapping(value = "/future")
    public List<PostSummaryDto> getScheduledPosts() {
        return postService.findScheduledPosts();
    }
}
