package HealthTracker;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.datatransfer.MimeTypeParseException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static HealthTracker.User.heightMetric.CM;
import static HealthTracker.User.heightMetric.INCHES;
import static HealthTracker.User.weightMetric.KG;
import static HealthTracker.User.weightMetric.LBS;

public class Account extends Application {
    public User loggedIn;

    public void setLoggedIn(User loggedIn) {
        this.loggedIn = loggedIn;
    }

    public User getLoggedIn() {
        return loggedIn;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //the temporary Data structure holding example users
        UserMap data = new UserMap();

        final User[] user = {new User()};

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
        ((Group)scene.getRoot()).getChildren().add(title);
        ((Group)scene.getRoot()).getChildren().add(header);
        ((Group)scene.getRoot()).getChildren().add(textField);
        ((Group)scene.getRoot()).getChildren().add(textField2);
        ((Group)scene.getRoot()).getChildren().add(userField);
        ((Group)scene.getRoot()).getChildren().add(passField);
        ((Group)scene.getRoot()).getChildren().add(logInButton);
        ((Group)scene.getRoot()).getChildren().add(createAccButton);

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
                    ((Group)scene2.getRoot()).getChildren().remove(errorWindow);
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

        Text lastName = new Text("Last Name:");
        lastName.getStyleClass().add("lastName");
        ((Group)scene3.getRoot()).getChildren().add(lastName);

        TextField lastNameField = new TextField();
        lastNameField.getStyleClass().add("lastNameField");
        ((Group)scene3.getRoot()).getChildren().add(lastNameField);

        Text height = new Text("Height: ");
        height.getStyleClass().add("height");
        ((Group)scene3.getRoot()).getChildren().add(height);

        TextField heightField = new TextField();
        // force the field to be numeric only - solution found on stackExchange
        heightField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    heightField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        heightField.setPrefWidth(70);
        heightField.getStyleClass().add("heightField");
        ((Group)scene3.getRoot()).getChildren().add(heightField);

        ComboBox<String> heightMetric = new ComboBox<String>();
        boolean cm = heightMetric.getItems().add("cm");
        boolean inches = heightMetric.getItems().add("inches");
        heightMetric.setPrefWidth(70);
        heightMetric.getStyleClass().add("heightMetric");
        ((Group)scene3.getRoot()).getChildren().add(heightMetric);


        Text weight = new Text("Weight: ");
        weight.getStyleClass().add("weight");
        ((Group)scene3.getRoot()).getChildren().add(weight);

        TextField weightField = new TextField();
        // force the field to be numeric only - solution found on stackExchange
        weightField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    weightField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        weightField.setPrefWidth(70);
        weightField.getStyleClass().add("weightField");
        ((Group)scene3.getRoot()).getChildren().add(weightField);

        ComboBox<String> weightMetric = new ComboBox<>();
        boolean kg = weightMetric.getItems().add("kg");
        boolean lbs = weightMetric.getItems().add("lbs");
        weightMetric.setPrefWidth(70);
        weightMetric.getStyleClass().add("weightMetric");
        ((Group)scene3.getRoot()).getChildren().add(weightMetric);

        Button nextButton2 = new Button("CREATE");
        nextButton2.setPrefWidth(150);
        nextButton2.getStyleClass().add("createAccButton");
        ((Group)scene3.getRoot()).getChildren().add(nextButton2);


        /**************************************************************************************************************
         * Scene 4, the screen that shows if the user is not verified. Asks the user for the verification code,
         * which has been sent by email
         * Scene 4 will show after user has created an account, and also when an unverified user tries to log in.
         * Includes button to resend email
         *************************************************************************************************************/
        Scene scene4 = new Scene(new Group(), 400, 700);


        Rectangle rect4 = new Rectangle(400, 100);
        rect4.getStyleClass().add("topBorder");
        ((Group)scene4.getRoot()).getChildren().add(rect4);

        Text title4 = new Text("TRACK");
        title4.getStyleClass().add("title");
        ((Group)scene4.getRoot()).getChildren().add(title4);

        Text header4 = new Text("JOIN");
        header4.getStyleClass().add("headerL");
        ((Group)scene4.getRoot()).getChildren().add(header4);

        Text enterCode = new Text("A 5 digit verification\n code has been sent\n         " +
                "by email.\n Please enter code: ");
        enterCode.getStyleClass().add("boldTopOfPage2");
        ((Group)scene4.getRoot()).getChildren().add(enterCode);

        TextField codeField = new TextField();
        codeField.getStyleClass().add("codeField");
        ((Group)scene4.getRoot()).getChildren().add(codeField);

        Button enterVerCode = new Button("Next");
        enterVerCode.setPrefWidth(150);
        enterVerCode.getStyleClass().add("enterVerCode");
        ((Group)scene4.getRoot()).getChildren().add(enterVerCode);

        Button resendEmail = new Button("Re-send Email");
        resendEmail.setPrefWidth(150);
        resendEmail.getStyleClass().add("resendEmail");
        ((Group)scene4.getRoot()).getChildren().add(resendEmail);

        enterVerCode.setOnAction(action -> {
            String verCodeEntered = codeField.getText();
            String verificationCode = String.valueOf(user[0].getVerificationCode());
            if(verCodeEntered.equals(verificationCode)){
                user[0].setVerified(true);
                data.updateUser(user[0]);
                data.saveData();
                /**********************************************
                 * User now logs in
                 *
                 **********************************************/
                System.out.println("User has logged in.");
            }
            else {
                Button errorWindow = new Button("Verification code is not correct.");
                errorWindow.getStyleClass().add("errorWindow");
                errorWindow.setPrefWidth(250);
                errorWindow.setPrefHeight(250);
                errorWindow.setWrapText(true);
                ((Group) scene4.getRoot()).getChildren().add(errorWindow);
                errorWindow.setOnAction(actionEvent ->  {
                    ((Group) scene4.getRoot()).getChildren().remove(errorWindow);
                });
            }
        });

        resendEmail.setOnAction(action -> {


            // mimemessage is used here to send an email from an email account attributed to the app.
            // Solution to errors that popped up found on tutorialspoint and stackexchange

            // Recipient's email ID
            String to = user[0].getEmail();

            // Sender's email ID
            final String username = "trackappsoftware@gmail.com";//
            final String password = "secretpassword";
            String from = username;

            // Sending email from localhost
            String host = "localhost";

            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

            Properties props = System.getProperties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.port", "465");
            props.setProperty("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.auth", "true");
            props.put("mail.debug", "true");
            props.put("mail.store.protocol", "pop3");
            props.put("mail.transport.protocol", "smtp");
            // Get the default Session object.
            Session session = Session.getDefaultInstance(props,
                    new Authenticator(){
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }});


            try {
                // Create a default MimeMessage object.
                MimeMessage message = new MimeMessage(session);

                // Set From: header field of the header.
                message.setFrom(new InternetAddress(from));

                // Set To: header field of the header.
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

                // Set Subject: header field
                message.setSubject("Track Verification required");

                // Now set the actual message
                message.setText("Your verification code is: " + user[0].getVerificationCode());

                // Send message
                Transport.send(message);
                System.out.println("Sent message successfully....");

                primaryStage.setScene(scene4);
                primaryStage.getScene().getStylesheets().add(
                        getClass().getResource("styles.css").toExternalForm());
                primaryStage.show();


            } catch (MessagingException mex) {
                mex.printStackTrace();
            }
            Button errorWindow = new Button("An email has been re-sent to the given email address");
            errorWindow.getStyleClass().add("errorWindow");
            errorWindow.setPrefWidth(250);
            errorWindow.setPrefHeight(250);
            errorWindow.setWrapText(true);
            ((Group) scene4.getRoot()).getChildren().add(errorWindow);
            errorWindow.setOnAction(actionEvent ->  {
                ((Group) scene4.getRoot()).getChildren().remove(errorWindow);
            });
        });

