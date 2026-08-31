package ru.javaops.topjava.graduation.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.topjava.graduation.app.AuthUser;
import ru.javaops.topjava.graduation.service.VoteService;
import ru.javaops.topjava.graduation.to.VoteTo;

@RestController
@RequiredArgsConstructor
@Tag(name = "Vote")
public class VoteController {

    public static final String REST_URL = "/api/votes";

    private final VoteService voteService;

    @PostMapping("/api/restaurants/{restaurantId}/votes")
    @Operation(summary = "Vote for a restaurant today (changeable until 11:00)")
    public VoteTo vote(@AuthenticationPrincipal AuthUser authUser,
                       @PathVariable int restaurantId) {
        return voteService.vote(authUser.getUser(), restaurantId);
    }

    @GetMapping(REST_URL + "/today")
    @Operation(summary = "Get current user's vote for today")
    public ResponseEntity<VoteTo> getToday(@AuthenticationPrincipal AuthUser authUser) {
        return ResponseEntity.of(voteService.getTodayVote(authUser.getUser()));
    }
}
