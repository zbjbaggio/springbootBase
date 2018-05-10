import com.springboot.base.conf.Application;
import com.springboot.base.service.PermissionInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

/**
 * 描述：
 * Created by jay on 2017-12-13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
public class PermissionServiceTest {

    @Inject
    private PermissionInfoService permissionInfoService;

    @Test
    public void sendEmail() throws Exception {
        //permissionInfoService.getMenu(32);
    }

}
