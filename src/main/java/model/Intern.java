/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;
import javax.ejb.Stateless;

/**
 *
 * @author NicoSoOl
 */
@Stateless
public class Intern extends Model {

    // Attributs
    private static String table = "info_intern";
    private static String[] attr
            = {"info_intern_id",
                "intern_group",
                "firstname",
                "lastname",
                "address",
                "skills",
                "linkedin",
                "birthday"};
    //id
    private int id;
    //intern_group
    private String group;
    //firstname
    private String first_name;
    //lastname
    private String last_name;
    //address
    private String address;
    //skills
    private String skills;
    //linkedin
    private String linkedin;
    //birthday
    private Date birthday;
    //mission
    private Mission mission;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Mission getMission() {
        return mission;
    }

    //info_intern_id
    public void setMission(Mission mission) {
        this.mission = mission;
    }

}
