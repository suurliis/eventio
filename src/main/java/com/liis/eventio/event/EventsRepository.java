package com.liis.eventio.event;

import com.liis.eventio.event.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventsRepository extends CrudRepository<Event,Integer> {
    public Long countById(Integer id);
}
