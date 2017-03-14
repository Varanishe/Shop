<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="locale" var="loc"/>
    <fmt:message bundle="${loc}" key="locale.men" var="men"/>
    <fmt:message bundle="${loc}" key="locale.kids" var="kids"/>
    <fmt:message bundle="${loc}" key="locale.ladies" var="ladies"/>
    <fmt:message bundle="${loc}" key="locale.accessories" var="accessories"/>
    <fmt:message bundle="${loc}" key="locale.addnewitem" var="addnewitem"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/main.css">
    <title>shop</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>

<main>
    <div class="title-image ${category}-image">
    </div>
    <div class="layout">
        <div class="main-content">
            <div class="title">
                <h3>
                    <c:choose>
                        <c:when test="${category == 'men'}">
                        ${men}
                        </c:when>
                        <c:when test="${category == 'ladies'}">
                            ${ladies}
                        </c:when>
                        <c:when test="${category == 'kids'}">
                            ${kids}
                        </c:when>
                        <c:when test="${category == 'accessories'}">
                            ${accessories}
                        </c:when>
                    </c:choose>
                </h3>
                <c:if test="${sessionScope.user.role == 'admin'}">
                <button class="button add-item-btn" onclick="location.href='Controller?action=redirect&page=additem.jsp'">${addnewitem}</button>
                </c:if>
            </div>
            <div class="products">
                <div class="row">
                    <c:if test="${items != null}">
                <c:forEach items="${items}" var="item" varStatus="loop">
                    <div class="grid col-3" onClick="location.href='Controller?action=gotoitem&item_id=${item.id}'">
                        <div class="product-image-wrapper">
                            <img src="${item.imageUrl}" alt="${item.imageUrl}" width="300">
                            <h3 class="product-headline">${item.name}</h3>
                            <div class="price-tag">$${item.price}</div>
                            <span class="mark-${item.rating}"></span>
                        </div>
                    </div>
                    <c:if test="${(loop.index + 1) % 3 == 0}">
                        </div>
                        <div class="row">
                    </c:if>
                    <c:if test="${loop.index == fn:length(items)}">
                        </div>
                    </c:if>
                </c:forEach>
                </c:if>
            </div>
        </div>
    </div>
</main>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>