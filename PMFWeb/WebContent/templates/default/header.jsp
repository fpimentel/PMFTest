<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.pmf.commons.Constants"%>
<s:set name="user" value="USER" scope="session"/>
<table id="tableHeader" border="0">
	<tr>
	<td width="30%" valign="top" align="left">
		<h2>BuscaBola.com.do</h2>
	</td>
	<td width="70%" valign="top" height="5px">
		<div id="headerLinks">
		<s:if test="#session.USER == null">
			<a href="<%=request.getContextPath()%>/user/signup.do">Registrate</a> | <a href="<%=request.getContextPath()%>/user/login.do">Iniciar Sesi&oacute;n</a>
		</s:if>
		<s:if test="#session.USER != null">
			<a href="<%=request.getContextPath()%>/user/profile.do"><s:property value="#session.USER.getFirstName()" />&nbsp;<s:property value="#session.USER.getLastName()" /></a>
			(<a href="<%=request.getContextPath()%>/user/logout.do" >Salir</a>)
		</s:if>
		</div>
	</td>
	</tr>
</table>