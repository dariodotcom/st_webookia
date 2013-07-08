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
					<p class="paragraph">Aggiungere un libro è semplice, basta
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
						List<BookDescriptor> bookList = books.getList();
						if (bookList.isEmpty()) {
					%>
					<p class="empty">Non hai inserito alcun libro.</p>
					<%
						} else {
					%>
					<div class="bookGrid clearfix">
						<%
							for (BookDescriptor b : books.getList()) {
						%>
						<div class="bookPresentation">
							<img class="bookPicture"
								src="<%=b.getThumbnail()%>" />
							<div class="bookInfo">
								<div class="title"><%=b.getTitle()%></div>
								<div class="author"><%=b.getAuthors()%></div>
								<div class="property">
									<%=bookStatusToHMTL(b.getStatus())%>
									<%=bookPrivacyToHTML(b.getPrivacy())%>
								</div>
								<div class="bookView"><%=viewLinkFor(ConcreteBook.class, b.getId())%></div>
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
	</div>
</body>
</html>