package com.adonis.main.login;

import com.adonis.main.MainScreen;
import com.adonis.data.persons.service.PersonService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.io.Serializable;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Created by oksdud on 03.04.2017.
 */
public class LoginView extends VerticalLayout implements View {

    private HorizontalLayout loginFormLayout;
    protected LoginForm loginForm;
    public static final String NAME =  "LoginView";
    PersonService service;

    public LoginView(PersonService personService, LoginListener loginListener){
        this.service = personService;
        addStyleName("login-screen");
        loginFormLayout = new HorizontalLayout();
        VerticalLayout centeringLayout = new VerticalLayout();
        centeringLayout.setStyleName("centering-layout");

        loginForm = new LoginForm();
        loginForm.setStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        loginForm.setSizeUndefined();
        updateCaption();
        loginForm.addLoginListener(new LoginForm.LoginListener() {
            @Override
            public void onLogin(LoginForm.LoginEvent event) {
                login((LoginForm)event.getSource(), event.getLoginParameter("username"), event.getLoginParameter("password"));
            }
        });
        centeringLayout.addComponent(loginForm);
        centeringLayout.setComponentAlignment(loginForm,
                Alignment.MIDDLE_CENTER);
        loginFormLayout.addComponent(centeringLayout);
        addComponent(loginFormLayout);
    }
    protected void updateCaption() {
        float width = loginForm.getWidth();
        float height = loginForm.getHeight();

        String w = width < 0 ? "auto" : (int) width + "px";
        String h = height < 0 ? "auto" : (int) height + "px";

        loginForm.setCaption("LoginForm (" + w + "/" + h + ")");
    }
    private void login(LoginForm form, String user, String password){

        if(service.findByCustomerLogin(user).getPassword().equals(password)){
            addStyleName(ValoTheme.UI_WITH_MENU);
            getUI().getNavigator().navigateTo(MainScreen.NAME);
            return;
        }else {

        /*build logout*/
            VerticalLayout infoLayout = new VerticalLayout();

            Label info = new Label("User '" + user + "', password='" + password
                    + "' logged in");
            Button logoutButton = new Button("Log out", new Button.ClickListener() {

                @Override
                public void buttonClick(Button.ClickEvent event) {
                    Button b = event.getButton();
                    loginFormLayout.replaceComponent(b.getParent(),
                            (LoginForm) b.getData());
                }

            });
            logoutButton.setData(loginForm);

            infoLayout.addComponent(info);
            infoLayout.addComponent(logoutButton);

            loginFormLayout.replaceComponent(loginForm, infoLayout);
        }
    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }

    @Override
    public void forEach(Consumer<? super Component> action) {

    }

    @Override
    public Spliterator<Component> spliterator() {
        return null;
    }
    public interface LoginListener extends Serializable {
        void loginSuccessful();
    }

}
