# Online Bus Reservation System - IrishBusApp

# Introduction
<p> Online Bus Reservation System (IrishBusApp) is the project whose objective is to make the bus reservation online so that end user can book ticket from their own place. I have made this project in Java GUI, also using MySQL database.</p>
<p> I am using online server to host the database of the IrishBusApp Project. You can also use local server to host the database of this project. I use MySQL database for this project. But It can be easily converted into Oracle or any other database.</p>
<p>Through this Online Reservation System, a practical approach has been taken under study to understand how online reservation system works. This system will include all the necessary fields which are required during online reservation. It will be easy to use and can be used by any person. The basic idea behind this project is to save data in a central database which can be accessed by any authorized person to get information and saves time and burden which are being faced by customers.</p>
<p>As I have been developing this application, I used to work with localhost database. However, after I have developed the database completely, I decided to proceed with some sort of online MySQL hosting. There was many options, but I have decided to go with the following: https://www.freemysqlhosting.net/</p>
<pre>
FreeMySQLHosting includes;
•	MySQL Database
•	5MB MySQL Hosting Space
•	phpMyAdmin
•	Secure, Reliable, Fast hosting
</pre>
  
# System Requirements
## Software Requirements
* Operating System: Windows XP or Higher
* Java
* Internet connection

## Hardware Requirements
* Processor: Pentium IV or above
* Free space on hard disk: 3mb or more
* RAM: 512 MB or more
* Internet adapter or LAN

# Setup & Installation
<p>To be able to run the application on your system you will need to do follow these steps:

1.	Make sure to have Java installed on your machine. If you do not have it installed, please use the following link to download and install: https://java.com/en/download/
2.	Download the client from `dist` folder, or just use the following link: https://github.com/Sarlianth/thirdYearProject/blob/master/dist/IrishBusApp.jar
3.	After installing Java and downloading the client, all you need to do is just to run the Jar file that you have downloaded from the dist folder.

<p>I am using online MySQL database, therefore you do not need to worry about installing it on your local machine. However, if you would like to use your own database from localhost, please follow these steps before attempting the steps above:

1.	Install `wamp`, use the following link: https://sourceforge.net/projects/wampserver/files/WampServer%203/WampServer%203.0.0/
2.	Inside of the application, in every class I have created variables that are responsible for connection to database. These include the host, port, username, password etc. Please change those to your own.
3.	If you have all the above steps done, run the Jar client.


# User Guide
<p>In this section I will provide images with description of what each window in the application is used for. If potential user will have troubles using the application, this section should help understanding the project.

![1](https://cloud.githubusercontent.com/assets/10263556/25205210/59641b06-2559-11e7-86f7-30c1371588be.jpg)

## Login
![2](https://cloud.githubusercontent.com/assets/10263556/25205212/5c34fc74-2559-11e7-87b3-a4ed45c323ef.jpg)
 	
<p>This is the first frame that loads after user runs the program. The internet connection is required to successfully log in into the application. That’s because as I have mentioned before the system is connecting to online MySQL database where all the information is stored. If you do not have internet connection a message will pop up and the Login button will be disabled until the internet connection will be retrieved. To login into the application please use one of the two following credentials (case-sensitive):

<pre>
1.	Username: admin	  /	Password: admin
2.	Username: user		/	Password: pass 
</pre>

<p>If correct credentials will be entered, after pressing Login button, pop up message should be displayed welcoming user, and informing the user if admin privileges are available for them.

## Main window
<p>The main window consists of 4 separate tabbed panels – “Reservation”, “Administration”, “Bus Timetable” and “Tickets”. I will describe each of the panel and its components separately. On the right hand side at the top, there’s a Logout button in order to logout and see the Login frame again.

## Reservation

![3](https://cloud.githubusercontent.com/assets/10263556/25205218/5ec49062-2559-11e7-94d9-5ec8480b0202.jpg)

<p>In this panel, user can proceed with the reservation. To start making the reservation, source station and destination station must be selected from the dropdown lists (Galway-Dublin as default). Once user have selected stations, search button should be clicked to search the database for available buses between those stations. 
  
![4](https://cloud.githubusercontent.com/assets/10263556/25205222/609933a2-2559-11e7-964a-c7707f743975.jpg)

<p>As can be seen in the picture above, there’s several connections between Galway and Dublin station, all of which are at different times. User must select one available bus from the list, after which “Choose bus” button should be pressed.
<p>After successfully selecting available bus, the panel below should activate allowing user to pick the date and select passenger quantity. On the right hand-side to the lower panel, user can see the total price of the ticket as well as how much it is for adults and/or students separately. 
<p>Once user has completed all the above steps and is happy with all the details, “Buy tickets” button should be pressed, or if user wishes to start again with the reservation, “Clear” button should be pressed. 
<p>Little frame with all the details that are essential for the customer will pop-up. User will be informed of the ticket ID, which needs to be used to print, save or retrieve the tickets later. The ticket ID will also be saved to user’s clipboard, and the user will be informed about that.

![5](https://cloud.githubusercontent.com/assets/10263556/25205228/626a5058-2559-11e7-8fb5-0810b9f23f33.jpg)

<p>After clicking OK, the ticket will appear containing all information about the journey. User can also save the ticket into PDF format for further use.

![6](https://cloud.githubusercontent.com/assets/10263556/25205231/63d68de4-2559-11e7-9d96-7076b23fe7cf.jpg)


## Administration

![7](https://cloud.githubusercontent.com/assets/10263556/25205236/6545037c-2559-11e7-9fb5-f634e7820182.jpg)

<p>In the Administration tab, we can see all the buses from the database, and we can either add, delete or update each bus. To delete/update respective bus, user should select the bus from the list and click either delete/update button.
<p>To add a new bus into the database, little popup will appear asking user to fill all essential information about the new bus.

![8](https://cloud.githubusercontent.com/assets/10263556/25205239/66aa9fe2-2559-11e7-8fa0-9bfab863d9ab.jpg)

<p>Administration tab is only available to users with admin privileges, user without those privileges will not see the Administration tab at all in the main frame.

## Bus timetable

![9](https://cloud.githubusercontent.com/assets/10263556/25205241/684f6152-2559-11e7-9063-29bf3e94cf7c.jpg)

<p>Bus timetable is a table containing all the available buses from the database, this tab is available both for administrators and standard users. Because this system is designed as a multi-user application, in the right hand-side corner there’s a “Refresh” button to refresh the table in case of any changes made at the same time by a different user that has administrator privileges. 

## Tickets

![10](https://cloud.githubusercontent.com/assets/10263556/25205243/6a3fe428-2559-11e7-9df3-c36d25345974.jpg)

<p>This tab is very clear and tidy, only having one text field allowing user to enter their ticket ID to retrieve the ticket, see all information and save it to a PDF format.

<p>If you would like to know more about this project, please visit Wiki (https://github.com/Sarlianth/thirdYearProject/wiki) where you can find more interesting information like the architecture, design methodology, technology and much more.
