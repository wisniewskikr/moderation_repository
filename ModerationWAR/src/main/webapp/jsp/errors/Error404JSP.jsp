<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid"%>



<rapid:override name="content">

<%@ page isErrorPage="true" %>

	<table class="innerTableBody">
		<tr>
			<td>

				<div><b>ERROR FOUND</b></div>
				<br/>
				<div><b>Error name</b>: Resource not found</div>
				<br/>
				<div><b>Error description</b>: Could not find resource '<%= request.getAttribute("javax.servlet.error.message").toString() %>'</div>
				
			</td>
		</tr>
	</table>

</rapid:override>



<%@ include file="../templates/TemplateJSP.jsp"%>