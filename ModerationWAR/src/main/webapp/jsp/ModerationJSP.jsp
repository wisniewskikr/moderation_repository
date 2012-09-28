<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<rapid:override name="content">

<spring:message var="next" code="button.next"/>

<input type="hidden" id="idTextModeration" name="idTextModeration" value="${command.idTextModeration}"/>

	<table class="innerTableBody">
		<tr>
			<td><textarea id="textContent" name="textContent" rows="15"
				cols="70" readonly="readonly">${command.textContent}</textarea></td>
		</tr>
		<tr>
			<td><input type="radio" id="result" name="result" value="OK"> <spring:message code="page.moderation.ok"/> </input>
			<input type="radio" id="result" name="result" value="PROBLEM"> <spring:message code="page.moderation.problem"/> </input>
			<input type="radio" id="result" name="result" value="WRONG"> <spring:message code="page.moderation.wrong"/> </input>

			</td>
		</tr>
		<tr>
			<td><input type="button" id="next" name="next" value="${next}"
				onclick="send('moderationController_next.do');" /></td>
		</tr>
	</table>

</rapid:override>



<%@ include file="templates/TemplateJSP.jsp" %> 