<#macro post_form action button>
    <form action="${action}" method="post">
        <#nested>
        <input type="submit" value="${button}"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="submit" value="Sign Out"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
</#macro>