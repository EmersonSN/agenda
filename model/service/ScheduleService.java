package model.service;

import model.entities.Client;
import model.entities.Schedule;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class ScheduleService {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public void userInsert(Scanner sc, List<Schedule> schedules) {
        System.out.println("Cadastre o usuário: ");
        System.out.print("Nome: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Data agendamento (dd/MM/yyyy HH:mm): ");
        String date = sc.nextLine();
        LocalDateTime scheduleDate = LocalDateTime.parse(date, formatter);
        System.out.print("Telefone para contato: ");
        long phone = Long.parseLong(sc.nextLine());
        try {
            int id = schedules.size();
            schedules.add(new Schedule(id, scheduleDate, new Client(id, name, email, phone)));
        } catch (IllegalArgumentException e) {
            System.out.println("Registering error: " + e.getMessage());
        }

    }

    public void userList(List<Schedule> schedules) {
        System.out.println("ID | NOME | EMAIL | DATE | PHONE");
        for (Schedule schedule : schedules) {
            System.out.printf("%s%n", schedule);
        }

    }

    public void userAlter(Scanner sc, List<Schedule> schedules) {
        System.out.println("Escolha um cod para alterar");
        userList(schedules);
        System.out.print("Digite o código: ");
        int cod = Integer.parseInt(sc.nextLine());
        System.out.println();
        if (cod >= 0 && cod < schedules.size()) {
            System.out.println("O que você deseja alterar?");
            System.out.println("1 - Nome\n2 - Email\n3 - Date\n4 - Phone");
            int option = sc.nextInt();
            switch (option) {
                case 1:
                    System.out.print("Agora digite o novo nome: ");
                    String name = sc.nextLine();
                    schedules.get(cod).getClient().setName(name);
                    break;
                case 2:
                    System.out.print("Agora digite o novo email: ");
                    String email = sc.nextLine();
                    schedules.get(cod).getClient().setEmail(email);
                    break;
                case 3:
                    System.out.print("Agora digite a nova data (dd/MM/yyyy HH:mm): ");
                    String date = sc.nextLine();
                    LocalDateTime scheduleDate = LocalDateTime.parse(date, formatter);
                    schedules.get(cod).setScheduleDate(scheduleDate);
                    break;
                case 4:
                    System.out.print("Agora digite o novo telefone: ");
                    long phone = Long.parseLong(sc.nextLine());
                    schedules.get(cod).getClient().setPhone(phone);
                    break;
                default:
                    System.out.println("Invalid code");
                    break;
            }

        } else {
            System.out.println("Invalid cod");
        }
        System.out.println("Data updated successfully");
        userList(schedules);
    }

    public void userDelete(Scanner sc, List<Schedule> schedules) {
        System.out.println("Escolha um cod para excluir");
        userList(schedules);
        System.out.print("Digite o código: ");
        int cod = sc.nextInt();
        if (cod >= 0 && cod < schedules.size()) {
            schedules.remove(cod);
        } else {
            System.out.println("Invalid cod");
        }
        userList(schedules);
    }

    public void findByName(Scanner sc, List<Schedule> schedules) {
        boolean found = false;
        System.out.print("Digite o nome de quem deseja encontrar: ");
        sc.nextLine();
        String find = sc.nextLine();
        for (int i = 0; i < schedules.size(); i++) {
            if (schedules.get(i).getClient().getName().toLowerCase().contains(find.toLowerCase())) {
                System.out.printf("%d - %s%n", i, schedules.get(i));
                found = true;
                break;
            }
            if (!found) {
                System.out.println("Usuário não encontrado");
            }
        }
    }

    public void findByEmail(Scanner sc, List<Schedule> schedules) {
        boolean found = false;
        System.out.print("Digite o email de quem deseja encontrar: ");
        sc.nextLine();
        String find = sc.nextLine();
        for (int i = 0; i < schedules.size(); i++) {
            if (schedules.get(i).getClient().getEmail().toLowerCase().contains(find.toLowerCase())) {
                System.out.printf("%d - %s%n", i, schedules.get(i));
                found = true;
                break;
            }
            if (!found) {
                System.out.println("Usuário não encontrado");
            }
        }
    }

    public void saveData(List<Schedule> schedules) {
        int savedCount = 0;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("c:\\temp\\schedule.txt", true))) {
            for (Schedule schedule : schedules) {
                if (validationData(schedule)) {
                    bw.write(String.format("%d,%s,%s,%s,%d%n",
                            schedule.getScheduleId(),
                            schedule.getClient().getName(),
                            schedule.getClient().getEmail(),
                            schedule.getScheduleDate().format(formatter),
                            schedule.getClient().getPhone()
                    ));
                    savedCount++;
                }
            }
            System.out.println(savedCount > 0 ? "Saved successfully" : "No new data to save");
        } catch (IOException e) {
            System.out.println("Error Writing: " + e.getMessage());
        }
    }

    public void loadData(List<Schedule> schedules) {
        try (BufferedReader br = new BufferedReader(new FileReader("c:\\temp\\schedule.txt"))) {
            String line;
            int isNotEmpty = 0;
            while ((line = br.readLine()) != null) {
                String[] div = line.split(",");
                int id = Integer.parseInt(div[0]);
                String name = div[1];
                String email = div[2];
                LocalDateTime scheduleDate = LocalDateTime.parse(div[3], formatter);
                long phone = Long.parseLong(div[4]);
                schedules.add(new Schedule(id, scheduleDate, new Client(id, name, email, phone)));
                isNotEmpty++;
            }
            if (isNotEmpty > 0) {
                userList(schedules);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean validationData(Schedule schedule) {
        try (BufferedReader br = new BufferedReader(new FileReader("c:\\temp\\schedule.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] div = line.split(",");
                int id = Integer.parseInt(div[0]);
                if (schedule.getScheduleId() == id) {
                    return false;
                }
            }
        } catch (IOException e) {
            System.out.println("Error on validationData: " + e.getMessage());
        }
        return true;
    }
}

