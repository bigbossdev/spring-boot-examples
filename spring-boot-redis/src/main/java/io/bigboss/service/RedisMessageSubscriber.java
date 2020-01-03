package io.bigboss.service;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class RedisMessageSubscriber implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] bytes) {
        System.out.println("[received] " + message.toString());
    }

}
