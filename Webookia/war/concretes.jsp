<%@page import="it.webookia.backend.controller.services.SearchContainer"%>
<%@page
	import="it.webookia.backend.controller.services.SearchContainer.ConcreteResultBox"%>
<%@page import="java.util.List"%>
<%@page import="it.webookia.backend.descriptor.BookDescriptor"%>
<%@page import="it.webookia.backend.controller.services.Books"%>
<%@page import="it.webookia.backend.model.ConcreteBook"%>
<%@page import="it.webookia.backend.descriptor.ListDescriptor"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="shared/commons.jsp"%>

<%
	String userId = ServletUtils.getAuthenticatedUserId(request);
	UserDescriptor viewer = UserResource.getUser(userId)
			.getDescriptor();
	ConcreteResultBox box = ServletUtils.getRequestAttribute(request,
			ConcreteResultBox.class, SearchContainer.CONCRETE_RESULTS);
	List<BookDescriptor> results = box.getList();
%>

<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="shared/head.jsp"%>
<body class="webookia">
	<%@ include file="shared/header.jsp"%>
	<div id="contentContainer">
		<div id="content" class="topWidthElement">
			<div class="selfCoords hidden"><%=viewer.getLocation()%></div>
			<!-- Search -->
			<div class="contentSection">
				<h1 class="sectionTitle">Libri disponibili</h1>
				<div class="sectionContent">
					<%
						if (results.isEmpty()) {
					%>
					<div class="empty">Non sono presenti copie disponibili per il
						prestito.</div>
					<%
						} else {
							BookDescriptor example = results.get(0);
					%>
					<p class="paragraph">
						Stai cercando copie di "<%=example.getTitle()%>". I seguenti
						utenti ne hanno una, selezionane uno per chiedere in prestito!
					</p>
					<div id="concreteDisplay" class="clearfix">
						<div id="concreteList">
							<%
								for (BookDescriptor book : results) {
										UserDescriptor owner = UserResource.getUser(
												book.getOwnerId()).getDescriptor();
							%>
							<div class="concrete animate clearfix">
								<div class="userThumb">
									<img class="profilePicture" src="<%=owner.getThumbnail()%>">
								</div>
								<div class="concreteDetail">
									<div class="name"><%=owner.getFullName()%></div>
									<div class="location"><%=owner.getLocation().getName()%></div>
									<div class="coords hidden"><%=owner.getLocation()%></div>
									<div class="id hidden"><%=book.getId()%></div>
								</div>
							</div>
							<%
								}
							%>
							<div class="concretePopup">
								<div class="arrow">&nbsp;</div>
								<div class="text">
									Vuoi chiedere il libro in prestito a <span class="name"></span>?
								</div>
								<div class="buttons">
									<a class="button yes" href="#">Si</a> <a class="button no"
										href="#">No</a>
								</div>
							</div>
						</div>
						<div id="map"></div>
					</div>
					<%
						}
					%>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="shared/footer.jsp"%>
</body>
</html>