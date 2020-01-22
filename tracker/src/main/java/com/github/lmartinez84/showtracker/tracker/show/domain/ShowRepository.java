package com.github.lmartinez84.showtracker.tracker.show.domain;

import reactor.core.publisher.Mono;

public interface ShowRepository {
    Mono<Show> load(ShowId id);
}
