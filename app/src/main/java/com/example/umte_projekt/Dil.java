package com.example.umte_projekt;

public class Dil {
    private String namePart, typePart, subtypePart, manufacture;
    private int countPart;

    public Dil() {

    }

    public String getTypePart() {
        return typePart;
    }

    public void setTypePart(String typePart) {
        this.typePart = typePart;
    }

    public String getNamePart() {
        return namePart;
    }

    public void setNamePart(String namePart) {
        this.namePart = namePart;
    }

    public String getSubtypePart() {
        return subtypePart;
    }

    public void setSubtypePart(String subtypePart) {
        this.subtypePart = subtypePart;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public int getCountPart() {
        return countPart;
    }

    public void setCountPart(int countPart) {
        this.countPart = countPart;
    }
}
