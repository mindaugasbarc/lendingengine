package com.peerlender.lendingengine.application.config;

import com.peerlender.lendingengine.domain.event.UserRegisteredEventHandler;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {

    private static final String TOPIC = "userRegisteredTopic";
    private static final String QUEUE_NAME = "user.registered";

    @Bean
    public Queue userRegisteredQueue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    public TopicExchange userRegisteredTopic() {
        return new TopicExchange(TOPIC);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with("user.#");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter userRegisteredEventListener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(userRegisteredEventListener);
        return container;
    }

    @Bean
    MessageListenerAdapter userRegisteredEventListener(UserRegisteredEventHandler userRegisteredEventHandler) {
        return new MessageListenerAdapter(userRegisteredEventHandler, "handleUserRegistration");
    }
}