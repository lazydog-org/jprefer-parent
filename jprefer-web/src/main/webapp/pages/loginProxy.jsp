<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<!--
    Copyright 2009, 2010 lazydog.org.

    This file is part of JPrefer.

    JPrefer is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    JPrefer is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with JPrefer.  If not, see <http://www.gnu.org/licenses/>.
-->
<body onload="document.forms[0].submit()">
    <form method="post" action="j_security_check">
        <input type="hidden" name="j_username" value="${j_username}"/>
        <input type="hidden" name="j_password" value="${j_password}"/>
    </form>
</body>
