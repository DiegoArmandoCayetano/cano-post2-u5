<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1>Carrito de Compras</h1>

<a href="${pageContext.request.contextPath}/catalogo">
    Volver al catálogo
</a>

<table border="1">
    <tr>
        <th>Producto</th>
        <th>Cantidad</th>
        <th>Subtotal</th>
    </tr>

    <c:forEach var="item" items="${items}">
        <tr>
            <td>${item.producto.nombre}</td>
            <td>${item.cantidad}</td>
            <td>${item.subtotal}</td>
        </tr>
    </c:forEach>
</table>

<form method="post" action="${pageContext.request.contextPath}/carrito">
    <input type="hidden" name="accion" value="limpiar">
    <button type="submit">Limpiar carrito</button>
</form>