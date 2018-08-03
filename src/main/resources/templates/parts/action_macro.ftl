<#macro debug action>
    <form action="/debug/${action}" method="post">
        <#nested>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <input type="submit" value="${action}"/>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <input type="submit" value="Sign Out"/>
    </form>
</#macro>