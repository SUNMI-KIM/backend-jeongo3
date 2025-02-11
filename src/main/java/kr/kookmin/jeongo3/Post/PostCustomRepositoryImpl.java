package kr.kookmin.jeongo3.Post;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.kookmin.jeongo3.Post.Dto.PostMapping;
import kr.kookmin.jeongo3.Post.Dto.ResponseAllPostDto;
import kr.kookmin.jeongo3.Post.Dto.ResponseHotPostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import com.querydsl.core.types.Projections;


import java.util.List;
import java.util.Optional;

import static kr.kookmin.jeongo3.Post.QPost.post;
import static kr.kookmin.jeongo3.PostLike.QPostLike.postLike;

@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<ResponseHotPostDto> findHotPost(PostType postType) {
         ResponseHotPostDto postDto = jpaQueryFactory.select(Projections.constructor(ResponseHotPostDto.class,
                        post.id,
                        post.title,
                        post.content))
                .from(post)
                .join(postLike)
                .on(post.id.eq(postLike.post.id))
                .where(post.postType.eq(postType))
                .groupBy(post.id)
                .having(postLike.id.count().gt(3))
                .orderBy(post.time.desc())
                .fetchFirst();
        return Optional.ofNullable(postDto);
    }

    @Override
    public List<ResponseAllPostDto> findAllPost(PostType postType, Pageable pageable) {
        List<ResponseAllPostDto> postDtoList = jpaQueryFactory.select(Projections.constructor(ResponseAllPostDto.class,
                        post.id,
                        post.title,
                        post.content))
                .from(post)
                .where(post.postType.eq(postType))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();
        return postDtoList;
    }

    @Override
    public long updateViews(String id) {
        return jpaQueryFactory.update(post)
                .set(post.views, post.views.add(1))
                .where(post.id.eq(id))
                .execute();
    }
}
