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
					<div class="bookGrid clearfix">
						<%
							for (BookDescriptor b : books.getList()) {
								String statusClass, statusText;
								switch (b.getStatus()) {
								case AVAILABLE:
									statusClass = "available";
									statusText = "Disponibile";
									break;
								case LENT:
									statusClass = "lent";
									statusText = "In prestito";
									break;
								default:
									statusClass = "notAvailable";
									statusText = "Non disponibile";
								}

								String privacyClass, privacyText;
								switch (b.getPrivacy()) {
								case PUBLIC:
									privacyClass = "public";
									privacyText = "Pubblico";
									break;
								case FRIENDS_ONLY:
									privacyClass = "friendsOnly";
									privacyText = "Amici";
									break;
								default:
									privacyClass = "private";
									privacyText = "Privato";
								}
						%>
						<div class="bookPresentation">
							<img
								class="bookPicture"
								src="http://www.oasidellibro.it/wp-content/uploads/2010/04/Il-Signore-Degli-Anelli.jpg" />
							<div class="bookInfo">
								<div class="title"><%=b.getTitle()%></div>
								<div class="author"><%=b.getAuthors()%></div>
								<div class="property">
									<span class="privacy <%=privacyClass%>"><%=privacyText%></span>
									<span class="status <%=statusClass%>"><%=statusText%></span>
								</div>
								<div class="bookView"><%=viewLinkFor(ConcreteBook.class, b.getId())%></div>
							</div>
						</div>
						<%
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