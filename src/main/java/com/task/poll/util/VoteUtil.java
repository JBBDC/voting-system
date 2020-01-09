package com.task.poll.util;

import com.task.poll.model.Vote;
import com.task.poll.repository.CrudVoteRepository;
import com.task.poll.to.VoteTo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class VoteUtil {

    public static List<VoteTo> makeTos(Collection<Vote> votes) {
        if(votes != null) {
            return votes.stream()
                    .map(v -> new VoteTo(v.getId(),v.getUser().getId(),v.getRestaurant().getId(),v.getDate()))
                    .sorted(Comparator.comparing(VoteTo::getDate).reversed())
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public static List<VoteTo> makeTos(Vote... votes) {
        return makeTos(List.of(votes));
    }

    public static VoteTo makeTo(Vote vote){
        return new VoteTo(vote.getId(),vote.getUser().getId(),vote.getRestaurant().getId(),vote.getDate());
    }
}
