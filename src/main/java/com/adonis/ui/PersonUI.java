package com.adonis.ui;

import com.adonis.persons.Person;
import com.adonis.persons.service.PersonService;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.UI;
import com.vaadin.ui.components.grid.EditorSaveEvent;
import com.vaadin.ui.components.grid.EditorSaveListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by oksdud on 29.03.2017.
 */
@SpringUI
@Theme("valo")
public class PersonUI extends UI {

    @Autowired
    private PersonService service;

    private Person customer;
    com.adonis.ui.PersonView editor = new PersonView(this::savePerson, this::deletePerson);
    HorizontalSplitPanel splitter = new HorizontalSplitPanel();
    private Grid<Person> grid = new Grid();

    @Override
    protected void init(VaadinRequest request) {
        updateGrid();
        grid.addColumn(Person::getFirstName).setCaption("First name");
        grid.addColumn(Person::getLastName).setCaption("Last name");
        grid.addColumn(Person::getEmail).setCaption("Email");
        grid.addColumn(Person::getLocalDateBirthDay).setCaption("BirthDay");

        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addSelectionListener(e -> updateForm());
        grid.getEditor().addSaveListener(new EditorSaveListener() {
            @Override
            public void onEditorSave(EditorSaveEvent event) {
                List<Person> listPersons = (List<Person>)event.getGrid().getSelectedItems();
                listPersons.forEach(person -> service.update(person));
            }
        });
        editor.setSizeFull();

        splitter.setFirstComponent(grid);
        splitter.setSecondComponent(editor);

        setContent(splitter);

    }

    private void updateForm() {
        if (!grid.getSelectedItems().isEmpty()) {
            customer = (Person) grid.getSelectionModel().getFirstSelectedItem().get();
            customer = service.findByFirstLastNameEmail(customer.getFirstName(), customer.getLastName(), customer.getEmail());
            editor.setPerson(customer);
            grid.getEditor().save();
        }
    }
    private void updateGrid() {
        List customers = service.findAll();
        grid.setItems(customers);
    }

    private void savePerson(Person person) {
        service.update(person);
        updateGrid();
    }

    private void deletePerson(Person person) {
        service.delete(person);
        updateGrid();
    }

}
