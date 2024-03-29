<%@page import="java.util.List"%>
<%@page import="it.webookia.backend.controller.services.Books"%>
<%@page import="it.webookia.backend.descriptor.BookDescriptor"%>
<%@page import="it.webookia.backend.descriptor.ListDescriptor"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ include file="shared/commons.jsp"%>

<%
	ListDescriptor<BookDescriptor> books = (ListDescriptor<BookDescriptor>) request
			.getAttribute(Books.BOOKLIST_ATTR);
%>

<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="shared/head.jsp"%>
<body class="webookia">
	<%@ include file="shared/header.jsp"%>
	<div id="contentContainer">
		<div id="content" class="topWidthElement">
			<div class="contentSection hideout">
				<h1 class="sectionTitle">Aggiungi un libro</h1>
				<div class="sectionContent">
					<p class="paragraph">Aggiungere un libro � semplice, basta
						inserire il suo ISBN qui sotto. Trovi l'ISBN sul retro del tuo
						libro, sopra il codice a barre. Inserisci solo i numeri, senza
						trattini.</p>
					<form name="createBookForm" id="createBookForm" method="POST"
						action="/books/create">
						<input type="text" class="textInput" pattern="[0-9]{13}"
							name="isbn" title="Codice ISBN" /> <input type="submit"
							class="submitInput" value="Crea!" />
					</form>
				</div>
			</div>
			<div class="contentSection">
				<h1 class="sectionTitle">I tuoi libri</h1>
				<div class="sectionContent">
					<%
						if (books.getElements().isEmpty()) {
					%>
					<p class="empty">Non hai inserito alcun libro.</p>
					<%
						} else {
					%>
					<div class="list bookList narrowList withBorder">
						<%
							for (BookDescriptor book : books) {
						%>
						<div class="listElement clearfix">
							<div class="pictureContainer left">
								<img class="bookPicture" src="<%=book.getThumbnail()%>" />
							</div>
							<div class="elementDetails left">
								<div class="detail title"><%=book.getTitle()%></div>
								<div class="detail author">
									di
									<%=book.getAuthors()%></div>
								<div class="detail isbn"><%=book.getIsbn()%></div>
								<div class="property">
									<%=bookStatusToHMTL(book.getStatus())%>
									<%=bookPrivacyToHTML(book.getPrivacy())%>
								</div>
							</div>
							<div class="selectButton right">
								<a href="/books/detail?id=<%=book.getId()%>"
									class="button select animate">Visualizza</a>
							</div>
						</div>
						<%
							}
							}
						%>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="shared/footer.jsp"%>
</body>
</html>