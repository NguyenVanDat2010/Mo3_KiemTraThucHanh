<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: VCOM
  Date: 01/07/2020
  Time: 9:26 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<h1>Product List</h1>
<p>
    <a href="/products">Home</a>
    <a href="/products?action=create">Create new product</a>
</p>

<form method="post" action="/products?action=search">
    <input type="text" name="searchValue" placeholder="Enter your word">
    <input type="submit" name="search" value="Search user"><br>
    <c:if test="${requestScope['message']!=null}">
        <span class="message" style="color: red">${requestScope["message"]}</span>
    </c:if>
</form>

<form method="post" action="/products?action=sort">
    <select style="width: 150px; height: 30px;" name="sortBy">
        <option>Sort by</option>
        <option value="productName">Name</option>
        <option value="price">Price</option>
        <option value="quantity">Quantity</option>
        <option value="color">Color</option>
        <option value="categoryName">Category</option>
    </select>
    <SELECT STYLE="width: 150px; height: 30px;"name="styleSort">
        <option>Style sort</option>
        <option value="asc">ASC</option>
        <option value="desc">DESC</option>
    </SELECT>
    <button type="submit">Sort</button>
    <%--    <a href="/users?action=sort"><button type="submit">Sort</button></a>--%>
</form>

<table>
    <tr>
        <th>Name</th>
        <th>Price</th>
        <th>Quantity</th>
        <th>Color</th>
        <th>Description</th>
        <th>Category</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:forEach items="${requestScope['products']}" var="pro">
        <tr>
            <td>${pro.getProductName()}</td>
            <td>${pro.getPrice()}</td>
            <td>${pro.getQuantity()}</td>
            <td>${pro.getColor()}</td>
            <td><label>${pro.getDescription()}</label></td>
            <td>${pro.getCategory()}</td>
            <td><a href="/products?action=edit&id=${pro.getId()}"><button type="button">Edit</button></a></td>
            <td><a href="/products?action=delete&id=${pro.getId()}"><button type="button">Delete</button></a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
