package ru.javaops.topjava.graduation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "vote", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "vote_date"}, name = "uk_vote_user_vote_date")
})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote extends BaseEntity {

    @Column(name = "vote_date", nullable = false)
    private LocalDate voteDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    public Vote(Integer id, LocalDate voteDate, User user, Restaurant restaurant) {
        super(id);
        this.voteDate = voteDate;
        this.user = user;
        this.restaurant = restaurant;
    }
}
