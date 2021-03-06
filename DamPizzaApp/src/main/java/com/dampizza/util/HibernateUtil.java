/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dampizza.util;

import com.dampizza.model.entity.CredentialEntity;
import com.dampizza.model.entity.IngredientEntity;
import com.dampizza.model.entity.OrderEntity;
import com.dampizza.model.entity.ProductEntity;
import com.dampizza.model.entity.UserEntity;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate Util.
 * Provide sessions and add anotated classes to configuration.
 * @author Carlos Santos
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
                        Configuration config = new Configuration();
                        
                        // ADD ANOTATED CLASSES
                        config.addAnnotatedClass(UserEntity.class);
                        config.addAnnotatedClass(CredentialEntity.class);
                        config.addAnnotatedClass(IngredientEntity.class);
                        config.addAnnotatedClass(ProductEntity.class);
                        config.addAnnotatedClass(OrderEntity.class);
                                
                        // load hibernate.cfg.xml from different directory
			SessionFactory sessionFactory = config.configure(
					"/hibernate/hibernate.cfg.xml")
					.buildSessionFactory();

			return sessionFactory;

		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}
}
