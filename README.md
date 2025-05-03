# Name of application: MeetWithProf
# Version: 0.8

# who did what:
1. Bushra Asif - Implmented polymorphism and edit functionality 
2. Huda Mohammed - populated the db 
3. Jayden Manning - 
3. Kenny Li - 

# Any other instruction that users need to know: 
Polymorphism is used in our application through the use of the setStage(parent) interface that is used by multiple controller classes (concrete child classes), such as the HomeController, OfficeHourController. These controllers need access to the JavaFX stage, therefore they implement the setStage interface. To avoid coded duplication, the navigateHandler class is created to handle switching scenes. This class, when called from other controllers will load FXML files, retrieves the corresponding controller, and uses polymorphism to invoke the setStage(Stage) method. Adding this class has removed the needed for redundant code to switch between different stages. 


