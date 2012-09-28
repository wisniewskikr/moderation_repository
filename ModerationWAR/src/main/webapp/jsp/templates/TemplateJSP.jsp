<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %> 

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Moderation</title>
	<script type="text/javascript" src="js/application.js"></script>
	<script type="text/javascript" src="js/jquery-1.4.3.min.js"></script>
	<link rel="stylesheet" href="css/application.css" type="text/css"/>
</head>

<body>
<form name="form" method="post">

<center>

	<table class="mainTable">
		<tr class="headingRow" >
			<td align="center" class="mainTableCell">
				
					<table class="innerTable">
						<tr class="headingRowUp" >
							<td colspan="2">
							
								<spring:hasBindErrors name="command">
								    <c:forEach items="${errors.fieldErrors}" var="error">
								      <font color="red"> <spring:message code="${error.defaultMessage}"/> </font><br/>
								    </c:forEach>
								</spring:hasBindErrors>
							
							</td>
						</tr>				
						<tr class="headingRowBottom">
							<td style="text-align: left;"><spring:message code="template.application_version"/>: <b>${dbVersion}</b> / <b>${applicationVersion}</b></td>
							<td>
							
								<spring:message code="template.login"/>: <b><%= request.getUserPrincipal().getName() %></b> | <a href="loginController_logout.do?type=${command.moderationType}&id=${command.moderationObjectId}"> <spring:message code="template.logout"/> </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								
							</td>
						</tr>				
					</table>					
			
			</td>
		</tr>
		
		
		
		<tr class="mainTableRowSpace">
			<td></td>
		</tr>
		
		
		
		<tr>
			<td align="center" class="mainTableCell">
				
				<rapid:block name="content">
  					base_body_content
 				</rapid:block>
				
			</td>
		</tr>
	</table>

</center>

</form>
</body>

</html>