package com.indiepost.repository;

import com.indiepost.dto.post.PostQuery;
import com.indiepost.dto.post.PostSummaryDto;
import com.indiepost.enums.Types.PostStatus;
import com.indiepost.model.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static com.indiepost.model.QPost.post;
import static com.indiepost.repository.utils.CriteriaUtils.addSearchConjunction;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

/**
 * Created by jake on 7/30/16.
 */
@Repository
@SuppressWarnings("unchecked")
public class PostRepositoryHibernate implements PostRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Post findById(Long id) {
        return getQueryFactory()
                .selectFrom(post)
                .join(post.category, QCategory.category)
                .leftJoin(post.titleImage, QImageSet.imageSet)
                .fetchJoin()
                .where(post.id.eq(id))
                .fetchOne();
    }

    @Override
    public Post findByLegacyId(Long id) {
        return getQueryFactory()
                .selectFrom(post)
                .leftJoin(post.titleImage, QImageSet.imageSet)
                .fetchJoin()
                .where(post.legacyPostId.eq(id))
                .fetchOne();
    }

    @Override
    public Long findIdByLegacyId(Long legacyId) {
        Tuple tuple = getQueryFactory()
                .select(post.id, post.legacyPostId)
                .from(post)
                .where(post.legacyPostId.eq(legacyId))
                .fetchOne();
        return tuple != null ? tuple.get(post.id) : null;
    }

    @Override
    public Long count() {
        return getQueryFactory()
                .selectFrom(post)
                .fetchCount();
    }

    @Override
    public Long count(PostQuery search) {
        BooleanBuilder builder = addSearchConjunction(search, new BooleanBuilder());
        return getQueryFactory()
                .selectFrom(post)
                .where(builder)
                .fetchCount();
    }

    @Override
    public List<PostSummaryDto> findByIds(List<Long> ids) {
        if (isEmpty(ids)) {
            return new ArrayList<>();
        }
        JPAQuery query = getQueryFactory().from(post);
        addProjections(query)
                .innerJoin(post.category, QCategory.category)
                .leftJoin(post.titleImage, QImageSet.imageSet)
                .where(post.id.in(ids))
                .distinct();
        List<Tuple> rows = query.fetch();
        List<Tuple> result = new ArrayList<>();
        for (Long id : ids) {
            for (Tuple row : rows) {
                Long postId = row.get(post.id);
                if (id.equals(postId)) {
                    result.add(row);
                    break;
                }
            }
        }
        return toDtoList(result);
    }

    @Override
    public List<PostSummaryDto> findByCategoryId(Long categoryId, Pageable pageable) {
        PostQuery query = new PostQuery();
        query.setCategoryId(categoryId);
        return this.search(query, pageable);
    }

    @SuppressWarnings("JpaQlInspection")
    @Override
    public List<PostSummaryDto> findByCategorySlug(String slug, Pageable pageable) {
        PostQuery query = new PostQuery();
        query.setCategorySlug(slug);
        return this.search(query, pageable);
    }

    @Override
    public List<PostSummaryDto> findByTagName(String tagName, Pageable pageable) {
        JPAQuery query = getQueryFactory().from(post);
        addProjections(query)
                .innerJoin(post.category, QCategory.category)
                .innerJoin(post.postTags, QPostTag.postTag)
                .innerJoin(QPostTag.postTag.tag, QTag.tag)
                .leftJoin(post.titleImage, QImageSet.imageSet)
                .where(QTag.tag.name.eq(tagName).and(post.status.eq(PostStatus.PUBLISH)))
                .orderBy(post.publishedAt.desc(), QPostTag.postTag.priority.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).distinct();
        List<Tuple> result = query.fetch();
        if (result.size() == 0) {
            return new ArrayList<>();
        }
        return toDtoList(result);
    }

    @Override
    public List<PostSummaryDto> findByStatus(PostStatus status, Pageable pageable) {
        PostQuery query = new PostQuery();
        query.setStatus(status);
        return this.search(query, pageable);
    }

    @Override
    public List<PostSummaryDto> findScheduledPosts() {
        JPAQuery query = getQueryFactory().from(post);
        addProjections(query)
                .innerJoin(post.category, QCategory.category)
                .leftJoin(post.titleImage, QImageSet.imageSet)
                .where(post.status.eq(PostStatus.FUTURE))
                .orderBy(post.publishedAt.asc())
                .distinct();
        List<Tuple> result = query.fetch();
        return toDtoList(result);
    }

    @Override
    public List<PostSummaryDto> search(PostQuery search, Pageable pageable) {
        JPAQuery query = getQueryFactory().from(post);
        BooleanBuilder builder = addSearchConjunction(search, new BooleanBuilder());
        addProjections(query)
                .innerJoin(post.category, QCategory.category)
                .leftJoin(post.titleImage, QImageSet.imageSet)
                .where(builder)
                .orderBy(post.publishedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).distinct();
        List<Tuple> result = query.fetch();
        return toDtoList(result);
    }

    @Override
    public PostStatus getStatusById(Long postId) {
        return getQueryFactory()
                .select(post.status)
                .from(post)
                .where(post.id.eq(postId))
                .fetchOne();
    }

    private JPAQuery addProjections(JPAQuery query) {
        return query.select(
                post.id, post.legacyPostId, post.categoryId, post.category.name, post.category.slug, post.splash, post.picked, post.featured,
                post.displayName, post.title, post.publishedAt, post.excerpt, post.titleImage,
                post.likesCount);
    }


    private List<PostSummaryDto> toDtoList(List<Tuple> result) {
        List<PostSummaryDto> dtoList = new ArrayList<>();
        for (Tuple row : result) {
            PostSummaryDto dto = new PostSummaryDto();
            dto.setId(row.get(post.id));
            dto.setLegacyPostId(row.get(post.legacyPostId));
            dto.setTitle(row.get(post.title));
            dto.setDisplayName(row.get(post.displayName));
            dto.setPublishedAt(row.get(post.publishedAt));
            dto.setExcerpt(row.get(post.excerpt));
            dto.setSplash(row.get(post.splash));
            dto.setFeatured(row.get(post.featured));
            dto.setPicked(row.get(post.picked));
            dto.setCategoryName(row.get(post.category.slug));
            dto.setCategoryId(row.get(post.categoryId));
            ImageSet titleImage = row.get(post.titleImage);
            if (titleImage != null) {
                dto.setTitleImage(titleImage);
            }
            dtoList.add(dto);
        }
        return dtoList;
    }

    private JPAQueryFactory getQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}