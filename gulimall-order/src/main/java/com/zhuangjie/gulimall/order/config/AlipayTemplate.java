package com.zhuangjie.gulimall.order.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.zhuangjie.gulimall.order.vo.PayVo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "alipay")
@Component
@Data
public class AlipayTemplate {

    //在支付宝创建的应用的id
    private   String app_id = "2021000121602270";

    // 商户私钥，您的PKCS8格式RSA2私钥
    private  String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCMmUOx3vaETPdgoCJgKIgDk58q2p57CkFXin9pFkoOuHYSyk+ByIuAGnTlnAdFe0hfk4x6mONJDs6sU3sO5bpqRnwXmnq0zgB3yazF3CRznRds+X+cakdUdOj3hobVTHkottLi/4jATmHx4ZWvSmG4oYWwcPf8Gk8K/kD5TXn7CZAfcvlXMeSF5jdiHGQ/bSOpC1wOoCgZ6SDNxphdIfW2sRBwcQOabX0zNLnXrkrs+z9r60Byeql8SGZ2yeyRh328xW/pDp4m++KPhZKwMasUxnYGfVLgqe2/nmoV5YhNR/FXpULhmX8JJGtOrzZ9BVA23ld+XE1KBPUaOuzyF86hAgMBAAECggEATq8zcGcMZUxBoWTc+B2WyRGjoTMKQGej298ZoPElbwmSor9wMXzPfVjRFPL2Ks9s2HhVxQKtKGTOCykJFHRCmFlr9zWv+GHVZzSJqY2jJMTEqGsVHK9pzf1Dp8s0yEhtAfkXGozRtnXnYJvTzm79M3vJus3uKrt6gvd520uduXxtzboXCQbQhJ76i73SFcVzvoMmFjqexgbiJru4tWBQlc2PGAF1K7MybGi9PctU2r46Mxe/UGGNVQ1+qCTJisVY6ONOdirhr9SqGFxFZPhndqDSN0kAx1BZBe9vSih+wb0IJ7V0XsMuax9kbqInLiNq1uKnpENwFEqnILN5N3OVwQKBgQDtz52FSJtqGfcSwiIjERAshhPs/bPXNJ+csxmEDe5Icnq8CI32IlLkh/h3PgkvZuoSVnum9tKBEoG7BR+brgzvLqhKXypmlxfE0K+xk7D1nE2t6oKhL5rS2zlgISolniTvdKiN217TxqMZK64Qn76/DYH0Hoct/9jOhClgVlEaGQKBgQCXWjatZxKKkB8H2VQUW/5guJZeLBc6ZSO2QAh8AFmusIR/bsIbQp8oJkLYN2838fQ+ugAQz0bDVXAfH6RX6dTH2Bd/W+mtIfhNbLU+RtZDzbGc2rrnoTT2Hs5M6qI1soYA2v94ePzwG17WJX/KqBeNCMvjO1koXYznCgcQ60j5yQKBgQDKQ8SJgWjWRIFVzhJ02uVzyRIXhu04REThZZ91JtgdfGkRlwEPcMAqKc/Vs8jiLVk3AaGoL9R4/YGjzMbGhbHwmTGAGBMbMLWBq7FTivd+31NecdBrQe8gGl9heLO9TRXDKOjXxtaPryo3lWQ2NXhqTRXTyUdWg34moFgE3UWbuQKBgQCSteEAb0Q6yn851YG6Bn5q9SLVADo34agfVqyivpgwTGzzNcK0N/0dJxFukzWc7k2KrDO6F6IJYH1BviNJEKGwYhhM3SSDLcWKD5EUYAf8S5waZq/8LXiwBqaFSn+YO4fHtoEjH2xXtsBUJp9cK1pA3MD+cB35kc0ue7rjvJ7NeQKBgFM/B/Z46R4EJH3VapOez3c54bqt1LjDtaTz9uNkNQMerPKPIAn4aijG+L5kHG0/MhkZeDL6M3+KtPtbfs3lYjt1YUMkYc0LVqvEtoUc0f4kTrtEwLn28H3F/CeurZzW6hGQgnUOMKmfbAHpnckc8+Mg8SvniZ27hT0XHpznjVET";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    private  String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvXn7wShiSwGod3ZKogk/vRVaiH3hCyD7xRGPkCuumCAxDDOCtsgrQ+S1U5eeQrMcZc7P04l++jEfgpI1ob0u9LFNyHBQgd9ieqB9cQbWBDLP6PmwScUs1PFsiRz7GkjYRpoOZYjhqLuJfOpyc7ocQ8vb2/36nxroCMTCTxSnDsCwOK3D/Vfa4/64HWVaC30jZI7NMoj4iuDDYYMEqVFHlIChnpcMR8RwdFzXUP9Pm/pv8XDPrKCf8mup8jEjolmUjoPJa4tuN3K1NsM5jFmOklpbyIePrwpHZmuOiwQ49k6cFqMMifjvJlnjnOuyPsx8a13tMIydMek9KWKt+k7tQQIDAQAB";
    // 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 支付宝会悄悄的给我们发送一个请求，告诉我们支付成功的信息
    private  String notify_url = " http://nywwbn.natappfree.cc/payed/notify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //同步通知，支付成功，一般跳转到成功页
    private  String return_url = "http://member.gulimall.com/memberOrder.html";

    // 签名方式
    private  String sign_type = "RSA2";

    // 字符编码格式
    private  String charset = "utf-8";

    // 支付宝网关； https://openapi.alipaydev.com/gateway.do
    private  String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    public  String pay(PayVo vo) throws AlipayApiException {

        //AlipayClient alipayClient = new DefaultAlipayClient(AlipayTemplate.gatewayUrl, AlipayTemplate.app_id, AlipayTemplate.merchant_private_key, "json", AlipayTemplate.charset, AlipayTemplate.alipay_public_key, AlipayTemplate.sign_type);
        //1、根据支付宝的配置生成一个支付客户端
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
                app_id, merchant_private_key, "json",
                charset, alipay_public_key, sign_type);

        //2、创建一个支付请求 //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = vo.getOut_trade_no();
        //付款金额，必填
        String total_amount = vo.getTotal_amount();
        //订单名称，必填
        String subject = vo.getSubject();
        //商品描述，可空
        String body = vo.getBody();

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = alipayClient.pageExecute(alipayRequest).getBody();

        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
        System.out.println("支付宝的响应："+result);

        return result;

    }
}
