package hibernate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.json.JSONObject;

@Entity
@Table(name="users")
public class DataBaseUsers {

    @Id
    private Integer id;

    @Column(name = "name") // Dostosowanie nazwy kolumny
    private String userName;
    @Column(name = "surname")
    private String userSurname;
    @Column
    private String address;
    @Column
    private String order;
    @Column
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column
    private String password;

    public String getCartList() {
        return cartList;
    }

    public void setCartList(String cartList) {
        this.cartList = cartList;
    }

    @Column
    private String cartList;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }



    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    @Override
    public String toString() {
        return "DataBaseUsers{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userSurname='" + userSurname + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
