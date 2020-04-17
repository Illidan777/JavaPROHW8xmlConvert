package ua.kiev.prog.DTO;

import ua.kiev.prog.Models.Group;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Contact")
public class ContactDTO {
    private String id;
    private String name;
    private String surname;
    private String phone;
    private String email;


    public ContactDTO() {}
    //
    private ContactDTO(  String name, String surname, String phone, String email) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
    }

    public static ContactDTO of( String name, String surname, String phone, String email) {
        return new ContactDTO(  name,surname,phone,email);
    }


    @XmlElement
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElement
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @XmlElement
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "ContactDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
   @XmlElement
     public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
