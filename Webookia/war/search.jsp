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
		<div id="content" class="topWidthElement">
			<div class="contentSection">
				<h1 class="sectionTitle">Termini di ricerca</h1>
				<p class="paragraph">Puoi cercare un libro per titolo, autore
					e/o isbn.</p>
				
				<div class="sectionContent">
					<form class="completeSearchForm" action="result" method="post">
						<div class="searchLine">
							<label for="titleSearch" class="searchLabel">Titolo</label> <input
								id="titleSearch" class="textInput" type="text" name="title" />
						</div>
						<div class="searchLine">
							<label for="authorSearch" class="searchLabel">Autore</label> <input
								id="authorSearch" class="textInput" type="text" name="author" />
						</div>
						<div class="searchLine">
							<label for="isbnSearch" class="searchLabel">ISBN</label> <input
								id="isbnSearch" class="textInput" type="text" name="author" />
						</div>
						<div class="searchLine"><input type="submit" class="submitInput" value="Cerca"/></div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>