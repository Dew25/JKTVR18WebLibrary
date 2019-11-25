<%-- 
    Document   : showUploadFile
    Created on : Nov 12, 2019, 9:03:02 AM
    Author     : Melnikov
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Загрузка файла</h1>
        <p>${info}</p>
        <form action="uploadController" method="POST" enctype="multipart/form-data">
            Описание (имя): <input type="text" name="description"><br>
            <input type="file" name="file"><br>
            <input type="submit" value="Загрузить">
        </form>
    </body>
</html>
