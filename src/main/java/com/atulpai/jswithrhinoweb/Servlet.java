package com.atulpai.jswithrhinoweb;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.Scriptable;

@WebServlet(name = "JsWithRhinoWeb", value = "/servlet")
public class Servlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        String code = request.getParameter("code");

        try(  Context ctx = Context.enter() ){
            Scriptable scope = ctx.initStandardObjects();
            Object result = ctx.evaluateString(scope, code, "<code>", 1, null);
            response.getWriter().print(Context.toString(result));
        } catch(RhinoException ex) {
            response.getWriter().println(ex.getMessage());
        }
    }
}