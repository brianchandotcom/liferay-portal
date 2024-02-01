package com.liferay.partner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.liferay.petra.string.StringBundler;
import com.rabbitmq.client.Channel;

@Component
public class QueueListener {

    @RabbitListener(bindings = {
        @QueueBinding(value = @Queue("${spring.rabbitmq.default.queue}"), key="account.update", exchange = @Exchange("koroneiki"))
    })
    public void accountUpdateListener(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        try {
            if ((message == null) || message.isEmpty()) {
                if (_log.isWarnEnabled()) {
                    _log.warn(
                        StringBundler.concat(
                            "Message ", deliveryTag, " with routing key koroneiki.account.update contained no data"));
                }
    
                channel.basicReject(deliveryTag, false);
    
                return;
            }
    
            System.out.println("Update: " + message);
    
            channel.basicAck(deliveryTag, false);
        } catch (Exception exception) {
            _log.error(exception);
        }
    }

    private static final Log _log = LogFactory.getLog(QueueListener.class);
}
