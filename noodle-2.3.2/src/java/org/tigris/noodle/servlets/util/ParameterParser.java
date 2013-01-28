package org.tigris.noodle.servlets.util;

/*
 * Copyright (c) 1997-1999 The Java Apache Project.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. All advertising materials mentioning features or use of this
 *    software must display the following acknowledgment:
 *    "This product includes software developed by the Java Apache
 *    Project for use in the Apache JServ servlet engine project
 *    <http://java.apache.org/>."
 *
 * 4. The names "Apache JServ", "Apache JServ Servlet Engine", "Turbine",
 *    "Apache Turbine", "Turbine Project", "Apache Turbine Project" and
 *    "Java Apache Project" must not be used to endorse or promote products
 *    derived from this software without prior written permission.
 *
 * 5. Products derived from this software may not be called "Apache JServ"
 *    nor may "Apache" nor "Apache JServ" appear in their names without
 *    prior written permission of the Java Apache Project.
 *
 * 6. Redistributions of any form whatsoever must retain the following
 *    acknowledgment:
 *    "This product includes software developed by the Java Apache
 *    Project for use in the Apache JServ servlet engine project
 *    <http://java.apache.org/>."
 *
 * THIS SOFTWARE IS PROVIDED BY THE JAVA APACHE PROJECT "AS IS" AND ANY
 * EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE JAVA APACHE PROJECT OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Java Apache Group. For more information
 * on the Java Apache Project and the Apache JServ Servlet Engine project,
 * please see <http://java.apache.org/>.
 *
 */

// Java Core Classes
import java.io.*;
import java.util.*;
import java.beans.*;
import java.lang.reflect.*;
import java.text.*;

// Java Servlet Classes
import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 *    ParameterParser is a utility object to handle 
 *    parsing and retrieving the data passed via the 
 *    GET/POST/PATH_INFO arguments.
<p>
NOTE: The name= portion of a name=value pair will be converted to
      lowercase when the object is initialized and when new data is added.
      This may overwrite existing name=value pairs:
      <PRE>
           ParameterParser pp = data.getParameters();
           pp.add("ERROR",1);
           pp.add("eRrOr",2);
           int result = pp.getInt("ERROR");
      </PRE>
      In the above example, result is 2.
 *
 *
 *    @author Jon S. Stevens <a href="mailto:jon@clearink.com">jon@clearink.com</a>
 *    @version $Id: ParameterParser.java,v 1.1 2002/03/15 18:06:21 leonardr Exp $
 *
 */
