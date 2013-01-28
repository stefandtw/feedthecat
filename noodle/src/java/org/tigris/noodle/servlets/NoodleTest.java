/* ================================================================
 * Copyright (c) 2000-2003 CollabNet.  All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * 
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 
 * 3. The end-user documentation included with the redistribution, if
 * any, must include the following acknowlegement: "This product includes
 * software developed by CollabNet <http://www.collab.net/>."
 * Alternately, this acknowlegement may appear in the software itself, if
 * and wherever such third-party acknowlegements normally appear.
 * 
 * 4. The hosted project names must not be used to endorse or promote
 * products derived from this software without prior written
 * permission. For written permission, please contact info@collab.net.
 * 
 * 5. Products derived from this software may not use the "Tigris" or 
 * "Scarab" names nor may "Tigris" or "Scarab" appear in their names without 
 * prior written permission of CollabNet.
 * 
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL COLLABNET OR ITS CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * ====================================================================
 * 
 * This software consists of voluntary contributions made by many
 * individuals on behalf of CollabNet.
 */ 

package org.tigris.noodle.servlets;

// Java Servlet Classes
import javax.servlet.*;
import javax.servlet.http.*;
// Java Core Classes
import java.io.*;
import java.util.*;
import java.net.*;

import org.tigris.noodle.*;
import org.tigris.noodle.servlets.util.ParameterParser;

/**
    A basic poorly written servlet for testing the functionality of Noodle.
    <p>
    Here is an example URL for using it (with Tomcat):
    <p>
    http://www.foo.com/noodle/servlet/Noodle?page=%2Fnoodle%2Fservlet%2FNoodleTest
    <p>
    That will return a form that you can submit. The doGet() method sends a Set-Cookie:
    header that the POST method will display the contents of the cookie. This proves that
    both passing form data as well as Cookie headers through Noodle works.
    
    @author <a href="mailto:jon@collab.net">Jon S. Stevens</a>
*/
public class NoodleTest extends HttpServlet
{
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }
    public void doGet (HttpServletRequest req, HttpServletResponse res)
        throws IOException, ServletException
    {
        try
        {
            res.setContentType("text/html");
            Cookie ca = new Cookie("mycookieA",URLEncoder.encode("jon can cook cookies"));
            ca.setPath("/");
            res.addCookie(ca);
            Cookie cb = new Cookie("mycookieB",URLEncoder.encode("jon can cook FAT cookies"));
            cb.setPath("/");
            res.addCookie(cb);
            PrintWriter out = res.getWriter();

            out.println ("<html><head><title>Foo</title></head><body bgcolor=white>");
            out.println ("<form action=\"/noodle/servlet/Noodle?page=%2Fnoodle%2Fservlet%2FNoodleTest&true=mytrue\" method=\"POST\">");
            out.println ("<input type=\"text\" name=\"but\" value=\"sometext\">");
            out.println ("<input type=\"submit\" name=\"submit\" value=\"Red Pill\">");
            out.println ("</body></html>");
        }
        catch (Exception e)
        {
            System.out.println ("Error: " + e.toString());
        }
    }
    public void doPost (HttpServletRequest req, HttpServletResponse res)
        throws IOException, ServletException
    {
        PrintWriter output = null;
        try
        {
            output = res.getWriter();
            
            // things should still work if you comment this next line out
            res.setContentType("text/html");
            
            // playing with cookies
            Cookie[] cookies = req.getCookies();
            if (cookies.length > 0)
            {
                output.println ("WE Have a cookie!");
                output.println ("<p>Cookie Value A: " + cookies[0].getValue());
                output.println ("<p>Cookie Value B: " + cookies[1].getValue());
            }
            
            ParameterParser pp = new ParameterParser(req);
            output.println ("<p>post variable: " + pp.getString("but", "NOT THERE")); //output: sometext
            output.println ("<p>get variable: " + pp.getString("true", "NOT TRUE")); //output: mytrue
        }
        catch (Exception e)
        {
            output.println ("<pre>" + Noodle.stackTrace(e) + "</pre>");
        }
    }
    public String getServletInfo()
    {
        return "NoodleTest Servlet";
    }
}
