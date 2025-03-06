package kr.kookmin.jeongo3.User;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static kr.kookmin.jeongo3.User.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public long updateReport(String id) {
        return queryFactory.update(user)
                .set(user.report, user.report.add(1))
                .where(user.id.eq(id))
                .execute();
    }
}
