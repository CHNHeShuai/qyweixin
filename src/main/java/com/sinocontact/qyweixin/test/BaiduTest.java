package com.sinocontact.qyweixin.test;

import com.baidu.aip.face.AipFace;
import com.sinocontact.qyweixin.util.Base64Util;
import com.sinocontact.qyweixin.util.FileUtil;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author Jerry
 * @description
 * @since 2020/4/8 14:32
 */
public class BaiduTest {
    //设置APPID/AK/SK
    public static final String APP_ID = "19320698";
    public static final String API_KEY = "7sZziR9HopbxPygGBW8VE7lc";
    public static final String SECRET_KEY = "1MfnVV3KCwUx6FTx0cG0GS1vCeCacsqE";

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        // 初始化一个AipFace
        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        sample(client);
        System.out.println((System.currentTimeMillis()-start));
    }

    public static void sample(AipFace client) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");
        String image = null;
        try {
//            image = "https://ata-chuangxin.oss-cn-beijing.aliyuncs.com/gz_sm_system/gz_sm_system_test/c05f7f75-0c25-422e-956c-174e89ef2609.png";
            image = Base64Util.encode(FileUtil.readFileByBytes("F://by.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String imageType = "BASE64";
//        String imageType = "URL";
        String idCardNumber = "340321199603281794";
        String name = "孙乃梦";

        // 身份验证
        JSONObject res = client.personVerify(image, imageType, idCardNumber, name, options);
        System.out.println(res.toString(2));

    }

    //正面  301k  2.7s  相似度:91.796

    //闭眼  355k  1.6s  匹配不成功

    //侧脸  214k  1.8s  相似度:93.54

    //戴口罩 381k  1.6s  匹配不成功

    //倒立  288k   8.78s  相似度:100

    //横屏  290k   5.4s  相似度:100
}
