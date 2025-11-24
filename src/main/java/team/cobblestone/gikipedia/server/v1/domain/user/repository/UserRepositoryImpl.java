package team.cobblestone.gikipedia.server.v1.domain.user.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import team.cobblestone.gikipedia.server.v1.domain.document.entity.QDocumentVersion;
import team.cobblestone.gikipedia.server.v1.domain.user.entity.QUser;
import team.cobblestone.gikipedia.server.v1.domain.user.entity.User;
import team.cobblestone.gikipedia.server.v1.domain.user.entity.UserRole;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<User> searchUsers(String keyword, UserRole role, Pageable pageable) {
        QUser user = QUser.user;

        BooleanExpression predicate = user.isNotNull();

        if (keyword != null && !keyword.isEmpty()) {
            predicate = predicate
                    .and(user.username.containsIgnoreCase(keyword).or(user.email.containsIgnoreCase(keyword)));
        }

        if (role != null) {
            predicate = predicate.and(user.role.eq(role));
        }

        List<User> content = queryFactory.selectFrom(user).where(predicate).offset(pageable.getOffset())
                .limit(pageable.getPageSize()).orderBy(user.createdAt.desc()).fetch();

        Long total = queryFactory.select(user.count()).from(user).where(predicate).fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0L);
    }

    @Override
    public List<User> findTopContributors(int limit) {
        QUser user = QUser.user;
        QDocumentVersion version = QDocumentVersion.documentVersion;

        return queryFactory.select(user).from(version).join(version.author, user).groupBy(user)
                .orderBy(user.count().desc()).limit(limit).fetch();
    }

}
