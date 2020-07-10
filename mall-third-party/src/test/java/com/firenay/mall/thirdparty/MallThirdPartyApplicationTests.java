package com.firenay.mall.thirdparty;

import com.aliyun.oss.OSSClient;
import com.firenay.common.utils.HttpUtils;
import com.firenay.mall.thirdparty.component.SmsComponent;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 第三方服务上传文件
 */
@SpringBootTest
class MallThirdPartyApplicationTests {

	@Resource
	private OSSClient ossClient;

	@Resource
	private SmsComponent smsComponent;

	@Value("${spring.cloud.alicloud.oss.bucket}")
	private String bucketName;

	@Test
	public void SendCodeTest(){
		smsComponent.sendSmsCode("18173516309","666666");
	}

	@Test
	public void sendSms(){
		String host = "https://fesms.market.alicloudapi.com";
		String path = "/sms/";
		String method = "GET";
		String appcode = "541707ddc9c8463eb9336c3bfc0624b3";
		Map<String, String> headers = new HashMap<String, String>();
		//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("code", "666666");
		querys.put("phone", "18173516309");
		querys.put("skin", "1");
		querys.put("sign", "1");
		//JDK 1.8示例代码请在这里下载：  http://code.fegine.com/Tools.zip
		try {
			HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
			//System.out.println(response.toString());如不输出json, 请打开这行代码，打印调试头部状态码。
			//获取response的body
			System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testFindPath() throws IOException {
		// 上传网络流。
		InputStream inputStream = new FileInputStream("C:\\Users\\root\\Desktop\\1.jpg");

		ossClient.putObject(bucketName, "1.jpg", inputStream);

		// 关闭OSSClient。
		ossClient.shutdown();
		System.out.println("上传成功");
	}
}
