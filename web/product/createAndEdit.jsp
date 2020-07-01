<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: VCOM
  Date: 01/07/2020
  Time: 10:35 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

</head>
<body>
<h1>Insert or update new user</h1>

<p>
    <c:if test="${requestScope['message']!=null}">
        <span class="message" style="color: red">${requestScope["message"]}</span>
    </c:if>
</p>

<p><a href="/products">Back to user list</a></p>

<form method="post">
    <fieldset>
        <legend>Product information</legend>
        <table>
            <tr>
                <th>Name:</th>
                <td><input type="text" name="name" id="name" value="${requestScope['product'].getProductName()}"></td>
            </tr>
            <tr>
                <th>Price:</th>
                <td><input type="text" name="price" id="price" value="${requestScope['product'].getPrice()}"></td>
            </tr>
            <tr>
                <th>Quantity:</th>
                <td><input type="text" name="quantity" id="quantity" value="${requestScope['product'].getQuantity()}"></td>
            </tr>
            <tr>
                <th>Color</th>
                <td><input type="text" name="color" id="color" value="${requestScope['product'].getColor()}"></td>

            </tr>
            <tr>
                <th>Description</th>
                <td><input type="text" name="description" id="description" value="${requestScope['product'].getDescription()}"></td>
            </tr>
            <tr>
                <th>Category</th>
                <td>
<%--                    <select name="category">--%>
<%--                        <option value="phone">Phone</option>--%>
<%--                        <option value="television" >Television</option>--%>
<%--                        <option value="motorcycle">Motorcycle</option>--%>
<%--                        <option value="car">Car</option>--%>
<%--                    </select>--%>
                    <select name="category">
                        <c:forEach var="item" items="${dept}">
                            <option value="${item.key}" ${item.key == option ? 'selected="selected"' : ''}>${item.value}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" value="Submit"></td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>
