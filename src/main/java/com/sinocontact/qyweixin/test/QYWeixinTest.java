package com.sinocontact.qyweixin.test;

import com.sinocontact.qyweixin.util.MyHttpUtils;
import com.sinocontact.qyweixin.util.PropertyUtils;

/**
 * @author Jerry
 * @description
 * @since 2020/4/7 13:37
 */
public class QYWeixinTest {


    private static String getAccessToken(String access_token_url,String corpid,String corpsecret){

        return MyHttpUtils.sendGet(access_token_url.replace("ID", corpid).replace("SECRET", corpsecret));
    }

    public static void main(String[] args) {
        String access_token_url = PropertyUtils.getProperty("access_token_url");
        String corpid = PropertyUtils.getProperty("corpid");
        String corpsecret = PropertyUtils.getProperty("corpsecret");
        String accessToken = getAccessToken(access_token_url, corpid, corpsecret);
        System.out.println(accessToken);
    }
}
