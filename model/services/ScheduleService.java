package model.services;

import model.entities.Client;
import model.entities.Schedule;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class ScheduleService {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public void userInsert(Scanner sc, List<Client> clients) {
        System.out.println("Cadastre o usuário: ");
        System.out.print("Nome: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Data agendamento (dd/MM/yyyy HH:mm): ");
        LocalDateTime scheduleDate = LocalDateTime.parse(sc.nextLine(), formatter);
        System.out.print("Telefone para contato: ");
        long phone = Long.parseLong(sc.nextLine());
        try {
            int id = clients.size();
            Client client = new Client(id, name, email, phone);
            clientExists(clients, client);
            client.getSchedules().add(new Schedule(id, scheduleDate));
            clients.add(client);
            System.out.println("Chegou aqui");
        } catch (IllegalArgumentException e) {
            System.out.println("Registering error: " + e.getMessage());
        }

    }

    public void userList(List<Client> clients) {
        System.out.println("ID | NOME | EMAIL | DATE | PHONE");
        for (Client client : clients) {
            System.out.printf("%s%n", client);
        }
    }

    public void userAlter(Scanner sc, List<Client> clients) {
        System.out.println("Escolha um cod para alterar");
        userList(clients);
        System.out.print("Digite o código: ");
        int cod = Integer.parseInt(sc.nextLine());
        System.out.println();
        if (cod >= 0 && cod < clients.size()) {
            System.out.println("O que você deseja alterar?");
            System.out.println("1 - Nome\n2 - Email\n3 - Date\n4 - Phone");
            int option = Integer.parseInt(sc.nextLine());
            switch (option) {
                case 1:
                    System.out.print("Agora digite o novo nome: ");
                    String name = sc.nextLine();
                    clients.get(cod).setName(name);
                    break;
                case 2:
                    System.out.print("Agora digite o novo email: ");
                    String email = sc.next();
                    clients.get(cod).setEmail(email);
                    break;
                case 3:
                    System.out.print("Agora digite a nova data (dd/MM/yyyy HH:mm): ");
                    LocalDateTime scheduleDate = LocalDateTime.parse(sc.nextLine(), formatter);
                    clients.get(cod).getSchedules().get(0).setScheduleDate(scheduleDate);
                    break;
                case 4:
                    System.out.print("Agora digite o novo telefone: ");
                    long phone = Long.parseLong(sc.nextLine());
                    clients.get(cod).setPhone(phone);
                    break;
                default:
                    System.out.println("Invalid code");
                    break;
            }

        } else {
            System.out.println("Invalid cod");
        }
        System.out.println("Data updated successfully");
        userList(clients);
    }

    public void userDelete(Scanner sc, List<Client> clients) {
        System.out.println("Escolha um cod para excluir");
        userList(clients);
        System.out.print("Digite o código: ");
        int cod = Integer.parseInt(sc.nextLine());
        if (cod >= 0 && cod < clients.size()) {
            clients.remove(cod);
            System.out.println("Excluido");
        } else {
            System.out.println("Invalid cod");
        }
        userList(clients);
    }

    public void findByName(Scanner sc, List<Client> clients) {
        boolean found = false;
        System.out.print("Digite o nome de quem deseja encontrar: ");
        String find = sc.nextLine();
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getName().toLowerCase().contains(find.toLowerCase())) {
                System.out.printf("%d - %s%n", i, clients.get(i));
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Usuário não encontrado");
        }
    }

    public void findByEmail(Scanner sc, List<Client> clients) {
        boolean found = false;
        System.out.print("Digite o email de quem deseja encontrar: ");
        String find = sc.nextLine();
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getEmail().toLowerCase().contains(find.toLowerCase())) {
                System.out.printf("%d - %s%n", i, clients.get(i));
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Usuário não encontrado");
        }
    }

    public void saveData(List<Client> clients) {
        int savedCount = 0;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("c:\\temp\\schedule.txt", true))) {
            for (Client client : clients) {
                if (validationData(client)) {
                    bw.write(String.format("%d,%s,%s,%s,%d%n",
                            client.getClientId(),
                            client.getName(),
                            client.getEmail(),
                            client.getSchedules().get(0).getScheduleDate().format(formatter),
                            client.getPhone()
                    ));
                    savedCount++;
                }
            }
            System.out.println(savedCount > 0 ? "Saved successfully" : "No new data to save");
        } catch (IOException e) {
            System.out.println("Error Writing: " + e.getMessage());
        }
    }

    public void loadData(List<Client> clients) {
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
                Client client = new Client(id, name, email, phone);
                client.getSchedules().add(new Schedule(id, scheduleDate));
                clients.add(client);
                isNotEmpty++;
            }
            if (isNotEmpty > 0) {
                userList(clients);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean validationData(Client client) {
        try (BufferedReader br = new BufferedReader(new FileReader("c:\\temp\\schedule.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] div = line.split(",");
                int id = Integer.parseInt(div[0]);
                if (client.getClientId() == id) {
                    return false;
                }
            }
        } catch (IOException e) {
            System.out.println("Error on validationData: " + e.getMessage());
        }
        return true;
    }

    public void clientExists(List<Client> clients, Client client) {
        int hashCode = client.hashCode();
        int cod = 0;
        for (Client c : clients) {
            if (hashCode == c.hashCode()) {
                cod = clients.size();
                break;
            }
        }
        if (cod != 0) {
            throw new IllegalArgumentException("Client already exists");
        }
    }
}