public class ParameterParser extends Hashtable
{
    private HttpServletRequest req = null;
    /**
        Create a new instance of ParameterParser. This 
        requires a valid HttpServletRequest object. It will attempt 
        to parse out the GET/POST/PATH_INFO data and place the data 
        into itself which is an extension of Hashtable. There are 
        convenience methods for retrieving the data as a number of 
        different datatypes. The PATH_INFO data must be a URLEncoded() 
        string.
        <p>
        To add things into this object, use the add() method and not 
        the Hashtable.put() method.        
    */
    public ParameterParser(HttpServletRequest req)
    {
        this.req = req;

        // string object re-use at its best
        String tmp = null;
        
        Enumeration names = req.getParameterNames();
        if ( names != null )
        {
            while(names.hasMoreElements())
            {
                tmp = (String) names.nextElement();
                this.put( convert(tmp), (Object) req.getParameterValues(tmp) );
            }
        }
        
        // also cache any pathinfo variables that are passed 
        // around as if they are query string data.
        try
        {
            StringTokenizer st = new StringTokenizer(req.getPathInfo(), "/");
            boolean name = true;
            String tmp2 = null;
            while(st.hasMoreTokens())
            {
                if ( name == true )
                {
                    tmp = JServUtils.URLDecode(st.nextToken());
                    name = false;
                }
                else
                {
                    tmp2 = JServUtils.URLDecode(st.nextToken());
                    if ( tmp.length() != 0 )
                    {
                        add (convert(tmp), tmp2);
                    }
                    name = true;
                }
            }
        }
        catch ( Exception e )
        {
            // if anything goes wrong above, don't worry about it
            // chances are that the path info was wrong anyways
            // and things that depend on it being right will fail
            // later and should be caught later
        }
    }
    /**
        Add a name/value pair into this object.
        The reason for using an add() method instead of 
        Hashtable.put is because things must be in a String[].
    */
    public void add ( String name, double value )
    {
        add ( name, Double.toString(value));
    }
    /**
        Add a name/value pair into this object.
        The reason for using an add() method instead of 
        Hashtable.put is because things must be in a String[].
    */
    public void add ( String name, int value )
    {
        add ( name, Integer.toString(value));
    }
    /**
        Add a name/value pair into this object.
        The reason for using an add() method instead of 
        Hashtable.put is because things must be in a String[].
    */
    public void add ( String name, String value )
    {
        String[] strtmp = new String[1];
        strtmp[0] = value;
        this.put ( convert(name), (String[]) strtmp);
    }
    /**
        Trims the string data and converts it to lowercase. It returns
        a new string so that it does not destroy the value data.
        
        @return String a new string converted to lowercase and trimmed
    */
    public static String convert ( String value )
    {
        return (value.trim().toLowerCase());
    }
    /**
        All keys are stored in lowercase strings, so override method
        to account for this
    */
    public boolean containsKey( Object key )
    {
        return super.containsKey(convert((String)key));
    }
    /**
        Check for existence of key_day, key_month and key_year parameters
        (as returned by DateSelector generated HTML)
    public boolean containsDateSelectorKeys(String key)
    {
        return containsKey(key + DateSelector.DAY_SUFFIX)
            && containsKey(key + DateSelector.MONTH_SUFFIX)
            && containsKey(key + DateSelector.YEAR_SUFFIX);
    }
    */
    /**
        Return a boolean for the given name. If the name does not 
        exist, return false.
    */
    public boolean getBoolean(String name)
    {
        boolean value = false;
        Object object = this.get(convert(name));
        if (object != null)
        {
            String tmp = getString(name);
            if ( tmp.equalsIgnoreCase ("1") ||
                 tmp.equalsIgnoreCase ("true") )
            {
                value = true;
            }
        }
        return value;
    }
    /**
        Return a boolean for the given name. If the name does not 
        exist, return defaultValue.
    */
    public boolean getBoolean(String name, boolean defaultValue)
    {
        boolean value = defaultValue;
        Object object = this.get(convert(name));
        if (object != null)
        {
            String tmp = getString(name);
            if ( tmp.equalsIgnoreCase ("1") ||
                 tmp.equalsIgnoreCase ("true") )
            {
                value = true;
            }
        }
        return value;
    }
    /**
        Return a Boolean for the given name. If the name does not 
        exist, return false.
    */
    public Boolean getBool(String name)
    {
        boolean value = false;
        Object object = this.get(convert(name));
        if (object != null)
        {
            String tmp = getString(name);
            if ( tmp.equalsIgnoreCase ("1") ||
                 tmp.equalsIgnoreCase ("true") )
            {
                value = true;
            }
        }
        return new Boolean(value);
    }
    /**
        Return a Boolean for the given name. If the name does not 
        exist, return defaultValue.
    */
    public Boolean getBool(String name, boolean defaultValue)
    {
        boolean value = defaultValue;
        Object object = this.get(convert(name));
        if (object != null)
        {
            String tmp = getString(name);
            if ( tmp.equalsIgnoreCase ("1") ||
                 tmp.equalsIgnoreCase ("true") )
            {
                value = true;
            }
        }
        return new Boolean(value);
    }
    /**
        Return a double for the given name. If the name does not 
        exist, return 0.0.
    */
    public double getDouble(String name)
    {
        double value = 0.0;
        try
        {
            Object object = this.get(convert(name));
            if (object != null)
                value = Double.valueOf(((String[])object)[0]).doubleValue();
        }
        catch (NumberFormatException exception)
        {
        }
        return value;
    }
    /**
        Return a double for the given name. If the name does not 
        exist, return defaultValue.
    */
    public double getDouble(String name, double defaultValue)
    {
        double value = defaultValue;
        try
        {
          Object object = this.get(convert(name));
          if (object != null)
            value = Double.valueOf(((String[])object)[0]).doubleValue();
        }
        catch (NumberFormatException exception)
        {
        }
        return value;
    }
    /**
        Return an int for the given name. If the name does not 
        exist, return 0.
    */
    public int getInt(String name)
    {
        int value = 0;
        try
        {
            Object object = this.get(convert(name));
            if (object != null)
                value = Integer.valueOf(((String[])object)[0]).intValue();
        }
        catch (NumberFormatException exception)
        {
        }
        return value;
    }
    /**
        Return an int for the given name. If the name does not 
        exist, return defaultValue.
    */
    public int getInt(String name, int defaultValue )
    {
        int value = defaultValue;
        try
        {
            Object object = this.get(convert(name));
            if (object != null)
                value = Integer.valueOf(((String[])object)[0]).intValue();
        }
        catch (NumberFormatException exception)
        {
        }
        return value;
    }
    /**
        returns an Integer from an int
    */
    public Integer getInteger(String name)
    {
        return new Integer(getInt(name));
    }    
    /**
        returns an Integer from an int
    */
    public Integer getInteger(String name, int def)
    {
        return new Integer(getInt(name, def));
    }
    /**
        returns an Integer from an int. you cannot pass in a null here for 
        the default value.
    */
    public Integer getInteger(String name, Integer def)
    {
        return new Integer(getInt(name, def.intValue()));
    }
    /**
        Return an array of ints for the given name. If the name does not 
        exist, return null.
    */
    public int[] getInts(String name)
    {
        int[] value = null;
        Object object = getStrings(convert(name));
        if (object != null)
        {
            String[] temp = (String[])object;
            value = new int[temp.length];
            for (int i=0; i<temp.length; i++)
                value[i] = Integer.parseInt( temp[i] );
        }
        return value;
    }
    /**
        Return an array of Integers for the given name. If the name does not 
        exist, return null.
    */
    public Integer[] getIntegers(String name)
    {
        Integer[] value = null;
        Object object = getStrings(convert(name));
        if (object != null)
        {
            String[] temp = (String[])object;
            value = new Integer[temp.length];
            for (int i=0; i<temp.length; i++)
                value[i] = Integer.valueOf( temp[i] );
        }
        return value;
    }
    /**
        Return a long for the given name. If the name does not 
        exist, return 0.
    */
    public long getLong(String name)
    {
        long value = 0;
        try
        {
            Object object = this.get(convert(name));
            if (object != null)
                value = Long.valueOf(((String[])object)[0]).longValue();
        }
        catch (NumberFormatException exception)
        {
        }
        return value;
    }
    /**
        Return a long for the given name. If the name does not 
        exist, return defaultValue.
    */
    public long getLong(String name, long defaultValue )
    {
        long value = defaultValue;
        try
        {
            Object object = this.get(convert(name));
            if (object != null)
                value = Long.valueOf(((String[])object)[0]).longValue();
        }
        catch (NumberFormatException exception)
        {
        }
        return value;
    }
    /**
        Return a String for the given name. If the name does not 
        exist, return null.
    */
    public String getString(String name)
    {
        try
        {
            String value = null;
            Object object = this.get(convert(name));
            if (object != null)
                value = ((String[])object)[0];
            return value;
        }
        catch ( ClassCastException e )
        {
            return null;
        }
    }
    /**
        Return a String for the given name. If the name does not 
        exist, return the defaultValue.
    */
    public String getString(String name, String defaultValue)
    {
        String value = getString(name);
        if (value == null || value.length() == 0 || value.equals("null"))
            return defaultValue;
        else
            return value;
    }
    /**
        Return an array of Strings for the given name. If the name does not 
        exist, return null.
    */
    public String[] getStrings(String name)
    {
        String[] value = null;
        Object object = this.get(convert(name));
        if (object != null)
            value = ((String[])object);
        return value;
    }
    /**
        Return an array of Strings for the given name. If the name does not 
        exist, return the defaultValue.
    */
    public String[] getStrings(String name, String[] defaultValue)
    {
        String[] value = getStrings(name);
        if (value == null || value.length == 0)
            return defaultValue;
        else
            return value;
    }
    /**
        Return an array of bytes for a given name according
        to the HttpRequest's character encoding.
    */
    public byte[] getBytes(String name)
        throws UnsupportedEncodingException
    {
        String tempStr = getString(name);
        if ( tempStr != null )
            return tempStr.getBytes(req.getCharacterEncoding());
        return null;
    }
    /**
        Returns a java.util.Date object. If there are DateSelector style
        parameters then these are used. If not and there is a parameter 'name'
        then this is parsed by DateFormat.
    public Date getDate(String name) throws ParseException
    {
        Date date = null;

        if (containsDateSelectorKeys(name))
        {
            try
            {
                Calendar cal =  new GregorianCalendar(
                        getInt(name + DateSelector.YEAR_SUFFIX),
                        getInt(name + DateSelector.MONTH_SUFFIX),
                        getInt(name + DateSelector.DAY_SUFFIX));
                cal.setLenient(false);
                // reject invalid dates
                date = cal.getTime();
            }
            catch (IllegalArgumentException e)
            {
                // thrown if an invalid date
            }
        }
        else if (containsKey(name))
        {
            try
            {
                // could determine a locale to pass to getDateInstance
                DateFormat df = DateFormat.getDateInstance();
                // reject invalid dates
                df.setLenient(false);
                date = df.parse(getString(name));
            }
            catch (ParseException e)
            {
                // thrown if couldn't parse date
            }
        }

        return date;
    }
    */
    
