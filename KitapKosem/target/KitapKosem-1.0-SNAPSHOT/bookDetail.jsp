<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ include file="/WEB-INF/jspf/navbar.jsp" %>

<!DOCTYPE html>
<html lang="tr">
<head>
  <meta charset="UTF-8">
  <title>KitapKöşem - <c:out value="${book.title}"/></title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body> <%-- Karanlık mod için olan 'auth-body' class'ını kaldırdık --%>

<%-- Navbar buraya include ediliyor --%>

<div class="main-content"> <%-- Ana sayfa ile aynı genel konteyneri kullanıyoruz --%>
  <c:if test="${not empty book}">
    <div class="book-detail-box"> <%-- Kitap kartları gibi koyu bir kutu --%>

      <div class="detail-header">
        <div class="detail-cover">
          <img src="${pageContext.request.contextPath}/${book.imagePath}" alt="<c:out value='${book.title}'/>">
        </div>

        <div class="detail-meta">
          <h1><c:out value="${book.title}"/></h1>
          <p class="author">Yazar: <c:out value="${book.authorName}"/></p>
          <p class="description"><c:out value="${book.description}"/></p>

          <div class="rating-display">
            <strong>Ortalama Puan:</strong>
            <c:out value="${averageRating > 0 ? String.format('%.1f', averageRating) : 'Puanlanmamış'}"/> / 5.0
            <div class="rating-bar">
              <div class="rating-bar-fill" style="width: ${averageRating / 5 * 100}%;"></div>
            </div>
          </div>
        </div>
      </div>

      <div class="comments-wrapper">
        <h2>Yorumlar</h2>
        <ul class="comment-list">
          <c:choose>
            <c:when test="${not empty comments}">
              <c:forEach var="comment" items="${comments}">
                <li class="comment-item">
                  <div class="comment-header">
                    <strong><c:out value="${comment.username}"/></strong>
                    <span style="float: right;"><fmt:formatDate value="${comment.commentDate}" pattern="dd MMM yyyy"/></span>
                  </div>
                  <p class="comment-body"><c:out value="${comment.commentText}"/></p>
                  <c:if test="${not empty sessionScope.loggedInUser && sessionScope.loggedInUser.userId == comment.userId}">
                    <form method="POST" action="${pageContext.request.contextPath}/deleteComment" style="text-align: right; margin-top: 10px;">
                      <input type="hidden" name="commentId" value="${comment.commentId}">
                      <input type="hidden" name="bookId" value="${book.bookId}">
                      <input type="submit" value="Sil" class="delete-button">
                    </form>
                  </c:if>
                </li>
              </c:forEach>
            </c:when>
            <c:otherwise>
              <li><p>Bu kitaba henüz hiç yorum yapılmamış.</p></li>
            </c:otherwise>
          </c:choose>
        </ul>

        <c:if test="${not empty sessionScope.loggedInUser}">
          <div class="add-comment-form">
            <h3>Yorum Yap / Puan Ver</h3>
            <form method="POST" action="${pageContext.request.contextPath}/addComment">
              <input type="hidden" name="bookId" value="${book.bookId}">
              <textarea name="commentText" placeholder="Yorumunuz (isteğe bağlı)..." rows="3"></textarea>
              <select name="rating">
                <option value="">-- Puan Ver --</option>
                <option value="5">5 Yıldız</option>
                <option value="4">4 Yıldız</option>
                <option value="3">3 Yıldız</option>
                <option value="2">2 Yıldız</option>
                <option value="1">1 Yıldız</option>
              </select>
              <input type="submit" value="Gönder">
            </form>


          </div>
        </c:if>
      </div>

    </div>
  </c:if>

  <c:if test="${empty book}">
    <div class="book-detail-box" style="text-align:center;">
      <h1>Kitap Bulunamadı</h1>
      <p>Aradığınız kitap sistemde mevcut değil veya bir hata oluştu.</p>
    </div>
  </c:if>
</div>

</body>
</html>