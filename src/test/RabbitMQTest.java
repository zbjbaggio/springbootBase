import com.springboot.base.conf.Application;
import com.springboot.base.rabbitmq.demo.HelloSender1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 描述：
 * Created by jay on 2018-9-12.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RabbitMQTest {

    @Autowired
    private HelloSender1 helloSender1;



    @Test
    public void helloManyToMany() throws Exception {
                helloSender1.send(1);
        Thread.sleep(10000);
    }

}
