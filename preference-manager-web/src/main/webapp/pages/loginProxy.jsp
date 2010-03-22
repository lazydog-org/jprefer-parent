<body onload="document.forms[0].submit()">
    <form method="post" action="j_security_check">
        <input type="hidden" name="j_username" value="${j_username}"/>
        <input type="hidden" name="j_password" value="${j_password}"/>
    </form>
</body>
