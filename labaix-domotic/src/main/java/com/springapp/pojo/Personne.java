package com.springapp.pojo;

/**
 * Created by nicolas on 13/02/15.
 */
public class Personne {

    private Integer id;
    private String nom;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    @Override
    public String toString() {
        return "Personne[" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", age=" + age +
                ']';
    }

    public Personne(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        Personne personne = (Personne) o;

        if (this.age != personne.age) return false;
        if (this.id != null ? !this.id.equals(personne.id) : personne.id != null) return false;
        if (this.nom != null ? !this.nom.equals(personne.nom) : personne.nom != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = this.id != null ? this.id.hashCode() : 0;
        result = 31 * result + (this.nom != null ? this.nom.hashCode() : 0);
        result = 31 * result + this.age;
        return result;
    }

    public void setNom(String nom) {

        this.nom = nom;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Personne(Integer id, String nom, int age) {
        this.id = id;
        this.nom = nom;

        this.age = age;
    }

    private int age;
}
