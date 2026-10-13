package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;


public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        String date = LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return date;
    }

    public static String generateCity(Faker faker) {

        String[] city = {"Абакан", "Архангельск", "Астрахань", "Барнаул", "Белгород",
                "Биробиджан", "Благовещенск", "Брянск", "Великий Новгород", "Владивосток",
                "Владикавказ", "Владимир", "Волгоград", "Вологда", "Воронеж", "Гатчина", "Горно-Алтайск",
                "Грозный", "Екатеринбург", "Иваново", "Ижевск", "Иркутск",
                "Йошкар-Ола", "Казань", "Калининград", "Калуга",
                "Кемерово", "Киров", "Кострома", "Краснодар", "Красноярск", "Курган",
                "Курск", "Кызыл", "Липецк", "Луганск", "Магадан", "Майкоп",
                "Махачкала", "Москва", "Мурманск", "Нальчик", "Нарьян-Мар",
                "Нижний Новгород", "Новосибирск", "Омск", "Орёл", "Оренбург",
                "Пенза", "Пермь", "Петрозаводск", "Петропавловск-Камчатский", "Псков", "Ростов-на-Дону",
                "Рязань", "Салехард", "Самара", "Санкт-Петербург", "Саранск",
                "Саратов", "Смоленск", "Ставрополь", "Сыктывкар", "Тамбов",
                "Тверь", "Томск", "Тула", "Тюмень", "Улан-Уде", "Ульяновск", "Уфа", "Хабаровск",
                "Ханты-Мансийск", "Чебоксары", "Челябинск", "Черкесск", "Чита",
                "Элиста", "Южно-Сахалинск", "Якутск", "Ярославль"};
        return city[new Random().nextInt(city.length)];
    }

    public static String generateName(Faker faker) {

        String name = faker.name().fullName();
        return name;
    }

    public static String generatePhone(Faker faker) {

        String phone = faker.numerify("+7 9## ### ## ##");
        return phone;
    }

    public static class Registration {
        private static Faker faker;

        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            faker = new Faker(new Locale(locale));

            String city = generateCity(faker);
            String name = generateName(faker);
            String phone = generatePhone(faker);
            UserInfo user = new UserInfo(city, name, phone);

            return user;
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}
