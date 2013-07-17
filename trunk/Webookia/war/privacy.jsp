<%@page import="it.webookia.backend.model.Feedback"%>
<%@page import="java.util.List"%>
<%@page import="it.webookia.backend.descriptor.SingleFeedbackDescriptor"%>
<%@page import="it.webookia.backend.utils.storage.PermissionManager"%>
<%@page import="it.webookia.backend.controller.services.Users"%>
<%@page import="it.webookia.backend.controller.resources.LoanResource"%>
<%@page import="it.webookia.backend.descriptor.BookDescriptor"%>
<%@page import="it.webookia.backend.controller.services.Books"%>
<%@page import="it.webookia.backend.model.ConcreteBook"%>
<%@page import="it.webookia.backend.descriptor.ListDescriptor"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="shared/head.jsp"%>
<body class="webookia">
	<%@ include file="shared/header.jsp"%>
	<div id="contentContainer">
		<div id="userUI" class="content topWidthElement">
			<h1 class="sectionTitle">Normativa sulla privacy</h1>
			<div class="sectionContent">
				<div class="welcomeText left">
					Acconsentendo i termini di utilizzo di WeBookia verranno forniti i
					permessi necessari per accedere ai dati riguardanti la tua
					citt&agrave; attuale e i tuoi amici. <br> Solo in questo modo
					sar&agrave; infatti possibile trovare il libro a te pi&ugrave;
					vicino e/o filtrare le ricerche solo tra gli utenti appartenenti
					alla tua lista di contatti.
				</div>
				<img class="welcomeImage right" src="/resources/snoopy.png">
			</div>

		</div>
	</div>
</body>
</html>