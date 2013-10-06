package com.ornekler;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.*;

/**
 * user: Eyyub ÇİL
 */
public class Consumer{
    private static String brokerUrl = "tcp://localhost:61616";//sunucu adresi
    private static String username = "admin";//sunucu kullanici adi
    private static String password = "admin";//sunucu sifresi

    /**
     * Constant <code>factory</code>
     */
    private static ConnectionFactory factory;
    private static Connection connection;
    private static Session session;
    private static Destination destination;

    private static String DESTINATION_NAME = "KODCU.ORNEK";//mesaj almak istedigimiz kuyruk bilgisi

    private static MessageConsumer consumer;

    public static void main(String[] args) {
        factory = new ActiveMQConnectionFactory(brokerUrl);
        try {

            connection = factory.createConnection(username, password);
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            destination = session.createQueue(DESTINATION_NAME);
            consumer = session.createConsumer(destination);
           MessageListener listener = new MessageListener() {
               @Override
               public void onMessage(Message message) {
                   if (message instanceof ActiveMQTextMessage) {
                      ActiveMQTextMessage textMessage = (ActiveMQTextMessage) message;
                       try {
                           System.out.println(textMessage.getText());
                       } catch (JMSException e) {
                           e.printStackTrace();
                       }
                   }
               }
           };
            consumer.setMessageListener(listener);


        } catch (JMSException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
