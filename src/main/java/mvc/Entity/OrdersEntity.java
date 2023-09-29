package entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "orders")
public class OrdersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "orderDate")
    private LocalDate orderDate;
    @Column(name = "customerName")
    private String customerName;
    @Column(name = "customerAddress")
    private String customerAddress;

    @OneToMany(mappedBy = "orders",fetch = FetchType.EAGER)
    private List<OrderDetailsEntity> orderDetailsEntityList=new ArrayList<>();

    public OrdersEntity(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public List<OrderDetailsEntity> getOrderDetailsEntityList() {
        return orderDetailsEntityList;
    }

    public void setOrderDetailsEntityList(List<OrderDetailsEntity> orderDetailsEntityList) {
        this.orderDetailsEntityList = orderDetailsEntityList;
    }

    @Override
    public String toString(){
        return "OrdersEntity {"+
                "id="+id +
                ", orderDate="+orderDate+'\''+
                ", customerName="+customerName+'\''+
                ", customerAddress="+customerAddress +'\''+
//                ", orderDetailsEntityList="+orderDetailsEntityList +
                '}';
    }
}
