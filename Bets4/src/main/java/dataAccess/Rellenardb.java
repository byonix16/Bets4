package dataAccess;

import dataAccess.HibernateDataAccess;
import domain.Event;
import domain.Question;

import org.hibernate.Session;
import java.util.*;
import java.util.List;

public class Rellenardb {

	private void createAndStoreEvento(int id, String descripcion, Date fecha) {
		Session session = HibernateDataAccess.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Event e = new Event();
		e.setEventNumber(id);
		e.setDescription(descripcion);
		e.setEventDate(fecha);
		session.save(e);
		session.getTransaction().commit();
	}
	
	private void createAndStoreQuestion(int id, String descripcion,float betmin,String result, Date fecha) {
		Session session = HibernateDataAccess.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Event e = new Event();
		e.setEventNumber(id);
		e.setDescription(descripcion);
		e.setEventDate(fecha);
		session.save(e);
		session.getTransaction().commit();
	}

	private List listaEventos() {
		Session session = HibernateDataAccess.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List result = session.createQuery("from Event").list();
		session.getTransaction().commit();
		return result;
	}
	
	private List listaQuestions() {
		Session session = HibernateDataAccess.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List result = session.createQuery("from Question").list();
		session.getTransaction().commit();
		return result;
	}
	
	public void sup() {
		Rellenardb e = new Rellenardb();
		System.out.println("Creación de eventos:");
		
		e.createAndStoreEvento(1, "Pepe ha hecho login correctamente", new Date());
		e.createAndStoreEvento(2, "Nerea ha intentado hacer login", new Date());
		e.createAndStoreEvento(3, "Kepa ha hecho login correctamente", new Date());
		System.out.println("Listado de eventos:");

		List eventos = e.listaEventos();
		for (int i = 0; i < eventos.size(); i++) {
			Event ev = (Event) eventos.get(i);
			System.out.println("Id: " + ev.getEventNumber() + " Descripcion: " + ev.getDescription() + " Fecha: "
					+ ev.getEventDate());

		}
	}
	
	public static void main(String[] args) {
		Rellenardb e = new Rellenardb();
		System.out.println("Creación de eventos:");
		
		Session session = HibernateDataAccess.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Event e1 = new Event(1, "Pepe ha hecho login correctamente", new Date());
		List<Question> questions = new ArrayList<Question>();
		Question q1 = new Question(1,"1", 3,e1);
		Question q2 = new Question(1,"x", 3,e1);
		Question q3 = new Question(1,"2", 3,e1);
		questions.add(q1);
		questions.add(q2);
		questions.add(q3);
		e1.setQuestions(questions);
		session.save(e1);
		session.save(q1);
		session.save(q2);
		session.save(q3);
		Event e2 = new Event(2, "Nerea ha intentado hacer login", new Date());
		session.save(e2);
		Event e3 = new Event(3, "Kepa ha hecho login correctamente", new Date());
		session.save(e3);
		
		session.getTransaction().commit();
//		e.createAndStoreEvento(1, "Pepe ha hecho login correctamente", new Date());
//		e.createAndStoreEvento(2, "Nerea ha intentado hacer login", new Date());
//		e.createAndStoreEvento(3, "Kepa ha hecho login correctamente", new Date());
		System.out.println("Listado de eventos:");

		List eventos = e.listaEventos();
		for (int i = 0; i < eventos.size(); i++) {
			Event ev = (Event) eventos.get(i);
			System.out.println("Id: " + ev.getEventNumber() + " Descripcion: " + ev.getDescription() + " Fecha: "
					+ ev.getEventDate());

		}
		
		List preg = e.listaQuestions();
		for (int i = 0; i < preg.size(); i++) {
			Question qu = (Question) preg.get(i);
			System.out.println("Id: " + qu.getQuestionNumber() + " Descripcion: " + qu.getQuestion() + " Minbet: "
					+ qu.getBetMinimum());

		}
	}
}
