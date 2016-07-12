<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
<head>
    <title>Create Account: Choose Account Type</title>
</head>

<body>

<h1>Choose Account Type</h1>

<form method="POST">
    <table>
        <tr>
            <th>Type</th>
            <td>
                <select name="type">
                    <c:forEach var="type" items="<%= io.pivotal.AccountType.values() %>">
                        <option value="${type}">${type.name().toLowerCase()}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <th></th>
            <td><input type="submit" name="_eventId_submit" value="Continue"/></td>
        </tr>
    </table>
</form>

</body>

</html>
