package com.springboot.base.data.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.springboot.base.JsonSerializer.CustomDoubleSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 描述：
 * Created by jay on 2017-11-28.
 */
@Data
public class EmailOrderContentVO {

    private String orderNo;

    private String receiver_name;

    private String receiver_address1;

    private String receiver_address2;

    private String receiver_city;

    private String receiver_country;

    private String receiver_area;

    private BigDecimal postage;

    private BigDecimal amount;

    private List<OrderDetailVO> orderDetailVOs;

    public String getContent() {
        StringBuilder content = new StringBuilder();
        content.append("<table border=\"1\" style=\"border-collapse:collapse;width: 800px;\">\n");
        content.append("    <tr>\n");
        content.append("        <th colspan=\"4\" style=\"height: 50px;text-align: left;padding-left: 20px;\">Order Confirmed:").append("</th>\n");
        content.append("    </tr>\n");
        content.append("    <tr>\n");
        content.append("        <td colspan=\"4\" style=\"padding: 10px;color: red\">\n");
        content.append("            <div>Thank you for your order at JUN JIE. Once your package ships we will send you a notification email. Your order confirmation is showing as below.</div>\n");
        content.append("            <div style=\"margin-top: 20px;\">Order Number: #20180</div>\n");
        content.append("        </td>\n");
        content.append("    </tr>\n");
        content.append("    <tr>\n");
        content.append("        <th colspan=\"2\">BILLED TO:</th>\n");
        content.append("        <th colspan=\"2\">SHIPPING TO:</th>\n");
        content.append("    </tr>\n");
        content.append("    <tr>\n");
        content.append("        <th colspan=\"2\">BILLED TO:</th>\n");
        content.append("        <th colspan=\"2\">SHIPPING TO:</th>\n");
        content.append("    </tr>\n");
        content.append("    <tr>\n");
        content.append("        <th colspan=\"4\">Order Summary:</th>\n");
        content.append("    </tr>\n");
        content.append("    <tr>\n");
        content.append("        <th>ITEM</th>\n");
        content.append("        <th>QTY</th>\n");
        content.append("        <th>UNIT PRICE</th>\n");
        content.append("        <th>SUBTOTAL</th>\n");
        content.append("    </tr>\n");
        content.append("    <tr>\n");
        content.append("        <th>DOUBLE LAYERED T-SHIRT</th>\n");
        content.append("        <th>1</th>\n");
        content.append("        <th>$133.00</th>\n");
        content.append("        <th>$133.00</th>\n");
        content.append("    </tr>\n");
        content.append("    <tr>\n");
        content.append("        <th>DOUBLE LAYERED T-SHIRT</th>\n");
        content.append("        <th>1</th>\n");
        content.append("        <th>$133.00</th>\n");
        content.append("        <th>$133.00</th>\n");
        content.append("    </tr>\n");
        content.append("    <tr>\n");
        content.append("        <th></th>\n");
        content.append("        <th colspan=\"2\">Item Subtotal</th>\n");
        content.append("        <th>$133.00</th>\n");
        content.append("    </tr>\n");
        content.append("    <tr>\n");
        content.append("        <th></th>\n");
        content.append("        <th colspan=\"2\">Shipping & Handling</th>\n");
        content.append("        <th>$30.00</th>\n");
        content.append("    </tr>\n");
        content.append("    <tr>\n");
        content.append("        <th></th>\n");
        content.append("        <th colspan=\"2\">Tax</th>\n");
        content.append("        <th>$0.00</th>\n");
        content.append("    </tr>\n");
        content.append("    <tr>\n");
        content.append("        <th></th>\n");
        content.append("        <th colspan=\"2\">TOTAL</th>\n");
        content.append("        <th>$200.10</th>\n");
        content.append("    </tr>\n");
        content.append("</table>");
        return content.toString();
    }


}
