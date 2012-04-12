package com.googlecode.wicketwebbeans.databinder.examples;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;


import org.hibernate.cfg.AnnotationConfiguration;

import net.databinder.hib.DataApplication;
import net.databinder.hib.Databinder;

import org.apache.wicket.Page;
import org.hibernate.Session;

public class ExampleApplication extends DataApplication
{
    private static String PKG_PATH = "/" + ExampleApplication.class.getPackage().getName().replace('.', '/');
    
	public ExampleApplication()
	{
	    
	}

	@Override
	public Class<? extends Page> getHomePage()
	{
		return ListPage.class;
	}
	
	@Override
	protected void init()
	{
	    super.init();
	    
	    if( isDevelopment() )
	    {
	        URL url = getClass().getResource(PKG_PATH + "/log4j.config.xml");
	        org.apache.log4j.xml.DOMConfigurator.configure(url);
	    }
	    
	    // load some example contacts
	    Session session = Databinder.getHibernateSessionFactory().openSession();
	    session.beginTransaction();
	    Map<Integer,Category> categories = new HashMap<Integer,Category>();
	    String[] names = new String[]{"Friends","Family","Business"};
	    for(int ii = 0; ii < names.length; ii++ )
	    {
	    	Category category = new Category();
		    category.setName(names[ii]);
		    session.save(category);	
		    categories.put(ii, category);
	    }

	    Random random = new Random();
	    int numberBase = 1;
	    String line = null;
	    try
	    {
		    BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(PKG_PATH + "/RandomNames.txt")));
		    while(null != (line = reader.readLine()))
		    {
		    	String[] name = line.split("\\t");
		    	Contact contact = new Contact();
			    contact.setFirstName(name[0]);
			    contact.setLastName(name[1]);
			    contact.setCategory(categories.get(random.nextInt(2)));
			    contact.setPhoneNumber(String.format("800-555-%04d",numberBase++));
			    session.save(contact);	
		    }
	    }
	    catch(IOException ex)
	    {
	    	throw new RuntimeException(ex);
	    }
	    session.getTransaction().commit();
	    session.close();
	}
	
	@Override
	protected void configureHibernate(AnnotationConfiguration config)
    {
	    super.configureHibernate(config);
        config.configure(PKG_PATH + "/hibernate.cfg.xml");
        config.addAnnotatedClass(Contact.class);
        config.addAnnotatedClass(Category.class);
    }
}
