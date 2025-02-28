# StockCTRL  
IN DEVELOPMENT  
This is a computer application created to pass a university course in Java.  
📋 Requirements:  
- Client - server  
- Connection to the database  
- Graphical interface  

In this project I used JavaFX, hibernate, PostgreSQL, socket-based client- server.  

🎯 Client targets application performance and functionality:  
- ✅ Login/registration form  
- ✅ Change of user address  
- ✅ Easy to buy products ( without finalizing the transaction )  
- ✅ insight into your orders  

What you need to run a programme:  
- Java idea (e.g. Intelij IDEA)  
- PostgreSQL database  
  
1️⃣. In PostgreSQL tool ( for example pgAdmin ) you need to create database - paste text that is in ,,Create database PSQL.txt,,, run it and create user.  

2️⃣. In java idee you need to load project and in hibernate.cfg.xml change connections property:
 ```
        <property name="hibernate.connection.url">jdbc:postgresql://localhost:5432/postgres</property>  
        <property name="hibernate.connection.username">user_username</property>  
        <property name="hibernate.connection.password">user_password</property>
   ```


🚀 How it works:  
- In the first step you run a server - it automatically creates a local connection ( socket ) waiting for a client.  
- In the next step you run the client - it automatically connects to the server.  
When checking products, shopping cart, orders, address changes, the program communicates with the PostgreSQL database on your computer  
  
In the terminal you can see what just happened in the programme.  
  
🏁 Once this is done, you can use the programme to  
- Login or create a new account  
- Check available products  
- Add products to your basket  
- Purchase (this is a demonstration purchase only)  
- Check order (which products, order ID and current status of your order)  
- Change your address  
  
Screenshots taken during application development (demo)  
  
![image](https://github.com/user-attachments/assets/a3558092-966c-491c-aa7c-201506159e77)
![image](https://github.com/user-attachments/assets/549e1aac-8faf-4f5a-8aa1-d2e4402dbd19)
![image](https://github.com/user-attachments/assets/7ba18ed7-f2fb-4303-a42c-171e98678262)
![image](https://github.com/user-attachments/assets/75577a6b-0abc-4522-900d-5fde32792dc4)



