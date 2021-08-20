package az.elixir.vin.entity;

import javax.persistence.*;
import java.sql.Clob;

@Entity
@Table(name = "HOME_DETAILS")
public class CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int numberOfOrder;

    @Lob
    private Clob photoBase64;


    public CommonEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfOrder() {
        return numberOfOrder;
    }

    public void setNumberOfOrder(int numberOfOrder) {
        this.numberOfOrder = numberOfOrder;
    }

    public Clob getPhotoBase64() {
        return photoBase64;
    }

    public void setPhotoBase64(Clob photoBase64) {
        this.photoBase64 = photoBase64;
    }
}
