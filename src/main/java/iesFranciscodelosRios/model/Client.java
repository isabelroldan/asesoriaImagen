package iesFranciscodelosRios.model;

public class Client extends Person{
    private ColorTestResult colorTestResult;

    public Client(int id_person, String name, String surname, String telephone, String email, String password, ColorTestResult colorTestResult) {
        super(id_person, name, surname, telephone, email, password);
        this.colorTestResult = colorTestResult;
    }

    public Client() {
        this (0, "", "", "", "", "", null);
    }

    public ColorTestResult getColorTestResult() {
        return colorTestResult;
    }

    public void setColorTestResult(ColorTestResult colorTestResult) {
        this.colorTestResult = colorTestResult;
    }

    @Override
    public String toString() {
        return "Client " +
                super.toString()+
                "colorTestResult=" + colorTestResult;
    }
}
