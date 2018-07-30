<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Registration Page</title>
    </head>
    <body>
        <script type="text/javascript">
            "${message}" != null ? document.write("${message}") : console.log("");
        </script>
        <div>
            <form action="/login" method="GET">
                <input type="submit" value="Sign In"/>
            </form>
        </div>

        <p>Enter login and password for your account</p>
        <form action="/registration" method="POST">
            <div>
                <label> LOGIN : <input type="text" name="username"/> </label>
            </div>
            <div>
                <label> PASSWORD: <input type="password" name="password"/> </label>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <div><input type="submit" value="Sign Up"/></div>
        </form>
    </body>
</html>