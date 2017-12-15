package com.springboot.base.data.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.springboot.base.jsonserializer.CustomDoubleSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * 描述：
 * Created by jay on 2017-11-28.
 */
@Data
public class EmailOrderContentVO {

    private String order_no;

    private Date create_time;

    private String email;

    private String phone;

    private String receiver_name;

    private String receiver_address1;

    private String receiver_address2;

    private String receiver_city;

    private String receiver_country;

    private String receiver_area;

    private String createTime;

    @JsonSerialize(using = CustomDoubleSerialize.class)
    private BigDecimal postage;

    @JsonSerialize(using = CustomDoubleSerialize.class)
    private BigDecimal amount;

    private List<OrderDetailVO> orderDetailVOs;

    public String getContent() {
        StringBuilder content = new StringBuilder();
        content.append("<table border=\"1\" style=\"border-collapse:collapse;width: 800px;\">\n");
        content.append("    <tr>\n" );
        content.append("        <th colspan=\"4\" style=\"height: 80px;text-align: left;font-size:20px;padding-left: 10px;\">Order Confirmed:#2018").append(order_no).append("</th>\n" );
        content.append("    </tr>\n" );
        content.append("    <tr>\n" );
        content.append("        <td colspan=\"4\" style=\"padding: 10px;\">\n" );
        content.append("            <div>Thank you for your order at JUN JIE. Once your package ships we will send you a notification email. Your order confirmation is showing as below.</div>\n" );
        content.append("            <div style=\"margin-top: 20px;\">Order Number: <strong>#2018").append(order_no).append(" ").append(createTime).append("</strong></div>\n" );
        content.append("        </td>\n" );
        content.append("    </tr>\n" );
        content.append("    <tr style=\"text-align: left;\">\n" );
        content.append("        <th colspan=\"2\" style=\"padding-left: 10px;height: 50px;\">BILLED TO:</th>\n" );
        content.append("        <th colspan=\"2\" style=\"padding-left: 10px;\">SHIPPING TO:</th>\n" );
        content.append("    </tr>\n" );
        content.append("    <tr>\n" );
        content.append("        <th colspan=\"2\" style=\"width: 50%;text-align: left;padding: 10px;font-weight: normal\">\n" );
        content.append("            <div style=\"margin-top: 20px;\">").append(receiver_address1).append("</div>\n" );
        content.append("            <div style=\"margin-top: 10px;\">").append(receiver_city).append("</div>\n" );
        content.append("            <div style=\"margin-top: 10px;\">Paypal</div>\n" );
        content.append("            <div style=\"margin-top: 10px;\">").append(email).append("</div>\n" );
        content.append("        </th>\n" );
        content.append("        <th colspan=\"2\" valign=\"top\" style=\"width: 50%;text-align: left;padding: 10px;font-weight: normal\">\n" );
        content.append("            <div style=\"margin-top: 20px;\">").append(receiver_address1).append("</div>\n" );
        content.append("            <div style=\"margin-top: 10px;\">").append(receiver_city).append("</div>\n" );
        content.append("            <div style=\"margin-top: 10px;\">").append(phone).append("</div>\n" );
        content.append("            <div style=\"margin-top: 10px;\">").append(receiver_area).append("</div>\n" );
        content.append("        </th>\n" );
        content.append("    </tr>\n" );
        content.append("    <tr>\n" );
        content.append("        <th colspan=\"4\" style=\"height: 80px;font-size: 20px;text-align: left;padding-left: 10px;\">Order Summary:</th>\n" );
        content.append("    </tr>\n" );
        content.append("    <tr style=\"text-align: left;padding-left: 10px;\">\n" );
        content.append("        <th style=\"padding-left: 10px;height: 40px;\">ITEM</th>\n" );
        content.append("        <th style=\"padding-left: 10px;height: 40px;\">QTY</th>\n" );
        content.append("        <th style=\"padding-left: 10px;height: 40px;\">UNIT PRICE</th>\n" );
        content.append("        <th style=\"padding-left: 10px;height: 40px;\">SUBTOTAL</th>\n" );
        content.append("    </tr>\n" );
        //产品循环
        for (OrderDetailVO orderDetailVO : orderDetailVOs) {
            content.append("    <tr style=\"font-weight: normal\">\n");
            content.append("        <th valign=\"top\" style=\"text-align: left;font-weight: normal;padding: 10px;\">\n" );
            content.append("            <div>").append(orderDetailVO.getProduct_name()).append("</div>\n" );
            content.append("            <div>").append(orderDetailVO.getSize()).append("</div>\n" );
            content.append("        </th>\n" );
            content.append("        <th valign=\"top\" style=\"text-align: left;font-weight: normal;padding: 10px;\">").append(orderDetailVO.getNumber()).append("</th>\n" );
            content.append("        <th valign=\"top\" style=\"text-align: left;font-weight: normal;padding: 10px;font-style:italic\">").append(orderDetailVO.getPrice()).append("</th>\n" );
            content.append("        <th valign=\"top\" style=\"text-align: left;font-weight: normal;padding: 10px;font-style:italic\">").append(orderDetailVO.getAmount()).append("</th>\n" );
            content.append("    </tr>\n" );
        }
        DecimalFormat decFormat = new DecimalFormat("#.00");
        String itemSubtotal = decFormat.format(amount.subtract(postage));
        content.append("    <tr style=\"height: 30px;\">\n" );
        content.append("        <th colspan=\"4\"></th>\n" );
        content.append("    </tr>\n" );
        content.append("    <tr>\n" );
        content.append("        <th></th>\n" );
        content.append("        <th colspan=\"2\" style=\"text-align: left;padding: 30px 10px 10px;font-weight: normal\">\n" );
        content.append("            <div>Item Subtotal</div>\n" );
        content.append("            <div style=\"margin-top: 10px;\">Shipping & Handling</div>\n" );
        content.append("            <div style=\"margin-top: 10px;\">Tax</div>\n" );
        content.append("            <div style=\"margin-top: 10px;\">TOTAL</div>\n" );
        content.append("        </th>\n" );
        content.append("        <th style=\"text-align: left;padding: 30px 10px 10px;font-weight: normal\">\n" );
        content.append("            <div>$").append(itemSubtotal).append("</div>\n" );
        content.append("            <div style=\"margin-top: 10px;font-style:italic\">$").append(postage).append("</div>\n" );
        content.append("            <div style=\"margin-top: 10px;font-style:italic\">$0.00</div>\n" );
        content.append("            <div style=\"margin-top: 10px;font-style:italic\">$").append(amount).append("</div>\n" );
        content.append("        </th>\n" );
        content.append("    </tr>\n" );
        content.append("    <tr>\n" );
        content.append("        <th style=\"padding: 40px 10px 10px\" colspan=\"4\">\n" );
        content.append("            <div style=\"font-weight: bold;font-size: 35px;text-align: right\">JUN JIE</div>\n" );
        content.append("            <div style=\"font-weight: normal;text-align: right;margin-top: 10px\">https://www.junjieofficial.com</div>\n" );
        content.append("        </th>\n" );
        content.append("    </tr>\n" );
        content.append("</table>");
        return content.toString();
    }


}
