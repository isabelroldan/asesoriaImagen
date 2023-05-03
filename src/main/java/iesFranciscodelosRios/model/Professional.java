package iesFranciscodelosRios.model;

public class Professional extends Person {
    private String dni;
    private int nPersonnel;
    private int nSocialSecurity;
    private Space space;

    public Professional(int id_person, String name, String surname, String telephone, String email, String password, String dni, int nPersonnel, int nSocialSecurity, Space space) {
        super(id_person, name, surname, telephone, email, password);
        this.dni = dni;
        this.nPersonnel = nPersonnel;
        this.nSocialSecurity = nSocialSecurity;
        this.space = space;
    }

    public Professional() {
        this(0, "", "", "", "", "", "", 0, 0, null);
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getnPersonnel() {
        return nPersonnel;
    }

    public void setnPersonnel(int nPersonnel) {
        this.nPersonnel = nPersonnel;
    }

    public int getnSocialSecurity() {
        return nSocialSecurity;
    }

    public void setnSocialSecurity(int nSocialSecurity) {
        this.nSocialSecurity = nSocialSecurity;
    }

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        this.space = space;
    }

    @Override
    public String toString() {
        return "Professional" +
                super.toString() +
                "\n\tdni='" + dni +
                "\n\tnPersonnel=" + nPersonnel +
                "\n\tnSocialSecurity=" + nSocialSecurity +
                "\n\tspace=" + space;
    }
}
