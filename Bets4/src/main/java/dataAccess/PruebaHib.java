package dataAccess;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import businessLogic.BLFacadeImplementation;
import domain.Event;
import domain.Question;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

public class PruebaHib {
	
	public static void main(String[] args) {
		BLFacadeImplementation blf = new BLFacadeImplementation();
		Vector<Event> eventos = blf.getEvents(new Date());
		System.out.println("Listado de eventos:");

		for (int i = 0; i < eventos.size(); i++) {
			Event ev = (Event) eventos.get(i);
			System.out.println("Id: " + ev.getEventNumber() + " Descripcion: " + ev.getDescription() + " Fecha: "
					+ ev.getEventDate());

		}
		
		try {
			Question q = blf.createQuestion(eventos.get(0), "Patata2", 123);
			System.out.println("Id: " + q.getQuestionNumber() + " Question: " + q.getQuestion() + " BetMinimum: "
					+ q.getBetMinimum());
		} catch (EventFinished e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (QuestionAlreadyExist e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
