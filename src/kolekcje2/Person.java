package kolekcje2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Person implements Comparable<Person>, Serializable {

    private static List<Person> list;

    private String name;
    private String surname;
    private String street;
    private int housenumber;
    private int flatnumber; 
    private String city;
    private String phonenumber;

    public Person(String name, String surname, String street, int housenumber, String city, String phonenumber) {
        this(name, surname, street, housenumber, -1, city, phonenumber);
    }

    public Person(String name, String surname, String street, int housenumber, int flatnumber, String city, String phonenumber) {
        this.name = name;
        this.surname = surname;
        this.street = street;
        this.housenumber = housenumber;
        this.flatnumber = flatnumber;
        this.city = city;
        this.phonenumber = phonenumber;
        if (list == null) {
            list = new ArrayList<Person>();
        }
        list.add(this);
    }

    @Override
    public int compareTo(Person arg0) {

        int cmp = surname.toUpperCase().compareTo(arg0.surname.toUpperCase());
        if (cmp == 0) {
            cmp = name.toUpperCase().compareTo(arg0.name.toUpperCase());
        }

        return cmp;

    }

    public String toString() {

        return surname + " " + name + ", " + street + " " + housenumber + ((flatnumber == -1) ? (" ") : ("/" + flatnumber + " ")) + city + ", " + phonenumber;

    }

    public static void readDB(File dbfile) {

        list = new ArrayList<Person>();

        ObjectInputStream in = null;

        try {

            in = new ObjectInputStream(new FileInputStream(dbfile));

            Object o;

            while ((o = in.readObject()) != null) {

                if (o instanceof Person) {
                    list.add((Person) o);
                }

            }

        } catch (IOException | ClassNotFoundException e) {

        } finally {
            try {
                in.close();
            } catch (Exception e) {

            }
        }

    }

    public static void writeDB(File dbfile) {

        ObjectOutputStream out = null;

        try {

            out = new ObjectOutputStream(new FileOutputStream(dbfile));

            Iterator<Person> it = list.iterator();

            while (it.hasNext()) {
                out.writeObject(it.next());
            }

        } catch (IOException e) {

        } finally {
            try {
                out.close();
            } catch (Exception e) {

            }
        }

    }

    public static void sortBySurname() {

        Collections.sort(list);

    }

    public static void removePerson(Person p) {
        list.remove(p);
    }

    public static Person[] getPersonArray() {
        Person[] ret = new Person[list.size()];
        for (int i = 0, size = list.size(); i < size; i++) {
            ret[i] = list.get(i);
        }
        return ret;
    }

}
