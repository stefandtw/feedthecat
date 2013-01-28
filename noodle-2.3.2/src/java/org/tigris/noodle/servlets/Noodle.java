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
 * software developed by Collab.Net <http://www.Collab.Net/>."
 * Alternately, this acknowlegement may appear in the software itself, if
 * and wherever such third-party acknowlegements normally appear.
 * 
 * 4. The hosted project names must not be used to endorse or promote
 * products derived from this software without prior written
 * permission. For written permission, please contact info@collab.net.
 * 
 * 5. Products derived from this software may not use the "Tigris" or 
 * "Scarab" names nor may "Tigris" or "Scarab" appear in their names without 
 * prior written permission of Collab.Net.
 * 
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL COLLAB.NET OR ITS CONTRIBUTORS BE LIABLE FOR ANY
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
 * individuals on behalf of Collab.Net.
 */ 

package org.tigris.noodle.servlets;

// Java Servlet Classes
import javax.servlet.*;
import javax.servlet.http.*;
// Java Core Classes
import java.io.*;
import java.util.*;
import java.net.*;

import HTTPClient.*;

import org.tigris.noodle.*;

/**
 *  This is the main Noodle Servlet class. To implement within your
 *  own Servlet code, you simply need to copy/paste the stuff in the
 *  doGet() and init() methods, or just use this servlet directly.
 *  
 *  @author <a href="mailto:jon@collab.net">Jon S. Stevens</a> 
 */
public class Noodle extends HttpServlet
{
    private static final boolean DEBUG = false;

    private static Properties noodleProperties = new Properties();

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);

        if (DEBUG)
        {
            System.out.println("noodle Init a");
        }
        // get a reference to the properties file.
        String propertiesFile = null;
        try
        {
            propertiesFile = getInitParameter(NoodleConstants.PROPERTIES);
            if (propertiesFile != null)
            {
                // Translate to the real path.
                String realPath = getServletContext()
                    .getRealPath(propertiesFile);
                if (realPath != null)
                {
                    propertiesFile = realPath;
                }
            }
            noodleProperties.clear();
            if (DEBUG)
            {
                System.out.println("noodle Init b");
            }
            noodleProperties.load(new FileInputStream(propertiesFile));
            if (DEBUG)
            {
                System.out.println("noodle Init c");
            }
        }
        catch (Exception e)
        {
            String message = "Noodle: Could not open properties file path: " + 
                propertiesFile + " " + e.getMessage();
            System.err.println(message);
            throw new ServletException(message);
        }

        //Remove the default HTTPClient.CookieModule.class
        //so that it can be overridden by our own implementation
        HTTPConnection.removeDefaultModule(HTTPClient.CookieModule.class);
        try
        {
            HTTPConnection.addDefaultModule
                (Class.forName("org.tigris.noodle.NoodleCookieModule"), 1);
        }
        catch (ClassNotFoundException cnfe)
        {
            //Should never get here, but just in case
            throw new ServletException ("Noodle: " + cnfe.getMessage());
        }
        
        if (DEBUG)
        {
            System.out.println("noodle Init d");
        }
        // setup some defaults
        int port = 80;
        String configuredPort =
            noodleProperties.getProperty(NoodleConstants.DEFAULT_PORT);
        try
        {
            if (configuredPort != null && configuredPort.length() > 0)
            {
                port = Integer.parseInt(configuredPort);
            }
        }
        catch (NumberFormatException e)
        {
            System.err.println(NoodleConstants.DEFAULT_PORT + " '" +
                               configuredPort +
                               "' not parsable as an integer: Defaulting " +
                               "to " + port);
        }
        ProxyModule defaultProxyModule = ProxyModule.getInstance();
        defaultProxyModule.setServerPort(port);

        String serverName = noodleProperties.getProperty
            (NoodleConstants.DEFAULT_HOST, "localhost");
        defaultProxyModule.setServerName(serverName);
        if (DEBUG)
        {
            System.out.println("noodle Init e (done)");
        }
    }

    /**
     * This is the core of the application.
     */
    public void doGet (HttpServletRequest req, HttpServletResponse res)
        throws IOException, ServletException
    {
        if (DEBUG)
        {
            System.out.println("doGet a");
        }
        OutputStream output = null;
        try
        {
            // Create a NoodleData object for this request.
            if (DEBUG)
            {
                System.out.println("doGet b");
            }
            NoodleData noodleData = new NoodleData(req, res, noodleProperties);
            if (DEBUG)
            {
                System.out.println("doGet c");            
            }

            // deal with POST Data. Since this code is meant to be modular
            // to other systems, then we need to be able to assign 
            // the byte[] of post data here instead of within the ProxyModule
            // because other systems may have already read the byte[] of data
            // from the stream
            if (req.getMethod().equalsIgnoreCase(NoodleConstants.POST))
            {
                noodleData.setPostData
                    (ProxyModule.readFully
                     (new BufferedInputStream(req.getInputStream())));

                /*
                  This code will also work if you do not have access to 
                  the data in the InputStream
                  noodleData.setPostData(postToByteArray(req));
                */
            }

            if (DEBUG)
            {
                System.out.println("doGet d");
            }
            //Proxy the request, running the request and response servlets
            //defined in noodleProperties.
            noodleData.proxyRequest();
            if (DEBUG)
            {
                System.out.println("doGet e");
            }
        }
        catch (Exception e)
        {
            // get the outputstream
            if (output == null)
            {
                output = res.getOutputStream();
            }
            output.write ("<pre>".getBytes());
            output.write (stackTrace(e).getBytes());
            output.write ("</pre>".getBytes());
        }
        finally
        {
            if (output != null)
            {
                output.close();
            }
        }
        if (DEBUG)
        {
            System.out.println("doGet f (done)");
        }
    }

    /**
     * This just calls the doGet() method.
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws IOException, ServletException
    {
        doGet (req, res);
    }

    /**
     * Noodle Servlet
     */
    public String getServletInfo()
    {
        return "Noodle Servlet";
    }

    /**
     * Returns the output of printStackTrace as a String
     */
    public static final String stackTrace(Throwable e)
    {
        String foo = null;
        try
        {
            // and show the Error Screen
            ByteArrayOutputStream ostr = new ByteArrayOutputStream();
            e.printStackTrace( new PrintWriter(ostr,true) );
            foo = ostr.toString();
        }
        catch (Exception f)
        {
            // do nothing
        }
        return foo;
    }

    /**
     * This method will convert all the parameters of a HTTP request
     * into a byte array. It isn't the most efficient and should only be
     * used in systems where you cannot get access to the
     * req.getInputStream().  
     */
    public static final byte[] postToByteArray(HttpServletRequest req)
    {
        Vector nvars = new Vector();
        // loop over all the parameters
        for (Enumeration e = req.getParameterNames(); e.hasMoreElements(); )
        {
            String name = (String) e.nextElement();
            String[] value = req.getParameterValues(name);
            if (value.length > 1)
            {
                // deal with multiple's
                for (int k=0;k<value.length ;k++ )
                {
                    nvars.addElement(new NVPair(name, value[k]));
                }
            }
            else
            {
                nvars.addElement(new NVPair(name, value[0]));                
            }
        }
        NVPair[] yep = new NVPair[nvars.size()];
        int cnt = 0;
        // create the NVPair
        for (Enumeration e = nvars.elements();e.hasMoreElements() ; )
        {
            yep[cnt] = (NVPair) e.nextElement();
            cnt++;
        }
        // take advantage of code already done
        return Codecs.nv2query(yep).getBytes();
    }
}
