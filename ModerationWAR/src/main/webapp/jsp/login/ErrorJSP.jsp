<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Moderation</title>
	<script type="text/javascript" src="js/application.js"></script>
	<script type="text/javascript" src="js/jquery-1.4.3.min.js"></script>
	<link rel="stylesheet" href="css/application.css" type="text/css"/>
	
	<script type="text/javascript">

		$(document).ready(function() {
		    // check name availability on focus lost
		    $('#passwordField').blur(function() {		   
		        encryptPassword();
		    });
		});
	
		function encryptPassword() {	
	
	       	var requestUrlAjax = "encryptionAjaxController_encryptPassword.da";
	
	       	var sendedData = {
	       			password : $('#passwordField').val()
	        };      
	
	        $.ajax({
	    	    type: "GET",
	    	    url: requestUrlAjax,
	    	    data : sendedData,
	    	    dataType: "text",
	    	    success: function(data) {
	       	    			$("#j_password").val(data); 
	       	    		 }
	       	});  		
	
		}
		
	</script>
	
</head>

<body>


<form name="form" method="POST">

<input type="hidden" id="j_password" name="j_password"/>

<center>

	<table class="mainTable">
		<tr class="headingRow" >
			<td align="center" class="mainTableCell">
				
					<table class="innerTable">
						<tr class="headingRowUp" >
							<td>
								<font color="red"> ${loginErrorMessage} </font>
							</td>
						</tr>				
						<tr class="headingRowBottom">
							<td></td>
						</tr>				
					</table>					
			
			</td>
		</tr>
		
		
		
		<tr class="mainTableRowSpace">
			<td></td>
		</tr>
		
		
		
		<tr>
			<td align="center" class="mainTableCell">
				
				<table border="0">
					<tr>
						<td><label for="lang"> ${language} </label>:</td>
						<td>
							<select name="lang" onchange="send('loginController_init.da');">
			    				<c:forEach var="entry" items="${languageList}">
			      					<c:choose>
			      						<c:when test="${entry.selected}">
			      							<option value="${entry.value}" selected="selected"> ${entry.label}</option>
			      						</c:when>
			      						<c:otherwise>
			      							<option value="${entry.value}"> ${entry.label}</option>
			      						</c:otherwise>
			      					</c:choose>      					
			    				</c:forEach>
			  				</select>
						</td>
					</tr>
					<tr>
						<td><label for="loginfield"> ${login} </label>:</td>
						<td><input id="loginfield" type="text" name="j_username" /></td>
					</tr>
					<tr>
						<td><label for="passwordfield"> ${password} </label>:</td>
						<td><input type="password" id="passwordField" name="passwordField"/></td>
					</tr>					
					<tr>
						<td>&nbsp;</td>
						<td><input type="button" id="login" name="login" value="${loginButton}"
							onclick="send('j_security_check');" /></td>
					</tr>
				</table>
				
			</td>
		</tr>
	</table>

</center>



</form>


</body>

</html>