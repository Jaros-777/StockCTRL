# 🏪 StockCTRL  

### A simple stock control and purchasing application built in Java.  

StockCTRL is a client-server application with a graphical interface, designed for university coursework. It allows users to register, browse products, add them to a cart, and simulate a purchase.  

---

## 📋 Features  
✅ User authentication (Login/Registration)  
✅ Modify user details (name, surname, address)  
✅ Browse and search for products  
✅ Add products to a shopping cart  
✅ Simulated purchase (without transaction finalization)  
✅ View order history  

---

## 🛠️ Technologies Used  
- **JavaFX** – for the graphical user interface  
- **Hibernate** – for database ORM  
- **PostgreSQL** – as the database  
- **Sockets** – for client-server communication  

---

## 🚀 Installation Guide  

### **1️⃣ Set up the database**  
1. Open **pgAdmin** (or any PostgreSQL tool)  
2. Run the script from `Create database PSQL.txt` to create the database and user  

### **2️⃣ Configure Hibernate**  
1. Open the project in **IntelliJ IDEA** or another Java IDE  
2. Locate `hibernate.cfg.xml` and modify the connection properties:
   ```xml
   <property name="hibernate.connection.url">jdbc:postgresql://localhost:5432/postgres</property>  
   <property name="hibernate.connection.username">your_username</property>  
   <property name="hibernate.connection.password">your_password</property>  
   ```

### **3️⃣ Running the application**  
1. **Start the server** – Run the `Server` application (it waits for client connections)  
2. **Start the client** – Run the `Client` application (it will connect to the server automatically)  

---

## 🎯 How to Use  
1. **Login** or **Register**  
2. Browse the product list  
3. Add items to the cart  
4. View and simulate order checkout  
5. Modify your user details  

### **Example Screenshots** 📸  

| Login & Registration | Product List | Shopping Cart |  
|----------------------|-------------|--------------|  
| ![login](https://github.com/user-attachments/assets/67d9ed8a-7ba9-4227-b0d8-b5e14d481e92) | ![productList](https://github.com/user-attachments/assets/193704de-ad6c-46c0-85a6-aad7f3f199aa) | ![cart](https://github.com/user-attachments/assets/9b84f055-6b9b-4bed-80da-29ac47e25d1f) |  
||||  
| Menu | Orders list | Settings |  
| ![menu](https://github.com/user-attachments/assets/0350e16b-9160-4c5d-a95c-d63fd2866743) | ![orders](https://github.com/user-attachments/assets/f5d6e992-4080-49dc-8c47-a7fa5c2a1c62) | ![settings](https://github.com/user-attachments/assets/ae90b424-a69b-4c16-9fc7-cbdabb19085e) |  

---

## 🏁 Closing the Application  
- Simply close the **client window**  
- Stop the **server** in your IDE  

---

## 📝 License  
This project was created for educational purposes.  




