package modelo.bean;

import domain.Event;
import domain.Question;

import java.util.Date;
import java.util.Vector;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;

import org.primefaces.event.SelectEvent;
import businessLogic.BLFacadeImplementation;

@ManagedBean(name = "queryBean")
@ViewScoped
public class QueryBean {

	private Vector<Event> eventos;
	private BLFacadeImplementation blf;
	private Date fecha = null;
	private Event event;
	private String eventstr;
	private Vector<Question> questions;

	public Vector<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Vector<Question> questions) {
		this.questions = questions;
	}

	public QueryBean() {
		// TODO Auto-generated constructor stub
		blf = InicioBean.getBLF();
		eventos = blf.getEvents(new Date());
		eventstr = "";
	}

	public void getEventsDay() {
		if (fecha != null) {
			eventos = blf.getEvents(fecha);
			System.out.println("Eventos: ");
			for (Event i : eventos) {
				System.out.println(i);
			}
		} else {
			System.out.println("No hay fecha");
		}
	}

	public Vector<Event> getEventos() {
		return eventos;
	}

	public void setEventos(Vector<Event> eventos) {
		this.eventos = eventos;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Vector<Event> getEvents() {
		return eventos;
	}

	public void setDate(SelectEvent event) {
		this.fecha = (Date) event.getObject();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Fecha escogida: " + fecha));
		getEventsDay();
	}
	
	public void onEventSelect() {
        System.out.println("Evento seleccionado: " + eventstr);
    }
	
	public void getEvento() {
		System.out.println("id: " + eventstr);
	}

	public String getEventstr() {
		return eventstr;
	}

	public void setEventstr(String eventstr) {
		this.eventstr = eventstr;
	}

	public void eventodesdevar() {
		Event ev = null;
		try {
			for (Event i : eventos) {
				Boolean mayor = ((Integer.valueOf(eventstr).intValue() - i.getEventNumber().intValue()) > -1);
				Boolean menor = ((Integer.valueOf(eventstr).intValue() - i.getEventNumber().intValue()) < 1);
				if (mayor && menor)
					ev = i;
			}

			if (ev == null)
				System.out.println("No existe");
			else {
				System.out.println("Evento" + ev);
				event = ev;
			}
		} catch (Exception e) {
			System.out.println("No existe");
		}
	}

	public void queryQuestions() {

		eventodesdevar();
		if (event != null) {
			questions = new Vector<Question>(event.getQuestions());
			System.out.println("Preguntas: ");
			System.out.println("Evento: " + event);
			System.out.println("Preguntas: " + questions.size());
		} else {
			questions = new Vector<Question>();
			System.out.println("Datos insuficientes");
			System.out.println("Evento: " + event);
		}

	}

	public String volverInicio() {
		return "Inicio";
	}
	
}
