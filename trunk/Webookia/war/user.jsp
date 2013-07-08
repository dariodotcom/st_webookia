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

<%
	UserResource userToShow = ServletUtils.getRequestAttribute(request,
			UserResource.class, Users.SHOW_USER);

	UserDescriptor descriptor = userToShow.getDescriptor();
	UserResource viewer = ServletUtils.getAuthenticatedUser(request);
%>


<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="shared/head.jsp"%>
<body class="webookia">
	<%@ include file="shared/header.jsp"%>
	<div id="contentContainer">
		<div id="userUI" class="content topWidthElement">
			<div class="contentSection">
				<h1 class="sectionTitle">Profilo utente</h1>
				<div class="sectionContent">
					<div class="details clearfix">
						<div class="pictureContainer left">
							<div class="userThumb big">
								<img class="profilePicture" src="<%=descriptor.getThumbnail()%>">
							</div>
						</div>
						<div class="detailContainer left">
							<!-- Details -->
							<div class="heading"><%=descriptor.getFullName()%></div>
							<div class="location">Lonate Pozzolo</div>
						</div>
					</div>
				</div>
			</div>

			<div class="tabbedContainer">
				<div class="tabs">
					<div class="tab selected">Libreria</div>
					<div class="tab">Reputazione</div>
					<div class="tab">Reputazione</div>
				</div>
				<div class="panels">
					<div class="panel">
						<div class="bookList">
							<%
								ListDescriptor<BookDescriptor> books = userToShow.getUserBooks();
								for (BookDescriptor book : books) {
							%>
							<div class="listElement clearfix">
								<div class="pictureContainer left">
									<img class="bookPicture"
										src="http://www.oasidellibro.it/wp-content/uploads/2010/04/Il-Signore-Degli-Anelli.jpg" />
								</div>
								<div class="resultDetails left">
									<div class="detail title"><%=book.getTitle()%></div>
									<div class="detail author">
										di
										<%=book.getAuthors()%></div>
									<div class="detail isbn"><%=book.getIsbn()%></div>
								</div>
							</div>
							<%
								}
							%>
						</div>
					</div>
					<div class="panel feedbackList">
					<%

					
					%>
					
					</div>
					<div class="panel">Reputazione 2</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>