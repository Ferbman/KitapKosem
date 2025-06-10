<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="tr">
<head>
    <meta charset="UTF-8">
    <title>KitapKöşem - Giriş Yap</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="auth-body">

<div class="auth-container">
    <h2>Giriş Yap</h2>

    <%-- Hata mesajı varsa göster --%>
    <c:if test="${not empty requestScope.errorMessage}">
        <p style="color: #ff4d4d; text-align: center; margin-bottom: 15px;"><c:out value="${requestScope.errorMessage}"/></p>
    </c:if>

    <%-- Kayıt sonrası başarı mesajı varsa göster --%>
    <c:if test="${param.registration == 'success'}">
        <p style="color: #4CAF50; text-align:center; margin-bottom: 15px;">Kayıt başarılı! Lütfen giriş yapın.</p>
    </c:if>

    <form method="POST" action="${pageContext.request.contextPath}/login">
        <div class="form-group">
            <input type="text" id="username" name="username" class="input-glow" placeholder="Kullanıcı Adı veya E-posta" required>
        </div>

        <div class="form-group">
            <input type="password" id="password" name="password" class="input-glow" placeholder="Şifre" required>
        </div>

        <button type="submit" class="btn-glow">Giriş Yap</button>
    </form>

    <div class="auth-link">
        <p>Hesabın yok mu? <a href="${pageContext.request.contextPath}/register.jsp">Kayıt Ol</a></p>
    </div>
</div>

</body>
</html>