<%@page import="it.webookia.backend.controller.resources.BookResource"%>
<%@page import="it.webookia.backend.controller.services.Books"%>
<%@page import="it.webookia.backend.descriptor.BookDescriptor"%>
<%@page import="it.webookia.backend.utils.ServletUtils"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	String pageTitle = "Webookia";

	BookResource hBook = ServletUtils.getRequestAttribute(request,
			BookResource.class, Books.CONTEXT_BOOK);
	BookDescriptor hContextBook = (hBook == null) ? null : hBook
			.getDescriptor();
%>

<head>
<title><%=pageTitle%></title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />

<link type="text/css" rel="stylesheet" href="/css/zero.css" />
<link type="text/css" rel="stylesheet" href="/css/clearfix.css" />
<link href='http://fonts.googleapis.com/css?family=Alef'
	rel='stylesheet' type='text/css'>
<link type="text/css" rel="stylesheet" href="/css/webookia.css" />
<link rel="icon" href="/favicon.ico" type="image/icon" />
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
<%
	if (hContextBook != null) {
%>
<meta property="fb:app_id" content="497944510260906" />
<meta property="og:type" content="book" />
<meta property="og:url"
	content="books/detail?id=<%=hContextBook.getId()%>" />
<meta property="og:title" content="<%=hContextBook.getTitle()%>" />
<meta property="og:image" content="<%=hContextBook.getThumbnail()%>" />
<%
	}
%>

</head>