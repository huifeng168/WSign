package com.connsec.domain;

import javax.servlet.http.HttpServletRequest;

import com.connsec.web.WebConstants;

public class SignTarget {

	String relayState;
	String target;
	String wsign;

	public SignTarget(HttpServletRequest request) {
		target = request.getParameter(WebConstants.SINGLE_SIGN_ON_TARGET);
		wsign = request.getParameter(WebConstants.SINGLE_SIGN_ON_WSIGN);
		relayState = request.getParameter(WebConstants.SINGLE_SIGN_ON_RELAYSTATE);
		
		if(target==null){
			target="";
		}
		if(wsign==null){
			wsign="";
		}
		if(relayState==null){
			relayState="";
		}
	}

	public String getRelayState() {
		return relayState;
	}

	public String getTarget() {
		return target;
	}

	public String getWsign() {
		return wsign;
	}

	public boolean equals(SignTarget obj) {
		if(obj==null){
			return false;
		}
		
		if (this.getTarget().equals(obj.getTarget())
				&& this.getWsign().equals(obj.getWsign())
				&& this.getRelayState().equals(obj.getRelayState())) {
			return true;
		} else {
			return false;
		}

	}
	
	public boolean validated(){
		if(this.getTarget()!=null&&!this.getTarget().equals("")
				&&this.getWsign()!=null&&!this.getWsign().equals("")){
			return true;
		}
		return false;
	}
	
	public String toParameter() {
		String parameterString = WebConstants.SINGLE_SIGN_ON_TARGET+"=" + target 
								+ "&"+WebConstants.SINGLE_SIGN_ON_WSIGN+"=" + wsign;
		if(relayState!=null && !relayState.equals("")){
			parameterString +="&"+WebConstants.SINGLE_SIGN_ON_RELAYSTATE+"=" + relayState;
		}
		return parameterString;
				
	}
	
	
	@Override
	public String toString() {
		return "SignTarget [relayState=" + relayState + ", target=" + target
				+ ", wsign=" + wsign + "]";
	}
	


}
