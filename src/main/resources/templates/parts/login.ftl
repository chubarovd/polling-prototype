<#macro form action button>
    <form action="/${action}" method="POST">
        <div><label>
            USERNAME : <input type="text" name="username" value="<#if user??>${user.getUsername()}</#if>"/>
        </label></div>
        <div><label>
            PASSWORD: <input type="password" name="password" value="<#if user??>${user.getPassword()}</#if>"/> </label></div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div><input type="submit" value="${button}"/></div>
    </form>
</#macro>