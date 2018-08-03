<#import "parts/common.ftl" as c>

<@c.page "Login Page">
    <div>
        <form action="/registration" method="GET">
            <input type="submit" value="Sign Up"/>
        </form>
    </div>

    <p>You need sign in to polling</p>
    <form action="/login" method="POST">
        <div><label>
            User Name : <input type="text" name="username" value="<#if user??>${user.getUsername()}</#if>"/>
            </label></div>
        <div><label>
            Password: <input type="password" name="password" value="<#if user??>${user.getPassword()}</#if>"/> </label></div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div><input type="submit" value="Sign In"/></div>
    </form>

    <form action="/debug/cleardb" method="POST">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="submit" value="Clear DB(for debug)"/>
    </form>
</@c.page>