        /***************************************************************************************************************
         * End of building the Scenes, sets the first Scene
         *
         **************************************************************************************************************/

        primaryStage.setScene(scene);
        primaryStage.getScene().getStylesheets().add(
                getClass().getResource("styles.css").toExternalForm());
        primaryStage.show();



        /************************************************************************************************
         * Action for when a user tries to log in. Will pop up an error box that must be closed to proceed
         * if details do not match any details in the UserMap
         ******************************************************************************************************/
        logInButton.setOnAction(action -> {
            String usernameEntered = textField.getText();
            String passwordEntered = textField2.getText();

            try {
                user[0] = data.getUser(usernameEntered, passwordEntered);
                if(user[0].isVerified()) {
                    loggedIn=user[0];
                    System.out.println("Successful Log in of " + user[0].getUsername() + ", " + user[0].getPassword());
                }
                else {
                    // Recipient's email ID needs to be mentioned.
                    String to = user[0].getEmail();

                    // Sender's email ID needs to be mentioned
                    final String username = "trackappsoftware@gmail.com";//
                    final String password = "secretpassword";
                    String from = username;

                    // Assuming you are sending email from localhost
                    String host = "localhost";

                    final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

                    Properties props = System.getProperties();
                    props.setProperty("mail.smtp.host", "smtp.gmail.com");
                    props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
                    props.setProperty("mail.smtp.socketFactory.fallback", "false");
                    props.setProperty("mail.smtp.port", "465");
                    props.setProperty("mail.smtp.socketFactory.port", "465");
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.debug", "true");
                    props.put("mail.store.protocol", "pop3");
                    props.put("mail.transport.protocol", "smtp");
                    // Get the default Session object.
                    Session session = Session.getDefaultInstance(props,
                            new Authenticator(){
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(username, password);
                                }});


                    try {
                        // Create a default MimeMessage object.
                        MimeMessage message = new MimeMessage(session);

                        // Set From: header field of the header.
                        message.setFrom(new InternetAddress(from));

                        // Set To: header field of the header.
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

                        // Set Subject: header field
                        message.setSubject("Track Verification required");

                        // Now set the actual message
                        message.setText("Your verification code is: " + user[0].getVerificationCode());

                        // Send message
                        Transport.send(message);
                        System.out.println("Sent message successfully....");

                        primaryStage.setScene(scene4);
                        primaryStage.getScene().getStylesheets().add(
                                getClass().getResource("styles.css").toExternalForm());
                        primaryStage.show();


                    } catch (MessagingException mex) {
                        mex.printStackTrace();
                    }
                    primaryStage.setScene(scene4);
                    primaryStage.getScene().getStylesheets().add(
                            getClass().getResource("styles.css").toExternalForm());
                    primaryStage.show();
                }

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
                errorWindow.setOnAction((ActionEvent actionEvent) -> {
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
                    if(!matcher.find()){
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
                        user[0] = new User(usernameEntered, passwordEntered, emailEntered);
                        primaryStage.setScene(scene3);
                        primaryStage.getScene().getStylesheets().add(
                                getClass().getResource("styles.css").toExternalForm());
                        primaryStage.show();
                    }
                }
            }
        });


