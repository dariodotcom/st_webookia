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
			<h1 class="sectionTitle">Benvenuto su WeBookia!</h1>
			<div class="sectionContent">
				<div class="welcomeText left">
					WeBookia &egrave; una vera e propria biblioteca virtuale in cui
					potrai cercare online il libro che desideri ed ottenerlo in
					prestito dagli utenti della comunit&agrave; di WeBookia presenti o
					meno tra i tuoi contatti di Facebook.<br> Inoltre, sfruttando
					le funzionalit&agrave; offerte da Facebook e GoogleMaps WeBookia ti
					garantisce di trovare sempre la soluzione migliore per le tue
					esigenze. <br> Inizia subito a scambiare libri con WeBookia
					premendo il pulsante "Login" in alto a destra di questa pagina! <br>
					Per rimanere sempre aggiornato seguici sulla nostra pagina
					Facebook <a href="http://www.facebook.com/WebookiaCommunity">WeBookia Community</a>.
				</div>
				<img class="welcomeImage right" src="/resources/snoopy.png">
			</div>

		</div>
	</div>
</body>
</html>