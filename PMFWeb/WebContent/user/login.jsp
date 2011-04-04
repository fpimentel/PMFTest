<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <link type="text/css" href="<%=request.getContextPath()%>/css/openid.css" rel="stylesheet" />
    <link type="text/css" href="<%=request.getContextPath()%>/css/openid-shadow.css" rel="stylesheet" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/thirdparty/openid-jquery.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/thirdparty/openid-en.js"></script>
<script language="javascript">
$(document).ready(function() {
	openid.init('openid_identifier');
	openid.setDemoMode(false); //Stops form submission for client javascript-only test purposes
	$("#loginDiv").corner();
});
</script>
	<s:actionerror theme="css_xhtml" />
	<s:actionmessage theme="css_xhtml" />
		
	<s:form action="doLogin" method="GET" theme="css_xhtml" cssClass="topSpace" id="openid_form">
 <div id="loginDiv" align="left">
 		<div id="infoMessage" class="ui-widget biggest" align="left">
 		<br />
		<br />
		<div class="ui-state-highlight ui-corner-all" style="width:560px; padding: 0pt 0.7em;">
		<input type="hidden" name="action" value="verify" /> 
			<div id="openid_choice"> 
				<p>Favor seleccionar el proveedor de su cuenta:</p> 
				<div id="openid_btns"></div> 
			</div> 
			<div id="openid_input_area"> 
				<s:textfield name="openidIdentifier" id="openid_identifier" value="http://" />
				<input id="openid_submit" type="submit" value="Iniciar Sesi&oacute;n"/> 
			</div> 
<%

/*

	
		<h3>Iniciar sesi&oacute;n</h3>
		<table class="normalTable" width="300" cellspacing="0" cellpadding="3">
		<tr>
			<td width="150">Nombre de Usuario : </td>
			<td width="150">
				<s:textfield name="login" /><br />
			</td>
		</tr>
		<tr>
			<td>Contrase&ntilde;a : </td>
			<td>
				<s:password name="password"/>
			</td>
		</tr>
		<tr>
			<td align="right">
				<s:checkbox name="remember"/>
			</td>
		
			<td>Recordarme</td>
		
		</tr>
		<tr> 
			<td align="center">
				<button aria-disabled="false" role="button" class="ui-button ui-widget ui-state-default ui-priority-primary ui-corner-all ui-button-text-only" id="button"><span class="ui-button-text">Iniciar Sesi&oacute;n</span></button>
			</td>
			<td align="center">
				&nbsp;<button onClick="return false" aria-disabled="false" role="button" class="ui-button ui-widget ui-state-default ui-priority-secondary ui-corner-all ui-button-text-only" id="button"><span class="ui-button-text">Cancelar</span></button>
			</td>
		</table>
		*/
		%>
	</div>
	</div>
	</div>

	</s:form>
