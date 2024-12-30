package com.ll.timemateproject.domain.schedule;

import com.ll.timemateproject.api.v1.dto.response.schedule.ScheduleListResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.ll.timemateproject.domain.schedule.QSchedule.schedule;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ScheduleQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<ScheduleListResponse> getScheduleListByDateAndCategory(String username, LocalDate startDate, LocalDate endDate, Long categoryId) {
        return queryFactory.selectFrom(schedule)
                .leftJoin(schedule.category).fetchJoin()
                .leftJoin(schedule.user).fetchJoin()
                .where(usernameEqual(username).and(containsDate(startDate, endDate)).and(categoryEqual(categoryId)))
                .fetch()
                .stream()
                .map(ScheduleListResponse::of)
                .toList();
    }

    public Optional<Schedule> getScheduleWithUserById(Long id) {
        return Optional.ofNullable(queryFactory.selectFrom(schedule)
                .leftJoin(schedule.user).fetchJoin()
                .where(schedule.id.eq(id))
                .fetchOne());
    }

    private static BooleanExpression usernameEqual(String username) {
        return schedule.user.username.eq(username);
    }

    private BooleanExpression containsDate(LocalDate startDate, LocalDate endDate) {
        return schedule.startDatetime.before(endDate.atStartOfDay())
                .and(schedule.endDatetime.after(startDate.atStartOfDay()));
    }

    private static BooleanExpression categoryEqual(Long categoryId) {
        return categoryId != null ? schedule.category.id.eq(categoryId) : null;
    }
}
