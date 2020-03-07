package Model;

import javax.persistence.*;

/**
 * Entity for table Client
 */
@Entity
@Table(name = "client")
public class Client {

    @Id
    @Column(name = "id_passport")
    private String idPassport;
    @Column(name = "name")
    private String name;
    @Column(name = "residence_address")
    private String residenceAddress;
    @Column(name = "birth_date")
    private String birthDate;

    public String getIdPassport() {
        return idPassport;
    }

    public void setIdPassport(String idPassport) {
        this.idPassport = idPassport;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResidenceAddress() {
        return residenceAddress;
    }

    public void setResidenceAddress(String residenceAddress) {
        this.residenceAddress = residenceAddress;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
