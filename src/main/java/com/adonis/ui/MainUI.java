package com.adonis.ui;

import com.adonis.main.MainScreen;
import com.adonis.main.login.AccessControl;
import com.adonis.main.login.BasicAccessControl;
import com.adonis.main.login.LoginView;
import com.adonis.data.persons.service.PersonService;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Created by oksdud on 03.04.2017.
 */
@SpringUI
@Theme("mytheme")
@Widgetset("com.vaadin.v7.Vaadin7WidgetSet")
public class MainUI extends UI {

    @Autowired
    public PersonService service;

    private AccessControl accessControl = new BasicAccessControl();

    PersonUI personView;
    LoginView loginView;
    RegistrationUI registrationUI;
    MainScreen mainScreen;

    @PostConstruct
    void load() {
        if(service.findTotalCustomer()==0) {
            service.loadData();
        }
        personView = new PersonUI(service);
        loginView = new LoginView(service, new LoginView.LoginListener() {
            @Override
            public void loginSuccessful() {
                showMainView();
            }
        });
        registrationUI = new RegistrationUI(service);
        mainScreen = new MainScreen(MainUI.this);
    }


    @Override
    protected void init(VaadinRequest request) {
        Responsive.makeResponsive(this);

        setLocale(request.getLocale());
        getPage().setTitle("Car Manager");
        new Navigator(this, this);
        getNavigator().addView(LoginView.NAME, loginView);
        getNavigator().addView(RegistrationUI.NAME, registrationUI);
        getNavigator().addView(PersonUI.NAME, personView);
        getNavigator().addView(MainScreen.NAME, mainScreen);


        if (!accessControl.isUserSignedIn()) {
            getNavigator().navigateTo(LoginView.NAME);
        } else {
            showMainView();
        }


    }

    protected void showMainView() {
        addStyleName(ValoTheme.UI_WITH_MENU);
//        getNavigator().addView(MainScreen.NAME, mainScreen);
        getNavigator().navigateTo(MainScreen.NAME);
    }

    @Override
    public void forEach(Consumer<? super Component> action) {

    }

    @Override
    public Spliterator<Component> spliterator() {
        return null;
    }
}
