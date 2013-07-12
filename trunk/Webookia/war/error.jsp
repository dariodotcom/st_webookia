<%@page import="java.util.Date"%>
<%@page
	import="it.webookia.backend.controller.resources.exception.ResourceException"%>
<%@page import="it.webookia.backend.descriptor.BookDescriptor"%>
<%@page import="it.webookia.backend.controller.services.Books"%>
<%@page import="it.webookia.backend.model.ConcreteBook"%>
<%@page import="it.webookia.backend.descriptor.ListDescriptor"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	ResourceException ex = ServletUtils.getRequestAttribute(request,
			ResourceException.class, ServiceServlet.ERROR);

	String errorText;

	switch (ex.getErrorType()) {
	case NOT_LOGGED_IN:
		errorText = "Per proseguire devi effettuare l'autenticazione. "
				+ "Premi il pulsante \"Login\" in alto a destra per autenticarti.";
		break;
	case NOT_FOUND:
		errorText = "La risorsa richiesta non è stata trovata. Torna alla home e riprova.";
		break;
	case RESOURCE_UNAVAILABLE:
		errorText = "La risorsa richiesta non è al momento disponibile. Riprova più tardi.";
		break;
	case UNAUTHORIZED_ACTION:
		errorText = "Non sei autorizzato ad accedere alla risorsa richiesta.";
		break;
	case SERVER_FAULT:
		errorText = "Il servizio ha incontrato alcune difficolt&agrave;. Riprova più tardi.";
		break;
	case ALREADY_EXSISTING:
		errorText = "Una risorsa come quella di cui hai richiesto la creazione esiste già.";
		break;
	default:
		errorText = "La tua richiesta non è valida. Torna alla home e riprova";
	}
%>

<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="shared/head.jsp"%>
<body class="webookia">
	<%@ include file="shared/header.jsp"%>
	<div id="contentContainer">
		<div id="content" class="topWidthElement">
			<div class="errorNotificationContainer shadowBox">
				<div class="errorHeading">C'&egrave; stato un errore.</div>
				<div class="errorBody"><%=errorText%></div>
			</div>

			<%
				if (Settings.DEBUG_MODE) {
			%>
			<div class="contentSection">
				<h1 class="sectionTitle">Stack dell'errore:</h1>
				<%
					Throwable cause = ex;
						boolean isCause = false;
						while (cause != null) {
							StackTraceElement[] stackTrace = cause.getStackTrace();
							String elemClass = "stackTrace" + (isCause ? " cause" : "");
							String text = (isCause ? "Caused by " : "")
									+ cause.toString();
				%>
				<pre class="<%=elemClass%>"><%=text%></pre>
				<br>
				<%
					for (StackTraceElement elem : stackTrace) {
				%>
				<pre class="stackTrace"><%="\tat " + elem.toString()%></pre>
				<br>
				<%
					}
							cause = cause.getCause();
							isCause = true;
						}
				%>
			</div>
			<%
				}
			%>
		</div>
	</div>
		<%@ include file="shared/footer.jsp"%>
</body>
</html>