<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as reg>

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
    <@reg.form "registration/add" "Sign Up"/>
</@c.page>