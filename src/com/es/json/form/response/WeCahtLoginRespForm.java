package com.es.json.form.response;

public class WeCahtLoginRespForm {
	private String errcode = null;
	private String errmsg = null;
	private String openid = null;
	private String session_key = null;
	public String getErrcode() {
		return errcode;
	}
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getSession_key() {
		return session_key;
	}
	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}
	public WeCahtLoginRespForm(String errcode, String errmsg, String openid, String session_key) {
		super();
		this.errcode = errcode;
		this.errmsg = errmsg;
		this.openid = openid;
		this.session_key = session_key;
	}
	public WeCahtLoginRespForm() {
		super();
	}
	@Override
	public String toString() {
		return "WeCahtLoginRespForm [errcode=" + errcode + ", errmsg=" + errmsg + ", openid=" + openid
				+ ", session_key=" + session_key + "]";
	}
	
	
}
