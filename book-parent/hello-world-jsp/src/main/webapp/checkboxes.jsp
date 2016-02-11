<%@ page contentType="text/html;charset=UTF-8" %>
<%!
  private static final String[] fruits = {"Apple", "Orange", "Grapes"};
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Hello Fruits Application</title>
    </head>
    <body>
        <form action="checkboxes.jsp" method="GET">
            Select the fruits you like to eat:<br /><%
            for (String fruit : fruits) {
                %><input type="checkbox" name="fruit" value="<%= fruit %>" /> <%= fruit %><br /><%
            }
            %><input type="submit" value="Submit" /> 
        </form>
        <%
        String[] fruitArray = request.getParameterValues("fruit");
        if (fruitArray != null) {
            %>Your selection:<br /><ul><%
            for (String item : fruitArray) {
                out.println("<li>" + item + "</li>");
            }
            %></ul><%
        } else {
            %><p>You did not select any fruits.</p><%
        }
        %>
        <div>
        </div>
    </body>
</html>
