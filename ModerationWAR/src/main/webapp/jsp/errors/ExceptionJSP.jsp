<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid"%>



<rapid:override name="content">

<%@ page isErrorPage="true" %>
<%@page import="java.io.PrintWriter"%>

	<table class="innerTableBody">
		<tr>
			<td>

				<div><b>ERROR FOUND</b></div>
				<br/>
				<div><b>Error name</b>: <%= exception %> </div>
				<br/>
				<div><b>Error description</b>: <% exception.printStackTrace(new PrintWriter(out)); %></div>
				
			</td>
		</tr>
	</table>

</rapid:override>



<%@ include file="../templates/TemplateJSP.jsp"%>