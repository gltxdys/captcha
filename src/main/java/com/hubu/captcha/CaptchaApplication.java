package com.hubu.captcha;

import com.google.gson.JsonObject;
import io.gsonfire.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import com.alibaba.fastjson.JSONObject;

@SpringBootApplication
@RestController
@RequestMapping("/a")
@Slf4j
public class CaptchaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaptchaApplication.class, args);
	}

	@PostMapping("/b")
	public String test(HttpServletRequest request) throws Exception {
		boolean success=alipayCallback(request);
		return success?"True":"False";
	}

	private boolean alipayCallback(HttpServletRequest request) throws Exception {
		// 获取支付宝GET过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			params.put(name, new String(valueStr.getBytes("ISO-8859-1"), "UTF-8"));
		}

		// 计算得出通知验证结果
		log.info("1：---->获取支付宝回传的参数---------------------------------->" + params);
		// 支付宝返回的请求参数body
		String extparamString = request.getParameter("extra_common_param");


		log.info("2---->：支付宝交易返回的参数：{}", extparamString);
		String tradeno = params.get("trade_no");;
		//交易完成
		String body = params.get("body");
		if (StringUtils.isEmpty(tradeno)) {
			tradeno = params.get("trade_no");
		}


		log.info("3---->:【支付宝】交易的参数信息是：{}，流水号是：{}", body, tradeno);
		try {
			JSONObject bodyJson = JSONObject.parseObject(body);
			String userId = bodyJson.getString("userId");
			String ptype = bodyJson.getString("ptype");
			String orderNumber = bodyJson.getString("orderNumber");
			log.info("4---->:【支付宝】交易的参数信息是：ptype:{}，orderNumber是：{}",  ptype,orderNumber);
			// 课程支付成功，保存的订单交易明细

		} catch (Exception ex) {
			log.info("支付宝支付出现了异常.....");
			ex.printStackTrace();
			return false;
		}
		return true;
	}

}
