package HealthTracker;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //the temporary Data structure holding example users
        UserMap data = new UserMap();


        /**********************************************************
         * the first Scene- the Log In screen
         *
         *******************************************************/
        Scene scene = new Scene(new Group(), 400, 700);

        //objects in the first scene
        Rectangle rect = new Rectangle(400, 100);
        rect.getStyleClass().add("topBorder");

        Text title = new Text("TRACK");
        title.getStyleClass().add("title");

        Text header = new Text("LOG IN");
        header.getStyleClass().add("headerL");


        TextField textField = new TextField();
        textField.getStyleClass().add("textbox1");

        PasswordField textField2 = new PasswordField();
        textField2.getStyleClass().add("textbox2");

        Button logInButton = new Button("Log In");
        logInButton.setPrefWidth(150);
        logInButton.setPrefHeight(30);
        logInButton.getStyleClass().add("logInButton");

        Button createAccButton = new Button("Create Account");
        createAccButton.setPrefWidth(150);
        createAccButton.setPrefHeight(30);
        createAccButton.getStyleClass().add("createAccButton");

        Text userField = new Text("Username:");
        userField.getStyleClass().add("userField");

        Text passField = new Text("Password:");
        passField.getStyleClass().add("passField");



        ((Group)scene.getRoot()).getChildren().add(rect);
        //((Group)scene.getRoot()).getChildren().add(vbox2);
        ((Group)scene.getRoot()).getChildren().add(title);
        ((Group)scene.getRoot()).getChildren().add(header);
        ((Group)scene.getRoot()).getChildren().add(textField);
        ((Group)scene.getRoot()).getChildren().add(textField2);
        ((Group)scene.getRoot()).getChildren().add(userField);
        ((Group)scene.getRoot()).getChildren().add(passField);
        ((Group)scene.getRoot()).getChildren().add(logInButton);
        ((Group)scene.getRoot()).getChildren().add(createAccButton);

        logInButton.setOnAction(action -> {
            String usernameEntered = textField.getText();
            String passwordEntered = textField2.getText();

            try {
                User user = data.getUser(usernameEntered, passwordEntered);
                System.out.println("Successful Log in of " + user.getUsername() + ", " + user.getPassword());

                /******************************************************************************
                 * This part needs to connect to the home page with this user logged in.
                 *
                 ******************************************************************************/

            }
            catch(wrongPasswordException e) {
                Button errorWindow = new Button("The password entered was not correct. Please try again.");
                errorWindow.getStyleClass().add("errorWindow");
                errorWindow.setPrefWidth(250);
                errorWindow.setPrefHeight(250);
                errorWindow.setWrapText(true);
                ((Group) scene.getRoot()).getChildren().add(errorWindow);
                errorWindow.setOnAction(actionEvent ->  {
                    ((Group) scene.getRoot()).getChildren().remove(errorWindow);
                });
            }
            catch(usernameNotFoundException e){
                Button errorWindow = new Button("This username was not found. Please Enter Again.");
                errorWindow.getStyleClass().add("errorWindow");
                errorWindow.setPrefWidth(250);
                errorWindow.setPrefHeight(250);
                errorWindow.setWrapText(true);
                ((Group) scene.getRoot()).getChildren().add(errorWindow);
                errorWindow.setOnAction(actionEvent ->  {
                    ((Group) scene.getRoot()).getChildren().remove(errorWindow);
                });
            }

        });

        /*******************************************************************
         * The second Scene- Creating an account
         *
         *****************************************************************/
        Scene scene2 = new Scene(new Group(), 400, 700);

        Rectangle rect2 = new Rectangle(400, 100);
        rect2.getStyleClass().add("topBorder");

        Text title2 = new Text("TRACK");
        title2.getStyleClass().add("title");

        Text header2 = new Text("JOIN");
        header2.getStyleClass().add("headerL");

        Text createAccount = new Text("Create Account");
        createAccount.getStyleClass().add("boldTopOfPage");

        Text enterUsername = new Text("Enter a Username:");
        enterUsername.getStyleClass().add("enterUsername");

        TextField usernameTextField = new TextField();
        usernameTextField.getStyleClass().add("textbox1");


        /**************************************************************
         * Button that checks if the username is taken, or too short/long
         **************************************************************/
        Button checkButton = new Button("Check");
        checkButton.getStyleClass().add("checkButton");
        checkButton.setOnAction(action -> {
            String usernameEntered = usernameTextField.getText();
            if(data.containsUsername(usernameEntered)) {
                Button errorWindow = new Button("The username is taken. Please try a new one.");
                errorWindow.getStyleClass().add("errorWindow2");
                errorWindow.setPrefWidth(250);
                errorWindow.setPrefHeight(250);
                errorWindow.setWrapText(true);
                ((Group) scene2.getRoot()).getChildren().add(errorWindow);
                errorWindow.setOnAction(actionEvent -> {
                    ((Group) scene2.getRoot()).getChildren().remove(errorWindow);
                });
            }
            else if(usernameEntered.length() < 3){
                Button errorWindow = new Button("The username is too short. Please try a new one.");
                errorWindow.getStyleClass().add("errorWindow2");
                errorWindow.setPrefWidth(250);
                errorWindow.setPrefHeight(250);
                errorWindow.setWrapText(true);
                ((Group) scene2.getRoot()).getChildren().add(errorWindow);
                errorWindow.setOnAction(actionEvent -> {
                    ((Group) scene2.getRoot()).getChildren().remove(errorWindow);
                });
            }
            else if(usernameEntered.length() > 20){
                Button errorWindow = new Button("The username is too long. Please try a new one.");
                errorWindow.getStyleClass().add("errorWindow2");
                errorWindow.setPrefWidth(250);
                errorWindow.setPrefHeight(250);
                errorWindow.setWrapText(true);
                ((Group) scene2.getRoot()).getChildren().add(errorWindow);
                errorWindow.setOnAction(actionEvent -> {
                    ((Group) scene2.getRoot()).getChildren().remove(errorWindow);
                });
            }
            else {
                Button errorWindow = new Button("The username is available.");
                errorWindow.getStyleClass().add("errorWindow2");
                errorWindow.setPrefWidth(250);
                errorWindow.setPrefHeight(250);
                errorWindow.setWrapText(true);
                ((Group) scene2.getRoot()).getChildren().add(errorWindow);
                errorWindow.setOnAction(actionEvent -> {
                    ((Group) scene2.getRoot()).getChildren().remove(errorWindow);
                });
            }

        });

        Text enterPassword = new Text("Choose a Password:");
        enterPassword.getStyleClass().add("enterPassword");

        PasswordField passwordTextField = new PasswordField();
        passwordTextField.getStyleClass().add("passwordTextField");

        /**************************************************************
         * Button that checks if password is okay
         **************************************************************/
        Button passwordCheckButton = new Button("Check");
        passwordCheckButton.getStyleClass().add("passwordCheckButton");
        passwordCheckButton.setOnAction(action -> {
            String passwordCheck = passwordTextField.getText();
            if(passwordCheck.length() < 6){
                Button errorWindow = new Button("Password is too short, please enter a new one.");
                errorWindow.getStyleClass().add("errorWindow2");
                errorWindow.setPrefWidth(250);
                errorWindow.setPrefHeight(250);
                errorWindow.setWrapText(true);
                ((Group) scene2.getRoot()).getChildren().add(errorWindow);
                errorWindow.setOnAction(actionEvent ->  {
                    ((Group) scene2.getRoot()).getChildren().remove(errorWindow);
                });
            }
            else {
                Button errorWindow = new Button("Password is Okay.");
                errorWindow.getStyleClass().add("errorWindow2");
                errorWindow.setPrefWidth(250);
                errorWindow.setPrefHeight(250);
                errorWindow.setWrapText(true);
                ((Group) scene2.getRoot()).getChildren().add(errorWindow);
                errorWindow.setOnAction(actionEvent ->  {
                    ((Group) scene2.getRoot()).getChildren().remove(errorWindow);
                });
            }

        });

        Text enterEmail = new Text("Enter your Email Address:");
        enterEmail.getStyleClass().add("enterEmail");


        TextField emailTextField = new TextField();
        emailTextField.getStyleClass().add("emailTextField");


        /**************************************************************
         * Button that checks if the email address given is valid
         **************************************************************/
        Button emailCheckButton = new Button("Check");
        emailCheckButton.getStyleClass().add("emailCheckButton");
        emailCheckButton.setOnAction(action -> {

            String emailCheck = emailTextField.getText();
            //Got this formula for an email pattern from stack exchange- checks string for email pattern.
            Matcher matcher = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                    Pattern.CASE_INSENSITIVE).matcher(emailCheck);
            //button displaying a confirmation if email matches pattern
            if(matcher.find()){
                Button errorWindow = new Button("The email is of a valid format.");
                errorWindow.getStyleClass().add("errorWindow2");
                errorWindow.setPrefWidth(250);
                errorWindow.setPrefHeight(250);
                errorWindow.setWrapText(true);
                ((Group) scene2.getRoot()).getChildren().add(errorWindow);
                errorWindow.setOnAction(actionEvent ->  {
                    ((Group) scene2.getRoot()).getChildren().remove(errorWindow);
                });
            }
            //button announcing the email is not valid
            else {
                Button errorWindow = new Button("The email is not a valid format, please enter again.");
                errorWindow.getStyleClass().add("errorWindow2");
                errorWindow.setPrefWidth(250);
                errorWindow.setPrefHeight(250);
                errorWindow.setWrapText(true);
                ((Group) scene2.getRoot()).getChildren().add(errorWindow);
                errorWindow.setOnAction(actionEvent ->  {
                    ((Group) scene2.getRoot()).getChildren().remove(errorWindow);
                });
            }

        });

        Button nextButton = new Button("NEXT");
        nextButton.setPrefWidth(150);
        nextButton.getStyleClass().add("createAccButton");


        //adding the objects to the scene
        ((Group)scene2.getRoot()).getChildren().add(rect2);
        ((Group)scene2.getRoot()).getChildren().add(title2);
        ((Group)scene2.getRoot()).getChildren().add(header2);
        ((Group)scene2.getRoot()).getChildren().add(createAccount);
        ((Group)scene2.getRoot()).getChildren().add(enterUsername);
        ((Group)scene2.getRoot()).getChildren().add(usernameTextField);
        ((Group)scene2.getRoot()).getChildren().add(checkButton);
        ((Group)scene2.getRoot()).getChildren().add(enterPassword);
        ((Group)scene2.getRoot()).getChildren().add(passwordTextField);
        ((Group)scene2.getRoot()).getChildren().add(passwordCheckButton);
        ((Group)scene2.getRoot()).getChildren().add(enterEmail);
        ((Group)scene2.getRoot()).getChildren().add(emailTextField);
        ((Group)scene2.getRoot()).getChildren().add(emailCheckButton);
        ((Group)scene2.getRoot()).getChildren().add(nextButton);


        /************************************************************************************************
         * Scene 3- More of the Account creation, asking for personal details
         ***********************************************************************************************/
        Scene scene3 = new Scene(new Group(), 400, 700);


        Rectangle rect3 = new Rectangle(400, 100);
        rect3.getStyleClass().add("topBorder");
        ((Group)scene3.getRoot()).getChildren().add(rect3);

        Text title3 = new Text("TRACK");
        title3.getStyleClass().add("title");
        ((Group)scene3.getRoot()).getChildren().add(title3);

        Text header3 = new Text("JOIN");
        header3.getStyleClass().add("headerL");
        ((Group)scene3.getRoot()).getChildren().add(header3);

        Text moreDetails = new Text("More Details");
        moreDetails.getStyleClass().add("boldTopOfPage");
        ((Group)scene3.getRoot()).getChildren().add(moreDetails);

        Text firstName = new Text("First Name:");
        firstName.getStyleClass().add("firstName");
        ((Group)scene3.getRoot()).getChildren().add(firstName);

        TextField firstNameField = new TextField();
        firstNameField.getStyleClass().add("firstNameField");
        ((Group)scene3.getRoot()).getChildren().add(firstNameField);

        Text lastName = new Text("First Name:");
        lastName.getStyleClass().add("lastName");
        ((Group)scene3.getRoot()).getChildren().add(lastName);

        TextField lastNameField = new TextField();
        lastNameField.getStyleClass().add("lastNameField");
        ((Group)scene3.getRoot()).getChildren().add(lastNameField);





        /***************************************************************************************************************
         * End of building the Scenes, sets the first Scene
         *
         **************************************************************************************************************/

        primaryStage.setScene(scene);
        primaryStage.getScene().getStylesheets().add(
                getClass().getResource("styles.css").toExternalForm());
        primaryStage.show();

        /*****************************************************************
         //Shows the next scene if the user wishes to create an Account
         *****************************************************************/
        createAccButton.setOnAction(action -> {
            primaryStage.setScene(scene2);
            primaryStage.getScene().getStylesheets().add(
                    getClass().getResource("styles.css").toExternalForm());
            primaryStage.show();
        });


        /************************************************************************************
         * Makes sure the fields are filled in correctly and then creates a User account
         * before proceeding to the next page
         ***********************************************************************************/
        nextButton.setOnAction(action -> {
            /***********************************************
             * Checks for the Username
             ***********************************************/
            String usernameEntered = usernameTextField.getText();
            if(data.containsUsername(usernameEntered)) {
                Button errorWindow = new Button("The username is taken. Please try a new one.");
                errorWindow.getStyleClass().add("errorWindow2");
                errorWindow.setPrefWidth(250);
                errorWindow.setPrefHeight(250);
                errorWindow.setWrapText(true);
                ((Group) scene2.getRoot()).getChildren().add(errorWindow);
                errorWindow.setOnAction(actionEvent -> {
                    ((Group) scene2.getRoot()).getChildren().remove(errorWindow);
                });
            }
            else if(usernameEntered.length() < 3){
                Button errorWindow = new Button("The username is too short. Please try a new one.");
                errorWindow.getStyleClass().add("errorWindow2");
                errorWindow.setPrefWidth(250);
                errorWindow.setPrefHeight(250);
                errorWindow.setWrapText(true);
                ((Group) scene2.getRoot()).getChildren().add(errorWindow);
                errorWindow.setOnAction(actionEvent -> {
                    ((Group) scene2.getRoot()).getChildren().remove(errorWindow);
                });
            }
            else if(usernameEntered.length() > 20){
                Button errorWindow = new Button("The username is too long. Please try a new one.");
                errorWindow.getStyleClass().add("errorWindow2");
                errorWindow.setPrefWidth(250);
                errorWindow.setPrefHeight(250);
                errorWindow.setWrapText(true);
                ((Group) scene2.getRoot()).getChildren().add(errorWindow);
                errorWindow.setOnAction(actionEvent -> {
                    ((Group) scene2.getRoot()).getChildren().remove(errorWindow);
                });
            }
            else {
                /**********************************************
                 * Checks for password
                 **********************************************/
                String passwordEntered = passwordTextField.getText();
                if(passwordEntered.length() < 6){
                    Button errorWindow = new Button("Password is too short, please enter a new one.");
                    errorWindow.getStyleClass().add("errorWindow2");
                    errorWindow.setPrefWidth(250);
                    errorWindow.setPrefHeight(250);
                    errorWindow.setWrapText(true);
                    ((Group) scene2.getRoot()).getChildren().add(errorWindow);
                    errorWindow.setOnAction(actionEvent ->  {
                        ((Group) scene2.getRoot()).getChildren().remove(errorWindow);
                    });
                }
                else {
                    /********************************************
                     * Checks for email validity
                     *****************************************/
                    String emailEntered = emailTextField.getText();
                    //Got this formula for an email pattern from stack exchange- checks string for email pattern.
                    Matcher matcher = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                            Pattern.CASE_INSENSITIVE).matcher(emailEntered);
                    if(matcher.find() == false){
                        Button errorWindow = new Button("The email is not a valid format, please enter again.");
                        errorWindow.getStyleClass().add("errorWindow2");
                        errorWindow.setPrefWidth(250);
                        errorWindow.setPrefHeight(250);
                        errorWindow.setWrapText(true);
                        ((Group) scene2.getRoot()).getChildren().add(errorWindow);
                        errorWindow.setOnAction(actionEvent ->  {
                            ((Group) scene2.getRoot()).getChildren().remove(errorWindow);
                        });
                    }
                    else {
                        /**********************
                         * New User, next Scene
                         ********************/
                        User newUser = new User(usernameEntered, passwordEntered, emailEntered);
                        data.add(newUser);
                        data.saveData();
                        primaryStage.setScene(scene3);
                        primaryStage.getScene().getStylesheets().add(
                                getClass().getResource("styles.css").toExternalForm());
                        primaryStage.show();
                    }
                }
            }
        });



    }

    public static void main(String[] args) {
        launch(args);
    }
}