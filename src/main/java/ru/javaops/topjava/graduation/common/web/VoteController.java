package ru.javaops.topjava.graduation.common.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    public static final String REST_URL = "/api/votes";

    private final VoteService voteService;

    @PostMapping("/api/restaurants/{restaurantId}/votes")
    public Vote vote(@AuthenticationPrincipal AuthUser authUser,
                     @PathVariable int restaurantId) {
        return voteService.vote(authUser.getUser(), restaurantId);
    }

    @GetMapping(REST_URL + "/today")
    public ResponseEntity<Vote> getToday(@AuthenticationPrincipal AuthUser authUser) {
        return ResponseEntity.of(voteService.getTodayVote(authUser.getUser()));
    }
}
