<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
<head>
    <title>Accounts</title>
</head>

<body>

<h1>Accounts</h1>

<table>
    <tr>
        <th>Sort Code</th>
        <th>Number</th>
        <th>Type</th>
        <th>Owner</th>
        <th>Overdraft Limit</th>
    </tr>
    <c:forEach var="account" items="${accounts}">
        <tr data-qa="account-${account.owner}">
            <td data-qa="sortCode">${account.sortCode}</td>
            <td data-qa="number">${account.number}</td>
            <td data-qa="type">${account.type.name().toLowerCase()}</td>
            <td data-qa="owner">${account.owner}</td>
            <td data-qa="overdraft">${account.overdraft}</td>
        </tr>
    </c:forEach>
</table>

<form action="createAccount">
    <input type="submit" value="Create Account"/>
</form>

</body>

</html>
