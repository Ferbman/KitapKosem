<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="tr">
<head>
  <meta charset="UTF-8">
  <title>KitapKöşem - Kayıt Ol</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body class="auth-body">

<div class="auth-container">
  <h2>Hesap Oluştur</h2>

  <c:if test="${not empty requestScope.errorMessage}">
    <p style="color: #ff4d4d; text-align: center; margin-bottom: 15px;"><c:out value="${requestScope.errorMessage}"/></p>
  </c:if>

  <form method="POST" action="${pageContext.request.contextPath}/register">
    <div class="form-group">

      <input type="text" id="username" name="username" class="input-glow" placeholder="Kullanıcı Adı" required>
    </div>

    <div class="form-group">
      <input type="email" id="email" name="email" class="input-glow" placeholder="E-posta Adresi" required>
    </div>

    <div class="form-group">
      <input type="password" id="password" name="password" class="input-glow" placeholder="Şifre" required>
    </div>


    <button type="submit" class="btn-glow">Kayıt Ol</button>
  </form>

  <div class="auth-link">
    <p>Zaten bir hesabın var mı? <a href="${pageContext.request.contextPath}/login.jsp">Giriş Yap</a></p>
  </div>
</div>
</body>
</html>