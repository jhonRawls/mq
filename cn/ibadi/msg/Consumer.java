package ibadi.msg;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消费者
 * @author jhon
 *
 */
public class Consumer implements MessageListener {
	
	private static final String BROKER_URL="failover://tcp://192.168.1.104:61616";
	
	public static void main(String [] args) throws JMSException{
		//创建连接工厂
		ConnectionFactory connectionFactory=new ActiveMQConnectionFactory(BROKER_URL);
		
		//获取连接对象
		Connection connection=connectionFactory.createConnection();
		
		//开始
		connection.start();
		
		//创建Session，此方法第一个参数表示会话是否在事务中执行，第二个参数设定会话的应答模式
		Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//创建队列
		Destination destination=session.createQueue("test-queue");
		
		//消息消费者
		MessageConsumer consumer=session.createConsumer(destination);
		
		//初始化MessageListener
		Consumer msgConsumer =new Consumer();
		
		//给消费者设定监听对象
		consumer.setMessageListener(msgConsumer);
	}

	public void onMessage(Message msg) {
		// TODO Auto-generated method stub
		TextMessage textMessage=(TextMessage)msg;
		try {
			System.out.println("get message "+textMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
