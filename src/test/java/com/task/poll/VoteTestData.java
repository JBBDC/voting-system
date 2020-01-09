package com.task.poll;

import com.task.poll.model.Restaurant;
import com.task.poll.model.Vote;
import com.task.poll.to.RestaurantTo;
import com.task.poll.to.VoteTo;

import java.time.LocalDate;

import static com.task.poll.RestaurantTestData.*;
import static com.task.poll.UserTestData.*;

public class VoteTestData {

    public static int VOTE_1_ID = 100013;

    public static Vote USER_VOTE_1 = new Vote(VOTE_1_ID, USER, LocalDate.of(2019, 11, 20), REST_2);
    public static Vote USER_VOTE_2 = new Vote(VOTE_1_ID + 1, USER, LocalDate.now(), REST_1);
    public static Vote ADMIN_VOTE_1 = new Vote(VOTE_1_ID + 2, ADMIN, LocalDate.of(2019, 11, 20), REST_2);
    public static Vote ADMIN_VOTE_2 = new Vote(VOTE_1_ID + 3, ADMIN, LocalDate.now(), REST_3);

    public static Vote getCreated() {
        return new Vote(VOTE_1_ID + 4, USER2, LocalDate.now(), REST_1);
    }
    public static Vote getUpdated() {
        return new Vote(VOTE_1_ID + 4, USER, LocalDate.now(), REST_2);
    }

    public static TestMatchers<VoteTo> VOTE_TO_MATCHERS = TestMatchers.useEquals(VoteTo.class);
    public static TestMatchers<Vote> VOTE_MATCHERS = TestMatchers.useFieldsComparator(Vote.class);

}
