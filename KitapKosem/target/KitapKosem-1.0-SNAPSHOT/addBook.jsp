<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="/WEB-INF/jspf/navbar.jsp" %>

<!DOCTYPE html>
<html lang="tr">
<head>
    <meta charset="UTF-8">
    <title>KitapKöşem - Yeni Kitap Ekle</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body class="body-with-navbar">


<div class="main-content">
    <div class="add-book-container">
        <h2>Yeni Kitap Ekle</h2>


        <c:if test="${not empty requestScope.successMessage}">
            <p class="form-message success"><c:out value="${requestScope.successMessage}"/></p>
        </c:if>
        <c:if test="${not empty requestScope.errorMessage}">
            <p class="form-message error"><c:out value="${requestScope.errorMessage}"/></p>
        </c:if>

        <form class="add-book-form" method="POST" action="${pageContext.request.contextPath}/addBook" enctype="multipart/form-data">

            <div class="upload-image-area">

                <img id="imagePreview" src="#" alt="Kapak Resmi Önizlemesi" style="display: none; max-width: 100%; max-height: 200px; border-radius: 8px; margin-bottom: 10px;"/>

                <label for="bookImage" id="uploadLabel">
                    <i class="fas fa-plus upload-icon"></i>
                    <span class="upload-text">Kapak Resmi Yükle</span>
                </label>

                <input type="file" id="bookImage" name="bookImage" accept="image/png, image/jpeg" required style="display: none;">
            </div>
            <div class="form-fields">
                <div class="form-group">
                    <label for="title">Kitap Adı:</label>
                    <input type="text" id="title" name="title" required>
                </div>

                <div class="form-group">
                    <label for="authorName">Yazar Adı:</label>
                    <input type="text" id="authorName" name="authorName" required>
                </div>

                <div class="form-group">
                    <label for="description">Açıklama:</label>
                    <textarea id="description" name="description" rows="5" required></textarea>
                </div>

                <div class="form-group">
                    <label for="bookTags">Etiketler (isteğe bağlı, virgülle ayırın):</label>
                    <input type="text" id="bookTags" name="bookTags">
                </div>
            </div>

            <div class="submit-button-area">
                <button type="submit">Yükle</button>
            </div>
        </form>
    </div>
</div>



<script>

    document.getElementById('bookImage').onchange = function(event) {

        if (event.target.files && event.target.files[0]) {
            var file = event.target.files[0];

            var preview = document.getElementById('imagePreview');
            var uploadLabel = document.getElementById('uploadLabel');

            preview.src = URL.createObjectURL(file);

            preview.onload = function() {
                URL.revokeObjectURL(preview.src); // free memory
            }
            preview.style.display = 'block';
            uploadLabel.style.display = 'none';
        }
    };
</script>
</body>
</html>
</body>
</html>