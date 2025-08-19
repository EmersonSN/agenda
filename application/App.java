package application;

import model.entities.Schedule;
import model.exceptions.DataException;
import model.service.ScheduleService;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Schedule> schedules = new ArrayList<>();
        ScheduleService service = new ScheduleService();
        int option = 0;

        while (option != 9) {
            try {
                service.loadData(schedules);
                System.out.printf("================ APP STARTED ================%n" +
                        "Escolha uma opção:%n1 - Cadastrar usuário%n" +
                        "2 - Listar usuários%n" +
                        "3 - Alterar dados%n" +
                        "4 - Excluir usuário%n" +
                        "5 - Buscar por nome%n" +
                        "6 - Buscar por email%n" +
                        "7 - Carregar dados%n" +
                        "8 - Salvar dados%n" +
                        "9 - SAIR%n");
                option = Integer.parseInt(sc.nextLine());

                switch (option) {
                    case 1:
                        service.userInsert(sc, schedules);
                        break;
                    case 2:
                        service.userList(schedules);
                        break;
                    case 3:
                        service.userAlter(sc, schedules);
                        break;
                    case 4:
                        service.userDelete(sc, schedules);
                        break;
                    case 5:
                        service.findByName(sc, schedules);
                        break;
                    case 6:
                        service.findByEmail(sc, schedules);
                        break;
                    case 7:
                        service.loadData(schedules);
                        break;
                    case 8:
                        service.saveData(schedules);
                        break;
                    case 9:
                        System.out.println("Good bye, application ending");
                        break;
                    default:
                        System.out.println("Invalid option");
                }
            } catch (InputMismatchException e) {
                System.out.println("Just numbers allowed");
                sc.nextLine();
            } catch (DataException e) {
                System.out.println(e.getMessage());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format");
            }
        }
        System.out.print("================ APP FINISHED ================");
        sc.close();
    }
}