        //the button on the more details page
        nextButton2.setOnAction(action ->{

            String fName = firstNameField.getText();
            //error box if no name has been put in
            if(fName.length() < 1){
                Button errorWindow = new Button("'First Name' has not been filled in.");
                errorWindow.getStyleClass().add("errorWindow");
                errorWindow.setPrefWidth(250);
                errorWindow.setPrefHeight(250);
                errorWindow.setWrapText(true);
                ((Group) scene3.getRoot()).getChildren().add(errorWindow);
                errorWindow.setOnAction(actionEvent ->  {
                    ((Group) scene3.getRoot()).getChildren().remove(errorWindow);
                });
            }
            else {
                String lName = lastNameField.getText();

                if (lName.length() < 1) {
                    Button errorWindow = new Button("'Last Name' has not been filled in.");
                    errorWindow.getStyleClass().add("errorWindow");
                    errorWindow.setPrefWidth(250);
                    errorWindow.setPrefHeight(250);
                    errorWindow.setWrapText(true);
                    ((Group) scene3.getRoot()).getChildren().add(errorWindow);
                    errorWindow.setOnAction(actionEvent -> {
                        ((Group) scene3.getRoot()).getChildren().remove(errorWindow);
                    });
                }
                else {
                    try {
                        double userHeight = Double.parseDouble(heightField.getText());
                        if (userHeight < 20) {
                            Button errorWindow = new Button("Please enter a valid height");
                            errorWindow.getStyleClass().add("errorWindow");
                            errorWindow.setPrefWidth(250);
                            errorWindow.setPrefHeight(250);
                            errorWindow.setWrapText(true);
                            ((Group) scene3.getRoot()).getChildren().add(errorWindow);
                            errorWindow.setOnAction(actionEvent -> {
                                ((Group) scene3.getRoot()).getChildren().remove(errorWindow);
                            });
                        } else {
                            //will default to cm
                            User.heightMetric userHeightMetric;
                            if (heightMetric.getValue().equals("inches")) {
                                userHeightMetric = INCHES;
                            } else {
                                userHeightMetric = CM;
                            }
                            try {

                                double userWeight = Double.parseDouble(weightField.getText());
                                if (userWeight < 20) {
                                    Button errorWindow = new Button("Please enter a valid weight");
                                    errorWindow.getStyleClass().add("errorWindow");
                                    errorWindow.setPrefWidth(250);
                                    errorWindow.setPrefHeight(250);
                                    errorWindow.setWrapText(true);
                                    ((Group) scene3.getRoot()).getChildren().add(errorWindow);
                                    errorWindow.setOnAction(actionEvent -> {
                                        ((Group) scene3.getRoot()).getChildren().remove(errorWindow);
                                    });
                                } else {
                                    User.weightMetric userWeightMetric;
                                    if (weightMetric.getValue().equals("lbs")) {
                                        userWeightMetric = LBS;
                                    } else {
                                        userWeightMetric = KG;
                                    }

                                    //creates account and moves to scene
                                    user[0].setPreferredWeightMetric(userWeightMetric);
                                    user[0].setPreferredHeightMetric(userHeightMetric);
                                    user[0].setFirstName(fName);
                                    user[0].setLastName(lName);
                                    user[0].setHeight(userHeight);
                                    user[0].setStartWeight(userWeight);

                                    data.add(user[0]);
                                    data.saveData();

                                    // Recipient's email ID needs to be mentioned.
                                    String to = user[0].getEmail();

                                    // Sender's email ID needs to be mentioned
                                    final String username = "trackappsoftware@gmail.com";//
                                    final String password = "secretpassword";
                                    String from = username;

                                    // Assuming you are sending email from localhost
                                    String host = "localhost";

                                    final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

                                    Properties props = System.getProperties();
                                    props.setProperty("mail.smtp.host", "smtp.gmail.com");
                                    props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
                                    props.setProperty("mail.smtp.socketFactory.fallback", "false");
                                    props.setProperty("mail.smtp.port", "465");
                                    props.setProperty("mail.smtp.socketFactory.port", "465");
                                    props.put("mail.smtp.auth", "true");
                                    props.put("mail.debug", "true");
                                    props.put("mail.store.protocol", "pop3");
                                    props.put("mail.transport.protocol", "smtp");
                                    // Get the default Session object.
                                    Session session = Session.getDefaultInstance(props,
                                            new Authenticator(){
                                                protected PasswordAuthentication getPasswordAuthentication() {
                                                    return new PasswordAuthentication(username, password);
                                                }});


                                    try {
                                        // Create a default MimeMessage object.
                                        MimeMessage message = new MimeMessage(session);

                                        // Set From: header field of the header.
                                        message.setFrom(new InternetAddress(from));

                                        // Set To: header field of the header.
                                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

                                        // Set Subject: header field
                                        message.setSubject("Track Verification required");

                                        // Now set the actual message
                                        message.setText("Your verification code is: " + user[0].getVerificationCode());

                                        // Send message
                                        Transport.send(message);
                                        System.out.println("Sent message successfully....");

                                        primaryStage.setScene(scene4);
                                        primaryStage.getScene().getStylesheets().add(
                                                getClass().getResource("styles.css").toExternalForm());
                                        primaryStage.show();


                                    } catch (MessagingException mex) {
                                        mex.printStackTrace();
                                    }
                                }
                            } catch (Exception e) {
                                Button errorWindow = new Button("Please enter a valid weight");
                                errorWindow.getStyleClass().add("errorWindow");
                                errorWindow.setPrefWidth(250);
                                errorWindow.setPrefHeight(250);
                                errorWindow.setWrapText(true);
                                ((Group) scene3.getRoot()).getChildren().add(errorWindow);
                                errorWindow.setOnAction(actionEvent -> {
                                    ((Group) scene3.getRoot()).getChildren().remove(errorWindow);
                                });
                            }
                        }
                    }
                    catch(Exception e){
                        Button errorWindow = new Button("Please enter a valid height");
                        errorWindow.getStyleClass().add("errorWindow");
                        errorWindow.setPrefWidth(250);
                        errorWindow.setPrefHeight(250);
                        errorWindow.setWrapText(true);
                        ((Group) scene3.getRoot()).getChildren().add(errorWindow);
                        errorWindow.setOnAction(actionEvent -> {
                            ((Group) scene3.getRoot()).getChildren().remove(errorWindow);
                        });
                    }
                }
            }
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}