package ibadi.msg;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {
	private static final String BROKER_URL="failover://tcp://192.168.1.104:61616";
	public static void main(String[] args) throws JMSException, InterruptedException{
		//创建连接工厂
		ConnectionFactory connectionFactory=new ActiveMQConnectionFactory(BROKER_URL);
		
		//获取连接对象
		Connection connection=connectionFactory.createConnection();
		
		//start
		connection.start();
		
		//创建session,此方法第一个参数回话表示是否在事务中执行，第二个参数设置会话的答应模式
		Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//创建队列
		Destination destination=session.createQueue("test-queue");
		
		//创建消息产生者
		MessageProducer producer=session.createProducer(destination);
		
		for (int i = 0; i < 100; i++) {
			//初始化一个mq消息
			TextMessage message=session.createTextMessage("这个是第 " +i+" 条消息！");
			//发送消息
			producer.send(message);
			System.out.println("发送消息 "+i);
			//暂停 3秒
			Thread.sleep(3000);
		}
		
		//关闭链接
		connection.close();
	}
	
}
