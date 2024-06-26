package businessLogic;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.hibernate.Session;

import dataAccess.DataAccessInterface;
import dataAccess.HibernateDataAccess;
import domain.Question;
import domain.Event;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the business logic as a web service.
 */

public class BLFacadeImplementation implements BLFacade {
	DataAccessInterface dbManager;

	public BLFacadeImplementation() {

	}

	public BLFacadeImplementation(DataAccessInterface da) {
		
	}

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished        if current data is after data of the event
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */

	public Question createQuestion(Event event, String question, float betMinimum)
			throws EventFinished, QuestionAlreadyExist {
		System.out.println("Crear pregunta");

		Session session = HibernateDataAccess.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		String q = question.replace("'", "''");
		List l = session.createQuery("from Question where question = '" + q + "'").list();
		if (l.size() < 1) {
			Question q1 = event.addQuestion(question, betMinimum);

			session.update(event);

			session.save(q1);

			session.getTransaction().commit();
			return q1;
		} else {
			System.out.println("La pregunta ya existe");
			session.getTransaction().rollback();
			return null;
		}
	};

	/**
	 * This method invokes the data access to retrieve the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */

	public Vector<Event> getEvents(Date date) {
		System.out.println("Coger Eventos");

		Session session = HibernateDataAccess.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		System.out.println("Dia: " + localDate.getYear() + localDate.getMonthValue() + localDate.getDayOfMonth());
		List l = session
				.createQuery("from Event where YEAR(eventDate) = " + localDate.getYear() + " and MONTH(eventDate) = "
						+ localDate.getMonthValue() + " and DAY(eventDate) = " + localDate.getDayOfMonth())
				.list();
		System.out.println("Dias: " + l.size());
		Vector<Event> eventos = new Vector<Event>();
		for (int i = 0; i < l.size(); i++) {
			Event ev = (Event) l.get(i);
			eventos.add(ev);
		}
		session.getTransaction().commit();
		return eventos;

	}

	/**
	 * This method invokes the data access to retrieve the dates a month for which
	 * there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */

	public Vector<Date> getEventsMonth(Date date) {
		System.out.println("Coger Eventos");

		Session session = HibernateDataAccess.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		System.out.println("Dia: " + localDate.getYear() + localDate.getMonthValue() + localDate.getDayOfMonth());

		List l = session
				.createQuery("from Event where YEAR(eventDate) = " + localDate.getYear() + " and MONTH(eventDate) = "
						+ localDate.getMonthValue())
				.list();
		System.out.println("Dias: " + l.size());
		Vector<Date> dates = new Vector<Date>();
		for (int i = 0; i < l.size(); i++) {
			Date da = (Date) l.get(i);
			dates.add(da);
		}
		session.getTransaction().commit();
		return dates;
	}

	public void close() {

	}

	/**
	 * This method invokes the data access to initialize the database with some
	 * events and questions. It is invoked only when the option "initialize" is
	 * declared in the tag dataBaseOpenMode of resources/config.xml file
	 */

	public void initializeBD() {

	}

}