    /**
        Uses bean introspection to set writable properties of bean from
        the parameters, where a (case-insensitive) name match between the 
        bean property and the parameter is looked for.
    public void setProperties(Object bean) throws Exception
    {
        Class beanClass = bean.getClass();
        PropertyDescriptor[] props 
            = Introspector.getBeanInfo(beanClass).getPropertyDescriptors();

        for (int i = 0; i < props.length; i++) 
        {
            String propname = props[i].getName();
            Method setter = props[i].getWriteMethod();
            if (setter != null
                    && (containsKey(propname) 
                        || containsDateSelectorKeys(propname)))
            {
                setProperty(bean, props[i]);
            }
        }
    }
    */
    /**
        Set the property 'prop' in the bean to the value of the 
        corresponding parameter. Supports all types supported by
        getXXX methods plus a few more that come for free because
        primitives have to be wrapped before being passed to invoke 
        anyway.
    private void setProperty(Object bean, PropertyDescriptor prop) 
        throws Exception
    {
        if (prop instanceof IndexedPropertyDescriptor)
        {
            throw new Exception(
                    prop.getName() + " is an indexed property (not supported)");
        }

        Method setter = prop.getWriteMethod();
        if (setter == null)
        {
            throw new Exception(
                    prop.getName() + " is a read only property");
        }

        Class propclass = prop.getPropertyType();
        Object[] args = { null };
        
        if (propclass == Boolean.class || propclass == Boolean.TYPE)
            args[0] = getBool(prop.getName());
        else if (propclass == Double.class  || propclass == Double.TYPE)
            args[0] = new Double(getDouble(prop.getName()));
        else if (propclass == Integer.class || propclass == Integer.TYPE)
            args[0] = getInteger(prop.getName());
        else if (propclass == Long.class    || propclass == Long.TYPE) 
            args[0] = new Long(getLong(prop.getName()));
        else if (propclass == String.class)
            args[0] = getString(prop.getName());
        else if (propclass == int[].class)
            args[0] = getInts(prop.getName());
        else if (propclass == Integer[].class) 
            args[0] = getIntegers(prop.getName());
        else if (propclass == String[].class) 
            args[0] = getStrings(prop.getName());
        else if (propclass == Date.class) 
            args[0] = getDate(prop.getName());
        else 
        {
            throw new Exception("property " 
                                + prop.getName() 
                                + " is of unsupported type "
                                + propclass.toString());
        }

        setter.invoke(bean, args);
    }
    */
    /**
        Simple method that attempts to get a toString()
        representation of this object. It doesn't do well
        with String[]'s though
    */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        for (Enumeration e = this.keys() ; e.hasMoreElements() ;)
        {
            String name = (String) e.nextElement();
            try
            {
                sb.append ("{");
                sb.append(name);
                sb.append("=");
                sb.append(this.getString(name));
                sb.append ("}\n");
            }
            catch ( Exception ee)
            {
                try
                {
                    sb.append ("{");
                    sb.append(name);
                    sb.append("=");
                    sb.append ("ERROR?");
                    sb.append ("}\n");
                }
                catch ( Exception eee ) {}
            }
        }
        return sb.toString();
    }
}
