package com.github.lmartinez84.showtracker.tracker.show.infrastructure;

import com.github.lmartinez84.showtracker.shared.domain.store.EventStoreAdapter;
import com.github.lmartinez84.showtracker.shared.infrastructure.store.RestEventSourcingRepository;
import com.github.lmartinez84.showtracker.tracker.show.domain.Show;
import com.github.lmartinez84.showtracker.tracker.show.domain.ShowId;
import com.github.lmartinez84.showtracker.tracker.show.domain.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public final class ShowEventSourcedRepository extends RestEventSourcingRepository<Show, ShowId> implements ShowRepository {

    @Autowired
    public ShowEventSourcedRepository(EventStoreAdapter<Show> adapter) {
        super(adapter);
    }

    @Override
    public Mono<Show> load(ShowId id) {
        return adapter.load(Show.class.getName(), id);
    }


}
