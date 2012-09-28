<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<rapid:override name="content">

	<spring:message var="refresh" code="button.refresh"/>

	<script type="text/javascript"> 
		var counter = 20;
	    function refreshCounter(){
			if(counter == 0){
				send('moderationController_refresh.do');
			}		    
			$("#counter").html(counter--);
		}
		window.onload = function() {setInterval("refreshCounter()", 1000)};		
	</script>

	<table class="innerTableBody">
		<tr>
			<td>

				<div> <spring:message code="page.moderationEmptyDb.sentence1"/> <br />
				<spring:message code="page.moderationEmptyDb.sentence2"/> <br />
				</div>
				<br />
				<div> <spring:message code="page.moderationEmptyDb.autorefreshing"/>: <b> <span id="counter"></span></b> <spring:message code="page.moderationEmptyDb.seconds"/>.</div>
				<br />
				<br />
				<input type="button" id="refresh" name="refresh" value="${refresh}" onclick="send('moderationController_refresh.do');" />
				
			</td>
		</tr>
	</table>

</rapid:override>



<%@ include file="templates/TemplateJSP.jsp"%>