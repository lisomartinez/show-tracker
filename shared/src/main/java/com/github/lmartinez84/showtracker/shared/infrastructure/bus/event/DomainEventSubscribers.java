package com.github.lmartinez84.showtracker.shared.infrastructure.bus.event;

import com.github.lmartinez84.showtracker.shared.domain.DomainEvent;
import com.github.lmartinez84.showtracker.shared.domain.bus.event.EventHandler;
import org.reflections.Reflections;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ReplayProcessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

@Service
public final class DomainEventSubscribers {
    private ApplicationContext context;
    private Map<Class<? extends DomainEvent>, Set<Class<? extends EventHandler>>> subscribers;
    private Map<Class<? extends DomainEvent>, List<ReplayProcessor<DomainEvent>>> publishers;

    public DomainEventSubscribers(ApplicationContext context) {
        this.context = context;
        Reflections reflections = new Reflections("com.github.lmartinez84.showtracker");
        Set<Class<? extends EventHandler>> handlers = reflections.getSubTypesOf(EventHandler.class);
        this.subscribers = new HashMap<>();
        this.publishers = new HashMap<>();
        this.subscribers = formatHandlers(handlers);
        this.publishers = publishersFromHandlers(handlers);
        subscribe();
    }

    private void subscribe() {
        for (Map.Entry<Class<? extends DomainEvent>, List<ReplayProcessor<DomainEvent>>> entry : publishers.entrySet()) {
            Set<Class<? extends EventHandler>> handlerClassSet = subscribers.get(entry.getKey());
            for (Class<? extends EventHandler> handlerClass : handlerClassSet) {
                EventHandler handler = context.getBean(handlerClass);
                List<ReplayProcessor<DomainEvent>> publisherList = entry.getValue();
                for (ReplayProcessor<DomainEvent> publisher : publisherList) {
                    publisher.subscribe(handler::handle);
                }
            }
        }
    }

    private Map<Class<? extends DomainEvent>, Set<Class<? extends EventHandler>>> formatHandlers(
            Set<Class<? extends EventHandler>> handlers
    ) {
        return handlers.stream().map(this::addEventHandler)
                       .collect(Collectors.toMap(
                               AbstractMap.SimpleEntry::getKey,
                               AbstractMap.SimpleEntry::getValue
                       ));

    }

    private AbstractMap.SimpleEntry<Class<? extends DomainEvent>, Set<Class<? extends EventHandler>>> addEventHandler(
            Class<? extends EventHandler> handler
    ) {
        ParameterizedType paramType = (ParameterizedType) handler.getGenericInterfaces()[0];
        Class<? extends DomainEvent> eventClass = (Class<? extends DomainEvent>) paramType.getActualTypeArguments()[0];
        Set<Class<? extends EventHandler>> handlerSet = subscribers.getOrDefault(eventClass, new HashSet<>());
        handlerSet.add(handler);
        return new HashMap.SimpleEntry<>(eventClass, handlerSet);
    }

    private Map<Class<? extends DomainEvent>, List<ReplayProcessor<DomainEvent>>> publishersFromHandlers(
            Set<Class<? extends EventHandler>> commandHandlers
    ) {
        return commandHandlers.stream().map(this::addPublisher)
                              .collect(Collectors.toMap(
                                      AbstractMap.SimpleEntry::getKey,
                                      AbstractMap.SimpleEntry::getValue
                              ));
    }

    private AbstractMap.SimpleEntry<Class<? extends DomainEvent>, List<ReplayProcessor<DomainEvent>>> addPublisher(
            Class<? extends EventHandler> handler
    ) {
        ParameterizedType paramType = (ParameterizedType) handler.getGenericInterfaces()[0];
        Class<? extends DomainEvent> eventClass =
                (Class<? extends DomainEvent>) paramType.getActualTypeArguments()[0];
        List<ReplayProcessor<DomainEvent>> publisherList = publishers.getOrDefault(eventClass, new ArrayList<>());
        publisherList.add(ReplayProcessor.create());
        return new HashMap.SimpleEntry<>(eventClass, publisherList);
    }

    public Flux<ReplayProcessor<DomainEvent>> forEvent(DomainEvent event) {
        return Flux.fromIterable(publishers.get(event.getClass()));
    }


    public List<String> allTypes() {
        List<String> types = new ArrayList<>();
        for (Class<? extends DomainEvent> aClass : subscribers.keySet()) {
            try {
                DomainEvent domainEvent = aClass.getConstructor().newInstance();
                String type = domainEvent.type();
                if (type == null || type.isBlank()) throw new RuntimeException("type is null/empty");
                types.add(type);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        if (types.isEmpty()) throw new RuntimeException("types list empty");
        return types;
    }
}
