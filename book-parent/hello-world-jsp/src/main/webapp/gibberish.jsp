<%@ page contentType="text/html;charset=UTF-8" %>
<%!
    private final int five = 0;

    protected String cowboy = "rodeo";

    //The assignment below is not declarative and is a syntax error if uncommented
    //cowboy = "test";

    public long addFive(long number) {
        return number + 5L;
    }

    public class MyInnerClass {
        public int seven() { return 7; }
    }
    MyInnerClass instanceVariable = new MyInnerClass();

    //WeirdClassWithinMethod is in method scope, so the declaration below is
    // a syntax error if uncommented
    //WeirdClassWithinMethod bad = new WeirdClassWithinMethod();
%>
<%
    class WeirdClassWithinMethod {
    }

    WeirdClassWithinMethod weirdClass = new WeirdClassWithinMethod();
    MyInnerClass innerClass = new MyInnerClass();
    int seven;
    seven = innerClass.seven();
%>
<%= "Hello, World" %><br />
<%= addFive(seven) %>
