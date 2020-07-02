<%@ page import="com.ara.teamcity.kicker.KickerConsts" %>
<%@ taglib prefix="bs" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="admin" tagdir="/WEB-INF/tags/admin" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="forms" tagdir="/WEB-INF/tags/forms" %>

<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>
<c:set var="urlID" value="<%=KickerConsts.SERVER_URL_KEY%>"/>
<c:set var="payloadID" value="<%=KickerConsts.SERVER_PAYLOAD_KEY%>"/>


<l:settingsGroup title="REST API Kicker Settings">
<tr>
    <th><label for="${urlID}">Server URL: <l:star/></label></th>
        <td>
            <div class="posRel">
                <props:textProperty name="${urlID}" size="56" maxlength="500"/>
                <span class="error" id="error_${urlID}"></span>
            </div>
        </td>
</tr>
<tr>
    <th><label for="${payloadID}">Payload: <l:star/></label></th>
        <td>
            <div class="posRel">
                <props:multilineProperty name="${payloadID}" linkTitle="Put the payload here" className="longField" expanded="true" rows="10" cols="40" />
                <span class="error" id="error_${payloadID}"></span>
            </div>
        </td>
</tr>

</l:settingsGroup>