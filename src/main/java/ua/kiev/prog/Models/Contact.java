package ua.kiev.prog.Models;

import ua.kiev.prog.DTO.ContactDTO;

import javax.persistence.*;


@Entity
@Table(name="Contacts")
//@XmlRootElement(name = "contact")
public class Contact {
    @Id
    @GeneratedValue
    private long id;
    
    @ManyToOne
    @JoinColumn(name="group_id")
    private Group group;

    private String name;
    private String surname;
    private String phone;
    private String email;

    public Contact() {}
//
    public Contact( Group group,String name, String surname, String phone, String email) {
        this.group = group;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
    }

    public static Contact of(Group group, String name, String surname, String phone, String email) {
        return new Contact(group, name, surname, phone, email);
    }



    public ContactDTO toDTO() {
        return ContactDTO.of(name,surname,phone,email);
    }

  //  @XmlElement
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

   // @XmlElement
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

   // @XmlElement
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

   // @XmlElement
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

   // @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //@XmlElement
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", group=" + group.getName() +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
