import com.springboot.base.conf.Application;
import com.springboot.base.data.dto.EmailDTO;
import com.springboot.base.util.EmailUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 描述：
 * Created by jay on 2017-11-13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
public class EmailUtilsTest {
    @Test
    public void sendEmail() throws Exception {
        String content = "<table border=\"1\" style=\"border-collapse:collapse;width: 800px;\">\n" +
                "    <tr>\n" +
                "        <th colspan=\"4\" style=\"height: 50px;text-align: left;padding-left: 20px;\">Order Confirmed:</th>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td colspan=\"4\" style=\"padding: 10px;color: red\">\n" +
                "            <div>Thank you for your order at JUN JIE. Once your package ships we will send you a notification email. Your order confirmation is showing as below.</div>\n" +
                "            <div style=\"margin-top: 20px;\">Order Number: #20180</div>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <th colspan=\"2\">BILLED TO:</th>\n" +
                "        <th colspan=\"2\">SHIPPING TO:</th>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <th colspan=\"2\">BILLED TO:</th>\n" +
                "        <th colspan=\"2\">SHIPPING TO:</th>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <th colspan=\"4\">Order Summary:</th>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <th>ITEM</th>\n" +
                "        <th>QTY</th>\n" +
                "        <th>UNIT PRICE</th>\n" +
                "        <th>SUBTOTAL</th>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <th>DOUBLE LAYERED T-SHIRT</th>\n" +
                "        <th>1</th>\n" +
                "        <th>$133.00</th>\n" +
                "        <th>$133.00</th>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <th>DOUBLE LAYERED T-SHIRT</th>\n" +
                "        <th>1</th>\n" +
                "        <th>$133.00</th>\n" +
                "        <th>$133.00</th>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <th></th>\n" +
                "        <th colspan=\"2\">Item Subtotal</th>\n" +
                "        <th>$133.00</th>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <th></th>\n" +
                "        <th colspan=\"2\">Shipping & Handling</th>\n" +
                "        <th>$30.00</th>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <th></th>\n" +
                "        <th colspan=\"2\">Tax</th>\n" +
                "        <th>$0.00</th>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <th></th>\n" +
                "        <th colspan=\"2\">TOTAL</th>\n" +
                "        <th>$200.10</th>\n" +
                "    </tr>\n" +
                "</table>";


        EmailUtils.sendEmail(new EmailDTO("你大爷", "215344388@qq.com", "大侄子", "测试一下", content));
    }

}