<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/jspf/navbar.jsp" %>

<!DOCTYPE html>
<html lang="tr">
<head>
  <meta charset="UTF-8">
  <title>Kitaplarım - KitapKöşem</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body class="body-with-navbar">
<div class="main-content">
  <h1 style="text-align: center; margin-bottom: 30px;">Eklediğim Kitaplar</h1>

  <div class="book-grid">
    <c:choose>
      <c:when test="${not empty bookList}">
        <c:forEach var="kitap" items="${bookList}">
          <div class="book-card-link"> <%-- Bu bir link değil, sadece sarmalayıcı --%>
            <div class="book-card">
              <a href="${pageContext.request.contextPath}/kitapDetay?id=${kitap.bookId}">
                <div class="card-image-wrapper">
                  <div class="image-container-card" style="justify-content: center; display: flex;">
                    <img src="${pageContext.request.contextPath}/${kitap.imagePath}" alt="<c:out value='${kitap.title}'/>" class="book-card-image"></div>
                </div>
              </a>
              <div class="book-card-info">
                <h3><a href="${pageContext.request.contextPath}/kitapDetay?id=${kitap.bookId}"><c:out value="${kitap.title}"/></a></h3>
                <p  style="text-decoration: none; color: white" class="author"><c:out value="${kitap.authorName}"/></p>

                <div style="text-decoration: none; color: white" class="card-details" style="border-top: none; padding-top: 0; margin-top: 15px;">
                    <%-- SİLME FORMU --%>
                  <form method="POST" action="${pageContext.request.contextPath}/deleteBook" onsubmit="return confirm('Bu kitabı silmek istediğinizden emin misiniz?');">
                    <input type="hidden" name="bookId" value="${kitap.bookId}">
                    <button type="submit" class="delete-button" style="width: 100%; font-size: 1em; padding: 10px;">Bu Kitabı Sil</button>
                  </form>
                </div>
              </div>
            </div>
          </div>
        </c:forEach>
      </c:when>
      <c:otherwise>
        <p style="color: white; text-align: center; grid-column: 1 / -1;">Henüz hiç kitap eklemediniz.</p>
      </c:otherwise>
    </c:choose>
  </div>
</div>
</body>
</html>