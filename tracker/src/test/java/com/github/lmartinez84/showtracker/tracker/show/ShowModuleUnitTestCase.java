package com.github.lmartinez84.showtracker.tracker.show;

import com.github.lmartinez84.showtracker.shared.infrastructure.UnitTestCase;
import com.github.lmartinez84.showtracker.tracker.show.domain.show.ShowRepository;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

public abstract class ShowModuleUnitTestCase extends UnitTestCase {
    protected ShowRepository showRepository;

    @BeforeEach
    void setUp() {
        showRepository = mock(ShowRepository.class);
    }
}
