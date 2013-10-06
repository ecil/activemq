package com.ornekler;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;


import javax.jms.*;


/**
 * User: eyyubcil
 */
public class Producer {
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

    private static String DESTINATION_NAME = "KODCU.ORNEK";//mesaj gondermek istedigimiz kuyruk bilgisi

    private static MessageProducer producer;

    public static void main(String[] args) {
        factory = new ActiveMQConnectionFactory(brokerUrl);
        try {

            connection = factory.createConnection(username, password);
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            destination = session.createQueue(DESTINATION_NAME);
            producer = session.createProducer(destination);
        } catch (JMSException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        ActiveMQTextMessage textMessage = new ActiveMQTextMessage();
        int count = 1;
        while (true) {
            try {
                textMessage.setText("Message id:" + count);
                System.out.println("Gönderiliyor..");
                producer.send(textMessage);
                Thread.sleep(6000);
                count++;
            } catch (MessageNotWriteableException e) {
                e.printStackTrace();
            } catch (JMSException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
