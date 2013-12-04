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
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

@SuppressWarnings("serial")
public class RechercherPersonneServlet extends HttpServlet {
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		String nom = req.getParameter("nom");
		
		Query query = new Query("Employee");
		query.addFilter("nom", FilterOperator.EQUAL, nom);
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		PreparedQuery pq = datastore.prepare(query);
		Iterator<Entity> entities = pq.asIterable().iterator();
		Entity entity = null;
		while(entities.hasNext()){
			entity = entities.next();
		}
		if(entity != null){
			
			System.out.println("Envoi demande exécution de la tache de fond");
			
			Queue queue = QueueFactory.getDefaultQueue();
			queue.add(TaskOptions.Builder.withUrl("/tacheDeFond").method(Method.POST).param("nom", nom));
			
			System.out.println("Fin envoi demande exécution de la tache de fond");
			
			try {
				req.setAttribute("nom", nom);
				getServletContext().getRequestDispatcher("/confirmation.jsp").forward(req, resp);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		
	}
}
