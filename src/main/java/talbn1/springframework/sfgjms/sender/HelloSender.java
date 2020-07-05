package talbn1.springframework.sfgjms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import talbn1.springframework.sfgjms.config.JmsConfig;
import talbn1.springframework.sfgjms.model.HelloWorldMessage;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

/**
 * @author talbn on 7/5/2020
 **/
@RequiredArgsConstructor
@Component
public class HelloSender {

    @Autowired
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 2000)
    private void sendMessage(){

        System.out.println("Im Sending a message");
        HelloWorldMessage message = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hello there")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);



    }


    @Scheduled(fixedRate = 2000)
    private void sendAndReceiveMessage() throws JMSException {

        System.out.println("Im Sending a message");
        HelloWorldMessage message = HelloWorldMessage.builder()
                .id(UUID.randomUUID())
                .message("Hello")
                .build();


        Message receivedMsg =  jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_RECEIVE_QUEUE, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                try{
                    Message helloMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
                    helloMessage.setStringProperty("_type","talbn1.springframework.sfgjms.model.HelloWorldMessage");

                    System.out.println("Sending hello");

                    return helloMessage;
                }catch (JsonProcessingException jsonProcessingException){
                    throw new JMSException("boom");
                }
            }
        });
        System.out.println(receivedMsg.getBody(String.class));
        }
}

