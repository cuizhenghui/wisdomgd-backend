package com.think.business.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @Description: web项目ajax异步或者httpClient请求返回结果
 * @date 2018年12月28日
 */
@Data
@AllArgsConstructor
public class JsonResponse {
	/**
	 * 业务返回码 默认 0 表示成功
	 */
	private String code = "0";
	/**
	 * 返回消息
	 */
	private String message = "操作成功";
	/**
	 * 返回数据体
	 */
	private Object data;

	public static JsonResponse response() {
		return response(null);
	}

	/**
	 * Ajax返回默认成功对象
	 * @param data
	 * @return
	 */
	public static JsonResponse response(Object data) {
		return new JsonResponse("0", "操作成功", data);
	}

	/**
	 * Ajax返回失败对象
	 * @param message
	 * @return
	 */
	public static JsonResponse responseFailure(String message) {
		return new JsonResponse("1", message, null);
	}
}
