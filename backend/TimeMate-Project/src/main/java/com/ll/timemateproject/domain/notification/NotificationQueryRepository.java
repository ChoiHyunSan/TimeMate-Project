package com.ll.timemateproject.domain.notification;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ll.timemateproject.domain.notification.QNotification.notification;

@Repository
@RequiredArgsConstructor
public class NotificationQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Notification> findByUsername(String username) {
        return queryFactory.selectFrom(notification)
                .leftJoin(notification.schedule).fetchJoin()
                .leftJoin(notification.schedule.user).fetchJoin()
                .where(notification.schedule.user.username.eq(username))
                .fetch();
    }

    public Notification findByIdAndUsername(Long id, String name) {
        return queryFactory.selectFrom(notification)
                .leftJoin(notification.schedule).fetchJoin()
                .leftJoin(notification.schedule.user).fetchJoin()
                .where(notification.schedule.user.username.eq(name).and(notification.id.eq(id)))
                .fetchOne();
    }
}
