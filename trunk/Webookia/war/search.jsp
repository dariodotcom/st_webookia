<%@page import="java.util.List"%>
<%@page import="it.webookia.backend.descriptor.DetailedBookDescriptor"%>
<%@page
	import="it.webookia.backend.controller.services.SearchContainer.DetailedResultBox"%>
<%@page import="it.webookia.backend.controller.services.SearchContainer"%>
<%@page import="it.webookia.backend.utils.servlets.SearchParameters"%>
<%@page import="it.webookia.backend.descriptor.BookDescriptor"%>
<%@page import="it.webookia.backend.controller.services.Books"%>
<%@page import="it.webookia.backend.model.ConcreteBook"%>
<%@page import="it.webookia.backend.descriptor.ListDescriptor"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="shared/commons.jsp"%>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="shared/head.jsp"%>
<body class="webookia">
	<%@ include file="shared/header.jsp"%>
	<div id="contentContainer">
		<div id="content" class="topWidthElement">
			<!-- Search -->
			<div class="contentSection">
				<h1 class="sectionTitle">Termini di ricerca</h1>
				<%
					SearchParameters params = ServletUtils.getRequestAttribute(request,
							SearchParameters.class, SearchContainer.PARAMETERS);

					if (params == null) {
						params = new SearchParameters();
					}
				%>
				<div class="sectionContent">
					<p class="paragraph">Puoi cercare un libro per titolo, autore
						e/o isbn.</p>
					<form class="completeSearchForm" action="result" method="post">
						<div class="searchLine">
							<label for="titleSearch" class="searchLabel">Titolo</label> <input
								id="titleSearch" class="textInput" type="text" name="title"
								value="<%=nullfix(params.getTitle())%>" />
						</div>
						<div class="searchLine">
							<label for="authorSearch" class="searchLabel">Autore</label> <input
								id="authorSearch" class="textInput" type="text" name="authors"
								value="<%=nullfix(params.getAuthors())%>" />
						</div>
						<div class="searchLine">
							<label for="isbnSearch" class="searchLabel">ISBN</label> <input
								id="isbnSearch" class="textInput" type="text" name="isbn"
								value="<%=nullfix(params.getISBN())%>" />
						</div>
						<div class="searchLine">
							<input type="submit" class="submitInput" value="Cerca" />
						</div>
					</form>
				</div>

			</div>
			<!-- Detail result -->
			<%
				DetailedResultBox resultBox = ServletUtils.getRequestAttribute(
						request, DetailedResultBox.class,
						SearchContainer.DETAILED_RESULTS);
				if (resultBox != null) {
					List<DetailedBookDescriptor> resultList = resultBox.getList();
			%>
			<div class="contentSection">
				<h1 class="sectionTitle">Risultati ricerca</h1>
				<div class="sectionContent">
					<%
						if (resultList.isEmpty()) {
					%>
					<p class="empty">La ricerca non ha restituito risultati.</p>
					<%
						} else {
					%>
					<p class="paragraph">La ricerca ha restituito i seguenti
						risultati. Selezionane uno per proseguire nella ricerca.</p>
					<div class="resultList">
						<%
							for (DetailedBookDescriptor book : resultBox.getList()) {
						%>
						<div class="result clearfix">
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
							<div class="selectButton right">
								<a href="/search/concrete?detail=<%=book.getId()%>"
									class="button select animate">Seleziona</a>
							</div>
						</div>
						<%
							}
						%>
					</div>
					<%
						}
					%>
				</div>
			</div>
			<%
				}
			%>
		</div>
	</div>
</body>
</html>