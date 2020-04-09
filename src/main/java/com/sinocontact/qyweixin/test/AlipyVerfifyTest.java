package com.sinocontact.qyweixin.test;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayUserCertifyOpenInitializeRequest;
import com.alipay.api.response.AlipayUserCertifyOpenInitializeResponse;

/**
 * @author Jerry
 * @description 支付宝身份验证测试
 * @since 2020/4/7 18:19
 */
public class AlipyVerfifyTest {
    public static void main(String[] args) {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do","app_id","your private_key","json","GBK","alipay_public_key","RSA2");
        AlipayUserCertifyOpenInitializeRequest request = new AlipayUserCertifyOpenInitializeRequest();
        request.setBizContent("{outer_order_no:ZGYD201809132323000001234,biz_code:FACE,identity_param:}");
        AlipayUserCertifyOpenInitializeResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }
}
