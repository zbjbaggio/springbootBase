import com.springboot.base.conf.Application;
import com.springboot.base.data.dto.EmailDTO;
import com.springboot.base.util.EmailUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * 描述：
 * Created by jay on 2017-11-13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class EmailUtilsTest {
    @Test
    public void sendEmail() throws Exception {
        EmailUtils.sendEmail(new EmailDTO("你大爷","215344388@qq.com", "大侄子","测试一下","你看看行不行"));
    }

}