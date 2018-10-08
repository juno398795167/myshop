package com.qf.shop_pay.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.qf.entity.Orders;
import com.qf.service.IOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Controller
@RequestMapping("/payController")
public class payController {
        @Reference
        IOrderService iOrderService;
        @RequestMapping("/alipay")
        public String alipay(String orderid,Model model) {
            // orders = iOrderService.queryOrdersByOrderId(orderid);
            model.addAttribute("orderid",orderid);
            return "topay";
        }

        @RequestMapping("/topay")
        public void pay(String orderid, HttpServletResponse response){
            Orders orders = iOrderService.queryOrdersByOrderId(orderid);
            AlipayClient alipayClient = new DefaultAlipayClient(
                    "https://openapi.alipaydev.com/gateway.do",
                    "2016092000558783",
                    "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCOVQHW4gb4GcVFLzCLrU+T0LesVm1U/yVZAZy+5uas9zVpjYaKebmmH/KDO2908pO9vn/U0f1MVVwF0/eWPltCyTD2XYvN7Y/E+fqB13SdvMv+MLVZuyeVdcx71GlZGgg1izYcuBDY6SS9RhpwoiVWLhf5cpmy4UOw8A1nBX2WPlDvpOVCXN9k6pZHZjCIKQZj+i45SvfEEm4s4FA5hCSOA4ZOPEznReOipkcrJoiwwd1+hL31JNjD5o5bLqrdh3G32DSEYJbg2gaPp8ZIjSmO17NnvChPnMabz38TO9CkP5puDzPbrKj7H9Gp2BR48dJo+3F1GA7pvunZzaI6JBvbAgMBAAECggEAVpSLS+Wui5lRkOZFx5GjiK08LY9dbojOdTgv20imJa0zRoQaxX2KWu2wHfg3spIlIRUSuakLRjXehoUMZM6FwTiVmigJdTj/bbkKk5U2kQVZqcSDbJQWXiqMOv9ZHiRhvgBy6EFnrisUeDZ+uulHgQcE+m6l5FR3UtiTrol+fbd+AybkdE9ibae4ej9IJnvlP7rktS11XsKBwSGL54PjQBLjvAwYwckplmY+sRFTopUQB3/7XkxEOqSjHPq+lwIbRX4GqGVdRSWtehjYTQh6/GjlrTOOoWp+DVHREBmI0zwPjoJQ5WzzohCBcFB5fyTBok23ymU6xS7f0huOA7riMQKBgQDJJpbivOUk6nqyTz543yfed3W1LugA0lY5Ea/IbyjI9UXm6JFADztF+HryA2mwxbkpaz9SeZmqkByGf7L8mOIH9ncaokdP1exKEVNw/lPVT22nGWGR6BYFvLRBiutsvlvKfKtcKz4mZhmPnhYReMYWxVQkNIaiOddhIXcop1M5RQKBgQC1JI4y14kgQKfFB90cGBisWcj2O+HtX/RuT7WNRIIZrHQv/aC+seKnBGsUEjm5d8a2R9m2cg4WBqCm67Ei29RqsgybG2QuH7pv8AocW4tOVY5uiq613RJFRX3pLhaDfKYBrihYOUz+70I2YRMakfACBmuofQN9Iup+jhAEbI0CnwKBgBZrx7vERGJmBIEllhgQ/7erZyJn9RJKX7QCxa44vboQpNcFK1ORnxDAjqu1N62tjGMSgj9mMHuzjoprZ38CZ4dtsH72ga5jcPAcmElq/9sx/E345UhHL1U6YHlBy9YWLdMXLF6cr1PGY6j/fQQ7ixD0s9lJRYyFl/1KpqaQaBVlAoGAZXq8OxlqleoC0yvroDCTqgKGxgOYN26ZNi9ri1/E7vMhCD2zjwGC84RTJKRtOE00wquP+Vj+MTK3T6wcyWWUI83pp/95aSmXPdyhHW/fpBvkNmN1KydVc/9RYY/TRwhOzAQwT/SSAQQy+Nn7ZNg7ODc2muamTLWB3zJhIMlbWgsCgYEAn7mF8/HFNBvgEGMjWfAUZWFChFblqQo0hdC3CotHCHLdcrB8dYGhhGTrmdnQEzjyyL+wJyDKDI6OpMbmv0KHV1Ki6mm145sRQ6zmqAkjynyXn+XEL7Kq58CwDUvdRQiBx7WZX8zoozWhBApa0WUog9uR8GvGUcP3TcGVUd+pM0E=",
                    "json",
                    "utf-8",
                    "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApirR1uUl/uCKrqzmCrgJtdMEdwLq+5M91Awu5osSG40jyOTUKC+OlBTZ3SeIsiEfDrVfIy1sf9y8sc044Wrt6obxCutLOliwG0pK1QCu86IMHb+1xrDxDBzlcGGF/c2jiQhtNBxxlDNQa2NfaQb4Extxa0ONzTSmG7WKmkPObMZpkQYnz/BE8Uuc8/ZMARISTL3OYTLShbyYPd5de/qBPHPfs1tkgwLiRXadexMNYkAMzlRC7m+JmcrJmoIpqAnb28HxnZsQTZ6WXYGJM6CTa1XS4GUWzjwxyA/IK6E1fy2+i+ia2Nslj4eQpLMUljedwu90vbwaupJHoRL2JzkuLwIDAQAB",
                    "RSA2"); //获得初始化的AlipayClient
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
            alipayRequest.setReturnUrl("http://www.baidu.com");
            alipayRequest.setNotifyUrl("http://www.baidu.com");//在公共参数中设置回跳和通知地址
            alipayRequest.setBizContent("{" +
                    "    \"out_trade_no\":\""+orders.getOrderid() +"\"," +
                    "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                    "    \"total_amount\":"+orders.getOprice()+"," +
                    "    \"subject\":\"Iphone6 16G\"," +
                    "    \"body\":\"Iphone6 16G\"," +
                    "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                    "    \"extend_params\":{" +
                    "    \"sys_service_provider_id\":\"2088511833207846\"" +
                    "    }"+
                    "  }");//填充业务参数
            String form="";
            try {
                form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }

            try {
                response.setContentType("text/html;charset=utf-8" );
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                response.getWriter().write(form);//直接将完整的表单html输出到页面
                response.getWriter().flush();
                response.getWriter().close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        @RequestMapping("/isok")
        public String isok(String orderid){
            System.out.println("订单号："+orderid);
            AlipayClient alipayClient = new DefaultAlipayClient(
                    "https://openapi.alipaydev.com/gateway.do",
                    //"https://openapi.alipay.com/gateway.do",
                    "2016092000558783",
                    "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCOVQHW4gb4GcVFLzCLrU+T0LesVm1U/yVZAZy+5uas9zVpjYaKebmmH/KDO2908pO9vn/U0f1MVVwF0/eWPltCyTD2XYvN7Y/E+fqB13SdvMv+MLVZuyeVdcx71GlZGgg1izYcuBDY6SS9RhpwoiVWLhf5cpmy4UOw8A1nBX2WPlDvpOVCXN9k6pZHZjCIKQZj+i45SvfEEm4s4FA5hCSOA4ZOPEznReOipkcrJoiwwd1+hL31JNjD5o5bLqrdh3G32DSEYJbg2gaPp8ZIjSmO17NnvChPnMabz38TO9CkP5puDzPbrKj7H9Gp2BR48dJo+3F1GA7pvunZzaI6JBvbAgMBAAECggEAVpSLS+Wui5lRkOZFx5GjiK08LY9dbojOdTgv20imJa0zRoQaxX2KWu2wHfg3spIlIRUSuakLRjXehoUMZM6FwTiVmigJdTj/bbkKk5U2kQVZqcSDbJQWXiqMOv9ZHiRhvgBy6EFnrisUeDZ+uulHgQcE+m6l5FR3UtiTrol+fbd+AybkdE9ibae4ej9IJnvlP7rktS11XsKBwSGL54PjQBLjvAwYwckplmY+sRFTopUQB3/7XkxEOqSjHPq+lwIbRX4GqGVdRSWtehjYTQh6/GjlrTOOoWp+DVHREBmI0zwPjoJQ5WzzohCBcFB5fyTBok23ymU6xS7f0huOA7riMQKBgQDJJpbivOUk6nqyTz543yfed3W1LugA0lY5Ea/IbyjI9UXm6JFADztF+HryA2mwxbkpaz9SeZmqkByGf7L8mOIH9ncaokdP1exKEVNw/lPVT22nGWGR6BYFvLRBiutsvlvKfKtcKz4mZhmPnhYReMYWxVQkNIaiOddhIXcop1M5RQKBgQC1JI4y14kgQKfFB90cGBisWcj2O+HtX/RuT7WNRIIZrHQv/aC+seKnBGsUEjm5d8a2R9m2cg4WBqCm67Ei29RqsgybG2QuH7pv8AocW4tOVY5uiq613RJFRX3pLhaDfKYBrihYOUz+70I2YRMakfACBmuofQN9Iup+jhAEbI0CnwKBgBZrx7vERGJmBIEllhgQ/7erZyJn9RJKX7QCxa44vboQpNcFK1ORnxDAjqu1N62tjGMSgj9mMHuzjoprZ38CZ4dtsH72ga5jcPAcmElq/9sx/E345UhHL1U6YHlBy9YWLdMXLF6cr1PGY6j/fQQ7ixD0s9lJRYyFl/1KpqaQaBVlAoGAZXq8OxlqleoC0yvroDCTqgKGxgOYN26ZNi9ri1/E7vMhCD2zjwGC84RTJKRtOE00wquP+Vj+MTK3T6wcyWWUI83pp/95aSmXPdyhHW/fpBvkNmN1KydVc/9RYY/TRwhOzAQwT/SSAQQy+Nn7ZNg7ODc2muamTLWB3zJhIMlbWgsCgYEAn7mF8/HFNBvgEGMjWfAUZWFChFblqQo0hdC3CotHCHLdcrB8dYGhhGTrmdnQEzjyyL+wJyDKDI6OpMbmv0KHV1Ki6mm145sRQ6zmqAkjynyXn+XEL7Kq58CwDUvdRQiBx7WZX8zoozWhBApa0WUog9uR8GvGUcP3TcGVUd+pM0E=",
                    "json",
                    "utf-8",
                    "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApirR1uUl/uCKrqzmCrgJtdMEdwLq+5M91Awu5osSG40jyOTUKC+OlBTZ3SeIsiEfDrVfIy1sf9y8sc044Wrt6obxCutLOliwG0pK1QCu86IMHb+1xrDxDBzlcGGF/c2jiQhtNBxxlDNQa2NfaQb4Extxa0ONzTSmG7WKmkPObMZpkQYnz/BE8Uuc8/ZMARISTL3OYTLShbyYPd5de/qBPHPfs1tkgwLiRXadexMNYkAMzlRC7m+JmcrJmoIpqAnb28HxnZsQTZ6WXYGJM6CTa1XS4GUWzjwxyA/IK6E1fy2+i+ia2Nslj4eQpLMUljedwu90vbwaupJHoRL2JzkuLwIDAQAB",
                    "RSA2"); //获得初始化的AlipayClient
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            request.setBizContent("{" +
                    "\"out_trade_no\":\""+orderid+"\"" +

                    "  }");
            AlipayTradeQueryResponse response = null;
            try {
                response = alipayClient.execute(request);
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
            System.out.println(response);
            if(response.isSuccess()){
                System.out.println("调用成功");
                String tradeStatus = response.getTradeStatus();
                if("TRADE_SUCCESS".equals(tradeStatus)){
                    iOrderService.updataOrderByOrderidAndStatus(orderid,1);
                }
            } else {
                System.out.println("调用失败");
            }

            return "redirect:http://localhost:8087/orderController/queryOrderList";
        }
}
