<#macro form action button>
    <form action="/${action}" method="POST">
        <label>
            USERNAME : <input type="text" name="username" value="<#if user??>${user.getUsername()}</#if>"/>
        </label><br>
        <label>
            PASSWORD: <input type="password" name="password" value="<#if user??>${user.getPassword()}</#if>"/>
        </label><br>
        <input type="submit" value="${button}"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
</#macro>