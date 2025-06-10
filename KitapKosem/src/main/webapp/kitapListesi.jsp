<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/jspf/navbar.jsp" %>

<!DOCTYPE html>
<html lang="tr">
<head>
  <meta charset="UTF-8">
  <title>KitapKöşem - Ana Sayfa</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"/>
</head>
<body class="body-with-navbar">

<div class="main-container">
  <div class="filter-bar">
    <div class="sort-options">
      <span>Sırala:</span>
      <a href="#">Yazar <i class="fa-solid fa-arrow-down"></i></a>
      <a href="#">Puan <i class="fa-solid fa-arrow-down"></i></a>
      <a href="#">Tarih <i class="fa-solid fa-arrow-down"></i></a>
      <a href="#">Sayfa <i class="fa-solid fa-arrow-down"></i></a>
    </div>
    <div class="main-search-bar">
      <form action="${pageContext.request.contextPath}/kitaplar" method="GET" style="justify-content: center; align-items: center; display: flex">
        <input type="text" name="aramaKelimesi" placeholder="Kitap veya yazar arayın..."
               value="<c:out value='${searchQuery}'/>">
        <i class="fas fa-search search-icon" ></i>
      </form>
    </div>
  </div>

  <div class="book-grid">
    <c:choose>
      <c:when test="${not empty kitapListesiAttribute}">
        <c:forEach var="kitap" items="${kitapListesiAttribute}">
          <a href="${pageContext.request.contextPath}/kitapDetay?id=${kitap.bookId}" class="book-card">
            <div class="image-container-card" style="justify-content: center; display: flex;">
              <img src="${pageContext.request.contextPath}/${kitap.imagePath}" alt="<c:out value='${kitap.title}'/>" class="book-card-image"></div>

            <div class="book-card-info">
              <h3 style="text-decoration: none; color: white"><c:out value="${kitap.title}"/></h3>
              <p style="text-decoration: none; color: white"><c:out value="${kitap.authorName}"/></p>
              <div class="book-card-extra-info">
              </div>
            </div>
          </a>
        </c:forEach>
      </c:when>
      <c:otherwise>
        <p>Gösterilecek kitap bulunamadı.</p>
      </c:otherwise>
    </c:choose>
  </div>
</div>
</body>
</html>