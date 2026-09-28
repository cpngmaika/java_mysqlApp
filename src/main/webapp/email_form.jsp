<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Gửi Email</title>
    <link rel="stylesheet" href="styles/main.css" type="text/css">
</head>
<body>
    <h1>Form Gửi Email</h1>
    
    <form action="sendEmail" method="post">
        <div class="form-group">
            <label>Gửi đến (To):</label>
            <input type="email" name="to" required placeholder="nhap_email_nguoi_nhan@gmail.com">
        </div>
        
        <div class="form-group">
            <label>Tiêu đề (Subject):</label>
            <input type="text" name="subject" required placeholder="Tiêu đề email">
        </div>
        
        <div class="form-group">
            <label>Nội dung (Body):</label>
            <textarea name="body" required placeholder="Nội dung email..."></textarea>
        </div>
        
        <div class="form-group">
            <label>&nbsp;</label>
            <input type="submit" value="Gửi Email" class="btn">
        </div>
    </form>
    
    <p><a href="index.jsp">Trở về trang chủ</a></p>
</body>
</html>
