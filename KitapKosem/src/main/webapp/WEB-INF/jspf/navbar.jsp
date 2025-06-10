<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="navbar">
    <a href="${pageContext.request.contextPath}/kitaplar" class="navbar-brand">
        <span class="logo-icon"><i class="fa-solid fa-book-open"></i></span> KitapKöşem
    </a>
    <div class="navbar-links">
        <c:choose>
            <c:when test="${not empty sessionScope.loggedInUser}">
                <a href="${pageContext.request.contextPath}/addBook.jsp">Kitap Ekle</a>
                <span><a href="${pageContext.request.contextPath}/myBooks"><c:out value="${sessionScope.loggedInUser.username}"/><span class="user-info-icon"><i class="fa-regular fa-user"></i></span></a></span>
                <a href="${pageContext.request.contextPath}/logout">Çıkış Yap</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/login.jsp">Giriş Yap</a>
                <a href="${pageContext.request.contextPath}/register.jsp">Kayıt Ol</a>
            </c:otherwise>
        </c:choose>
    </div>
</div>