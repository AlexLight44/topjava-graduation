package ru.javaops.topjava.graduation.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.javaops.topjava.graduation.app.AuthUser;
import ru.javaops.topjava.graduation.service.VoteService;
import ru.javaops.topjava.graduation.to.VoteTo;

import java.util.List;

@RestController
@RequestMapping(VoteController.REST_URL)
@RequiredArgsConstructor
@Tag(name = "Vote")
public class VoteController {

    public static final String REST_URL = "/api/votes";

    private final VoteService voteService;

    @GetMapping
    @Operation(summary = "Get current user's vote history")
    public List<VoteTo> getAll(@AuthenticationPrincipal AuthUser authUser) {
        return voteService.getAll(authUser.getUser());
    }

    @GetMapping("/today")
    @Operation(summary = "Get current user's vote for today")
    public ResponseEntity<VoteTo> getToday(@AuthenticationPrincipal AuthUser authUser) {
        return ResponseEntity.of(voteService.getTodayVote(authUser.getUser()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create today's vote")
    public VoteTo create(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody VoteTo voteTo) {
        return voteService.create(authUser.getUser(), voteTo.getRestaurantId());
    }

    @PutMapping("/today")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Change today's vote (until 11:00)")
    public void updateToday(@AuthenticationPrincipal AuthUser authUser, @Valid @RequestBody VoteTo voteTo) {
        voteService.updateToday(authUser.getUser(), voteTo.getRestaurantId());
    }
}
