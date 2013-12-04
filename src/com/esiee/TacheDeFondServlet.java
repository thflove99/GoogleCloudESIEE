package com.esiee;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

@SuppressWarnings("serial")
public class TacheDeFondServlet extends HttpServlet {
	
	static int compteur = 0;
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		System.out.println("Debut tache de fond.");
		 
		if(compteur < 3){
			System.out.println("Tache en erreur : elle va être relancée !");
			resp.sendError(HttpServletResponse.SC_FORBIDDEN);
			compteur++;
		}else{
			
			for(int i=0; i<10; i++){
				System.out.println("Je calcule ...");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
			
			
			System.out.println("La tache se termine normalement");
		}
		
	}
}
