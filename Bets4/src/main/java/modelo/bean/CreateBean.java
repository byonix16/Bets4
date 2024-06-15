package modelo.bean;

import domain.Event;
import domain.Question;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import businessLogic.BLFacadeImplementation;



public class CreateBean {

	private List<Event> eventos;
	private BLFacadeImplementation blf;
	private Date fecha = null;
	private Event event;
	private String eventstr;
	private String descripcion;
	private String minbet;

	public CreateBean() {
		blf = InicioBean.getBLF();
		eventos = blf.getEvents(new Date());
		eventstr = "";
	}

	public void getEventsDay() {
		eventstr = null;
		if (fecha != null) {
			eventos = blf.getEvents(fecha);
			System.out.println("Eventos: ");
			for (Event i : eventos) {
				System.out.println(i);
			}
			eventstr = null;
		} else {
			System.out.println("No hay eventos");
		}
	}

	public List<Event> getEventos() {
		return eventos;
	}

	public void setEventos(List<Event> eventos) {
		this.eventos = eventos;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public List<Event> getEvents() {
		return eventos;
	}

	public void setDate(SelectEvent event) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Fecha escogida: " + event.getObject()));
		System.out.println(event.getObject());
		fecha = (Date) event.getObject();
		getEventsDay();
		eventstr = null;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getMinbet() {
		return minbet;
	}

	public void setMinbet(String minbet) {
		this.minbet = minbet;
	}

	public void createQuestion() {
		try {
			eventodesdevar();
			if (event != null && descripcion != null && minbet != null) {
				if (descripcion.length() > 0) {					
					Question preg = blf.createQuestion(event, descripcion, Integer.valueOf(minbet));
					System.out.println("Pregunta creada: " + preg.getQuestion());
				} else
					System.out.println("Datos insuficientes");
			} else
				System.out.println("Datos insuficientes");
			System.out.println("Evento: " + event);
			System.out.println("Descripcion: " + descripcion);
			System.out.println("Minbet: " + minbet);
		} catch (NumberFormatException e) {
			System.out.println("Bet minimun debe ser un numero");
		} catch (Exception e) {
			System.out.println("Ya existe un evento con esta descripcion");
		}
	}
	
	public String volverInicio() {
		return "Inicio";
	}
}
