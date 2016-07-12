<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Create Account: Customer Details</title>
</head>

<body>

<h1>Customer Details</h1>

<form method="POST">
    <table>
        <tr>
            <th>Name</th>
            <td><input name="owner"/></td>
        </tr>
        <tr>
            <th></th>
            <td><input type="submit" name="_eventId_submit" value="Continue"/></td>
        </tr>
    </table>
</form>

</body>

</html>
