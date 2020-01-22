package com.github.lmartinez84.showtracker.shared.infrastructure.bus.command;

import com.github.lmartinez84.showtracker.shared.domain.bus.command.Command;
import com.github.lmartinez84.showtracker.shared.domain.bus.command.CommandHandler;
import org.reflections.Reflections;
import org.springframework.context.ApplicationContext;
import reactor.core.publisher.ReplayProcessor;

import java.lang.reflect.ParameterizedType;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
public class CommandBusSubscribers {
    private ApplicationContext context;
    private Map<Class<? extends Command>, Class<? extends CommandHandler>> indexedCommandHandlers;
    private Map<Class<? extends Command>, ReplayProcessor<Command>> publisher;

    public CommandBusSubscribers(ApplicationContext context) {
        this.context = context;
        Reflections reflections = new Reflections("com.github.lmartinez84.showtracker");
        Set<Class<? extends CommandHandler>> classes = reflections.getSubTypesOf(CommandHandler.class);
        indexedCommandHandlers = formatHandlers(classes);
        publisher = publishersFromHandlers(classes);
        subscribe();
    }

    private void subscribe() {
        for (Map.Entry<Class<? extends Command>, ReplayProcessor<Command>> entry : publisher.entrySet()) {
            ReplayProcessor<Command> pub = entry.getValue();
            Class<? extends CommandHandler> handlerClass = indexedCommandHandlers.get(entry.getKey());
            CommandHandler handler = context.getBean(handlerClass);
            pub.subscribe(handler::handle);
        }
    }

    public ReplayProcessor<Command> getPublisherFor(Class<? extends Command> commandClass) {
        return publisher.get(commandClass);
    }

    private Map<Class<? extends Command>, ReplayProcessor<Command>> publishersFromHandlers(Set<Class<? extends CommandHandler>> commandHandlers) {
        return commandHandlers.stream().map(this::addPublisher)
                              .collect(Collectors.toMap(
                                      AbstractMap.SimpleEntry::getKey,
                                      AbstractMap.SimpleEntry::getValue
                              ));
    }

    private AbstractMap.SimpleEntry<Class<? extends Command>, ReplayProcessor<Command>> addPublisher(Class<? extends CommandHandler> handler) {
        ParameterizedType paramType = (ParameterizedType) handler.getGenericInterfaces()[0];
        Class<? extends Command> commandClass = (Class<? extends Command>) paramType.getActualTypeArguments()[0];
        ReplayProcessor<Command> processor = ReplayProcessor.create();
        return new HashMap.SimpleEntry<>(commandClass, processor);
    }

    private Map<Class<? extends Command>, Class<? extends CommandHandler>> formatHandlers(Set<Class<? extends CommandHandler>> commandHandlers) {
        return commandHandlers.stream().map(this::addCommandHandler)
                              .collect(Collectors.toMap(
                                      AbstractMap.SimpleEntry::getKey,
                                      AbstractMap.SimpleEntry::getValue
                              ));

    }

    private AbstractMap.SimpleEntry<Class<? extends Command>, Class<? extends CommandHandler>> addCommandHandler(Class<? extends CommandHandler> handler) {
        ParameterizedType paramType = (ParameterizedType) handler.getGenericInterfaces()[0];
        Class<? extends Command> commandClass = (Class<? extends Command>) paramType.getActualTypeArguments()[0];
        return new HashMap.SimpleEntry<>(commandClass, handler);
    }
}
