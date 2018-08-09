<#import "../parts/common.ftl" as c>
<#import "../parts/action_macro.ftl" as action>
<#import "../parts/login.ftl" as login>

<@c.page "Login Page">
    <div>
        <form action="/registration" method="GET">
            <input type="submit" value="Sign Up"/>
        </form>
    </div>

    <h4>You need sign in to polling</h4>
    <@login.form "login" "Sign In"/>
</@c.page>