<%@ page contentType="text/html;charset=UTF-8" %>
<%!
  private static final String DEFAULT_USER = "Quest";
%>
<%
  String user = request.getParameter("user");
  if (user == null) {
    user = DEFAULT_USER;
  }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Hello User Application</title>
    </head>
    <body>
        <h3>Hello, <%= user %>!</h3>
        <form action="greeting.jsp" method="POST">
            Enter your name:
            <input type="text" name="user" /><br />
            <input type="submit" value="Submit" /> 
        </form>
    </body>
</html>
