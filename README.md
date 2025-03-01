# StockCTRL  
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


🚀 How to use:  
- In the first step you run a server in Java idea - it automatically creates a local connection ( socket ) waiting for a client.  
- In the next step you also run the client in Java idea - it automatically connects to the server.  
  
🏁 Once this is done, you can use the programme to  
- Login or create a new account  
- Check available products  
- Add products to your basket  
- Purchase (this is a demonstration purchase only)  
- Check order (which products, order ID and current status of your order)  
- Modify your details (name, surname, address)  
  
In the terminal you can see what just happened in the programme.  
  
🏁 When you are done. All you need to do is close a client window and close a server in your Java idea.
  
Screenshots  
  
![login,register](https://github.com/user-attachments/assets/67d9ed8a-7ba9-4227-b0d8-b5e14d481e92)
![register](https://github.com/user-attachments/assets/dce30a83-d111-4d63-9f65-e6262c0452a8)
![menu](https://github.com/user-attachments/assets/0350e16b-9160-4c5d-a95c-d63fd2866743)
![productList](https://github.com/user-attachments/assets/193704de-ad6c-46c0-85a6-aad7f3f199aa)
![cart](https://github.com/user-attachments/assets/9b84f055-6b9b-4bed-80da-29ac47e25d1f)
![orders](https://github.com/user-attachments/assets/f5d6e992-4080-49dc-8c47-a7fa5c2a1c62)
![settings](https://github.com/user-attachments/assets/ae90b424-a69b-4c16-9fc7-cbdabb19085e)

