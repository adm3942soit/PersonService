package com.adonis.ui;

import com.adonis.persons.Person;
import com.vaadin.data.Binder;
import com.vaadin.server.ExternalResource;

public class PersonView extends PersonDesign {

	public interface PersonSaveListener {
		void savePerson(Person person);
	}

	public interface PersonDeleteListener {
		void deletePerson(Person person);
	}

	Binder<Person> binder = new Binder<>(Person.class);

	public PersonView(PersonSaveListener saveEvt, PersonDeleteListener delEvt) {

		binder.forField(dateOfBirth).bind("dateOfBirth");

		binder.bindInstanceFields(this );

		save.addClickListener(evt -> {
			try {
//				binder.writeBean(binder.getBean());
				saveEvt.savePerson(binder.getBean());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		cancel.addClickListener(evt -> {

		});

		delete.addClickListener(evt -> {
			delEvt.deletePerson(binder.getBean());
		});
	}

	public void setPerson(Person selectedRow) {
		binder.setBean(selectedRow);
		picture.setSource(new ExternalResource(selectedRow.getPicture()));
	}

}
