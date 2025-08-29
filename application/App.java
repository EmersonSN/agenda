package application;

import model.entities.Client;
import model.exceptions.DataException;
import model.services.ScheduleService;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Client> clients = new ArrayList<>();
        ScheduleService service = new ScheduleService();
        int option = 0;

        service.loadData(clients);

        while (option != 9) {
            try {
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
                        service.userInsert(sc, clients);
                        break;
                    case 2:
                        service.userList(clients);
                        break;
                    case 3:
                        service.userAlter(sc, clients);
                        break;
                    case 4:
                        service.userDelete(sc, clients);
                        break;
                    case 5:
                        service.findByName(sc, clients);
                        break;
                    case 6:
                        service.findByEmail(sc, clients);
                        break;
                    case 7:
                        service.loadData(clients);
                        break;
                    case 8:
                        service.saveData(clients);
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
            } catch (NumberFormatException e){
                System.out.println("Number format: "+ e.getMessage());
            }
        }
        System.out.print("================ APP FINISHED ================");
        sc.close();
    }
}

