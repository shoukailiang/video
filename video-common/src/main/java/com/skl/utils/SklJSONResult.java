package com.skl.utils;

import lombok.Data;

/**
 * @Description: 自定义响应数据结构
 * 这个类是提供给门户，ios，安卓，微信商城用的
 * 门户接受此类数据后需要使用本类的方法转换成对于的数据类型格式（类，或者list）
 * 其他自行处理
 * 200：表示成功
 * 500：表示错误，错误信息在msg字段中
 * 501：bean验证错误，不管多少个错误都以map形式返回
 * 502：拦截器拦截到用户token出错
 * 555：异常抛出信息
 */
@Data
public class SklJSONResult {
  // 响应业务状态
  private Integer status;

  // 响应消息
  private String msg;

  // 响应中的数据
  private Object data;

  private String ok;  // 不使用

  public static SklJSONResult build(Integer status, String msg, Object data) {
    return new SklJSONResult(status, msg, data);
  }

  public static SklJSONResult ok(Object data) {
    return new SklJSONResult(data);
  }

  public static SklJSONResult ok() {
    return new SklJSONResult(null);
  }

  public static SklJSONResult errorMsg(String msg) {
    return new SklJSONResult(500, msg, null);
  }

  public static SklJSONResult errorMap(Object data) {
    return new SklJSONResult(501, "error", data);
  }

  public static SklJSONResult errorTokenMsg(String msg) {
    return new SklJSONResult(502, msg, null);
  }

  public static SklJSONResult errorException(String msg) {
    return new SklJSONResult(555, msg, null);
  }

  public SklJSONResult() {

  }

  public SklJSONResult(Integer status, String msg, Object data) {
    this.status = status;
    this.msg = msg;
    this.data = data;
  }

  public SklJSONResult(Object data) {
    this.status = 200;
    this.msg = "OK";
    this.data = data;
  }

  public Boolean isOK() {
    return this.status == 200;
  }

}
