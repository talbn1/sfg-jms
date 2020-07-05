package talbn1.springframework.sfgjms.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import sun.net.www.MessageHeader;
import talbn1.springframework.sfgjms.config.JmsConfig;
import talbn1.springframework.sfgjms.model.HelloWorldMessage;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

/**
 * @author talbn on 7/5/2020
 **/

@Component
@RequiredArgsConstructor
public class HelloMessageListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders headers, Message message){
/*

        System.out.println("I got a message!!!");
        System.out.println(helloWorldMessage);
        System.out.println("end of message");
*/

    }

    @JmsListener(destination = JmsConfig.MY_SEND_RECEIVE_QUEUE)
    public void listenForHello(@Payload HelloWorldMessage helloWorldMessage,
                       @Headers MessageHeaders headers, Message message) throws JMSException {

        HelloWorldMessage payload = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("world")
                .build();

        jmsTemplate.convertAndSend(message.getJMSReplyTo(), payload);


    }
}
