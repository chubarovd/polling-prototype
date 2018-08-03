<#import "parts/common.ftl" as c>

<@c.page "Registration Page">
    <p>
        <#if message??>
            ${message}
        </#if>
    </p>

    <div>
        <form action="/login" method="GET">
            <input type="submit" value="Sign In"/>
        </form>
    </div>

    <h4>Enter login and password for your account</h4>
    <form action="/registration/add" method="POST">
        <label> LOGIN : <input type="text" name="username"/> </label><br>
        <label> PASSWORD: <input type="password" name="password"/> </label><br>
        <input type="submit" value="Sign Up"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
</@c.page>