package com.github.lmartinez84.showtracker.tracker.show.domain.show;

import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.lmartinez84.showtracker.shared.domain.Identifier;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ShowRepository {
    Mono<Show> load(Identifier id);

    Flux<DomainEvent> save(Show show);
}
