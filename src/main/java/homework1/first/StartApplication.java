package homework1.first;

public class StartApplication {
    public static void main(String[] args) {
        Person person = new Person.Builder()
                .withFistName("Vasya")
                .withLastName("Petrov")
                .withMiddleName("Aleksandrovich")
                .withCountry("Russia")
                .withAddress("Novosibirsk, Lenina 5 - 12")
                .withPhone("99999999")
                .withAge(20)
                .withGender("Male")
                .build();
        System.out.println(person);
    }
}
