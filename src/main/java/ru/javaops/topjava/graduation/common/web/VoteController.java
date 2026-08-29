package ru.javaops.topjava.graduation.common.web;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.topjava.graduation.app.AuthUser;
import ru.javaops.topjava.graduation.common.model.Vote;
import ru.javaops.topjava.graduation.common.service.VoteService;

@RestController
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping("/api/restaurants/{restaurantId}/votes")
    public Vote vote(@AuthenticationPrincipal AuthUser authUser,
                     @PathVariable int restaurantId) {
        return voteService.vote(authUser.getUser(), restaurantId);
    }

    @GetMapping("/api/votes/today")
    public Vote getToday(@AuthenticationPrincipal AuthUser authUser) {
        return voteService.getTodayVote(authUser.getUser());
    }


}
