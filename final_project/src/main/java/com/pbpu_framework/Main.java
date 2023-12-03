package com.pbpu_framework;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.sql.SQLException;

public class Main {
    private static String dataType = "";

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        Scanner scanner = new Scanner(System.in);
        FileTypeHandler<List<Data>> fileTypeHandler = null;
        FileTypeHandler<List<Data>> txtFileHandler = new FileTypeHandler<>(new TxtFileHandler());
        FileTypeHandler<List<Data>> csvFileHandler = new FileTypeHandler<>(new CsvFileHandler());
        FileTypeHandler<List<Data>> jsonFileHandler = new FileTypeHandler<>(new JsonFileHandler());
        FileTypeHandler<List<Data>> sqliteFileHandler = new FileTypeHandler<>(new SQLiteFileHandler());
        FileTypeHandler<List<Data>> serializedFileHandler = new FileTypeHandler<>(new SerializedObjectFileHandler());

        while (true) {
            System.out.println("Choose a file format:");
            System.out.println("1. CSV");
            System.out.println("2. TXT");
            System.out.println("3. JSON");
            System.out.println("4. SQLite");
            System.out.println("5. Serialized Object");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    fileTypeHandler = new CsvFileHandlerFactory().createFileHandler();
                    dataType = "CSV";
                    System.out.println("You are currently writing to " + dataType + " file formats.");
                    break;
                case 2:
                    fileTypeHandler = new TxtFileHandlerFactory().createFileHandler();
                    dataType = "TXT";
                    System.out.println("You are currently writing to " + dataType + " file formats.");
                    break;
                case 3:
                    fileTypeHandler = new JsonFileHandlerFactory().createFileHandler();
                    dataType = "JSON";
                    System.out.println("You are currently writing to " + dataType + " file formats.");
                    break;
                case 4:
                    fileTypeHandler = new SQLiteFileHandlerFactory().createFileHandler();
                    dataType = "SQLite";
                    System.out.println("You are currently writing to " + dataType + " file formats.");
                    break;
                case 5:
                    // lama : fileTypeHandler = serializedFileHandler;
                    fileTypeHandler = new SerializedObjectFileHandlerFactory().createFileHandler();

                    dataType = "SO";
                    System.out.println("You are currently writing to " + dataType + " file formats.");
                    break;

                case 6:
                    System.out.println("Exiting program.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please choose again.");
                    continue;
            }

            menuCommandLine(fileTypeHandler, scanner);
        }
    }

    private static void menuCommandLine(FileTypeHandler<List<Data>> fileTypeHandler, Scanner scanner)
            throws IOException {
        while (true) {
            System.out.println("Choose an operation:");
            System.out.println("1. Create");
            System.out.println("2. Read");
            System.out.println("3. Update");
            System.out.println("4. Delete");
            System.out.println("5. Read All Data");
            System.out.println("6. Back to file format selection");

            System.out.println("Enter your choice: ");
            int operation = scanner.nextInt();

            switch (operation) {
                case 1:
                    createData(fileTypeHandler, scanner);
                    break;
                case 2:
                    readDataByIndex(fileTypeHandler, scanner);
                    break;
                case 3:
                    updateData(fileTypeHandler, scanner);
                    break;
                case 4:
                    deleteData(fileTypeHandler, scanner);
                    break;
                case 5:
                    readData(fileTypeHandler, scanner);
                    break;
                case 6:
                    System.out.println("Returning to file format selection.");
                    return;
                default:
                    System.out.println("Invalid choice. Please choose again.");
            }
        }
    }

    private static void createData(FileTypeHandler<List<Data>> fileTypeHandler, Scanner scanner) throws IOException {
        scanner.nextLine();
        System.out.println("Enter subject:");
        String subject = scanner.nextLine();

        System.out.println("Enter description:");
        String description = scanner.nextLine();

        Data newData = new Data(subject, description);

        List<Data> dataList;

        // Choose the file name based on the file type
        if (dataType.equals("CSV")) {
            dataList = fileTypeHandler.readFile("csv_data.csv");
        } else if (dataType.equals("TXT")) {
            dataList = fileTypeHandler.readFile("txt_data.txt");
        } else if (dataType.equals("JSON")) {
            dataList = fileTypeHandler.readFile("json_data.json");
        } else if (dataType.equals("SQLite")) {
            dataList = fileTypeHandler.readFile("dataTable");
        } else if (dataType.equals("SO")) {
            dataList = fileTypeHandler.readFile("serialized_data.ser");
        } else {
            throw new IllegalArgumentException("Unsupported file type");
        }

        if (dataList == null) {
            dataList = new ArrayList<>(); // Initialize the list if it's null
        }

        dataList.add(newData);

        try {
            // Use the appropriate file name based on the file type
            if (dataType.equals("CSV")) {
                fileTypeHandler.writeFile("csv_data.csv", dataList);
            } else if (dataType.equals("TXT")) {
                fileTypeHandler.writeFile("txt_data.txt", dataList);
            } else if (dataType.equals("JSON")) {
                fileTypeHandler.writeFile("json_data.json", dataList);
            } else if (dataType.equals("SQLite")) {
                fileTypeHandler.writeFile("dataTable", dataList);
            } else if (dataType.equals("SO")) {
                fileTypeHandler.writeFile("serialized_data.ser", dataList);
            }

            System.out.println("Data created successfully.");
        } catch (IOException e) {
            System.out.println("Error creating data: " + e.getMessage());
        }
    }

    private static void readData(FileTypeHandler<List<Data>> fileTypeHandler,
            Scanner scanner) throws IOException {
        List<Data> dataList;

        // Choose the file name based on the file type
        if (dataType.equals("CSV")) {
            dataList = fileTypeHandler.readFile("csv_data.csv");
        } else if (dataType.equals("TXT")) {
            dataList = fileTypeHandler.readFile("txt_data.txt");
        } else if (dataType.equals("JSON")) {
            dataList = fileTypeHandler.readFile("json_data.json");
        } else if (dataType.equals("SQLite")) {
            dataList = fileTypeHandler.readFile("dataTable");
        } else if (dataType.equals("SO")) {
            dataList = fileTypeHandler.readFile("serialized_data.ser");
        } else {
            throw new IllegalArgumentException("Unsupported file type");
        }
        System.out.println("Data:");

        for (Data data : dataList) {
            System.out.println(data);
        }
    }

    private static void updateData(FileTypeHandler<List<Data>> fileTypeHandler, Scanner scanner) throws IOException {
        int index = readDataByIndex(fileTypeHandler, scanner);

        if (index != -1) {
            System.out.println("Do you want to update this data? (Y/N)");
            String confirmation = scanner.nextLine().trim();

            if (confirmation.equalsIgnoreCase("Y")) {
                System.out.println("Enter new subject:");
                String newSubject = scanner.nextLine();

                System.out.println("Enter new description:");
                String newDescription = scanner.nextLine();

                List<Data> dataList;

                // Choose the file name based on the file type
                if (dataType.equals("CSV")) {
                    dataList = fileTypeHandler.readFile("csv_data.csv");
                } else if (dataType.equals("TXT")) {
                    dataList = fileTypeHandler.readFile("txt_data.txt");
                } else if (dataType.equals("JSON")) {
                    dataList = fileTypeHandler.readFile("json_data.json");
                } else if (dataType.equals("SQLite")) {
                    dataList = fileTypeHandler.readFile("dataTable");
                } else if (dataType.equals("SO")) {
                    dataList = fileTypeHandler.readFile("serialized_data.ser");
                } else {
                    throw new IllegalArgumentException("Unsupported file type");
                }

                Data updatedData = new Data(newSubject, newDescription);
                dataList.set(index, updatedData);

                try {
                    // Use the appropriate file name based on the file type
                    if (dataType.equals("CSV")) {
                        fileTypeHandler.writeFile("csv_data.csv", dataList);
                    } else if (dataType.equals("TXT")) {
                        fileTypeHandler.writeFile("txt_data.txt", dataList);
                    } else if (dataType.equals("JSON")) {
                        fileTypeHandler.writeFile("json_data.json", dataList);
                    } else if (dataType.equals("SQLite")) {
                        fileTypeHandler.writeFile("dataTable", dataList);
                    } else if (dataType.equals("SO")) {
                        fileTypeHandler.writeFile("serialized_data.ser", dataList);
                    }

                    System.out.println("Data created successfully.");
                } catch (IOException e) {
                    System.out.println("Error creating data: " + e.getMessage());
                }
                System.out.println("Data updated successfully.");
            } else {
                System.out.println("Update operation canceled.");
            }
        }
    }

    private static void deleteData(FileTypeHandler<List<Data>> fileTypeHandler, Scanner scanner) throws IOException {
        int index = readDataByIndex(fileTypeHandler, scanner);

        if (index != -1) {
            System.out.println("Do you want to delete this data? (Y/N)");
            String confirmation = scanner.nextLine().trim();

            if (confirmation.equalsIgnoreCase("Y")) {
                List<Data> dataList;

                // Choose the file name based on the file type
                if (dataType.equals("CSV")) {
                    dataList = fileTypeHandler.readFile("csv_data.csv");
                    dataList.remove(index);
                    fileTypeHandler.writeFile("csv_data", dataList);

                } else if (dataType.equals("TXT")) {
                    dataList = fileTypeHandler.readFile("txt_data.txt");
                    dataList.remove(index);
                    fileTypeHandler.writeFile("txt_data.txt", dataList);
                } else if (dataType.equals("JSON")) {
                    dataList = fileTypeHandler.readFile("json_data.json");
                    dataList.remove(index);
                    fileTypeHandler.writeFile("json_data.json", dataList);
                } else if (dataType.equals("SQLite")) {
                    dataList = fileTypeHandler.readFile("dataTable");
                    dataList.remove(index);
                    fileTypeHandler.writeFile("dataTable", dataList);
                } else if (dataType.equals("SO")) {
                    dataList = fileTypeHandler.readFile("serialized_data.ser");
                    dataList.remove(index);
                    fileTypeHandler.writeFile("serialized_data.ser", dataList);
                } else {
                    throw new IllegalArgumentException("Unsupported file type");
                }

                System.out.println("Data deleted successfully.");
            } else {
                System.out.println("Delete operation canceled.");
            }
        }
    }

    private static int readDataByIndex(FileTypeHandler<List<Data>> fileTypeHandler, Scanner scanner)
            throws IOException {
        System.out.println("Enter the index of the data to read:");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        List<Data> dataList;

        // Choose the file name based on the file type
        if (dataType.equals("CSV")) {
            dataList = fileTypeHandler.readFile("csv_data.csv");
        } else if (dataType.equals("TXT")) {
            dataList = fileTypeHandler.readFile("txt_data.txt");
        } else if (dataType.equals("JSON")) {
            dataList = fileTypeHandler.readFile("json_data.json");
        } else if (dataType.equals("SQLite")) {
            dataList = fileTypeHandler.readFile("dataTable");
        } else if (dataType.equals("SO")) {
            dataList = fileTypeHandler.readFile("serialized_data.ser");
        } else {
            throw new IllegalArgumentException("Unsupported file type");
        }

        if (index >= 0 && index < dataList.size()) {
            System.out.println("Data at index " + index + ":");
            System.out.println(dataList.get(index));
            return index;
        } else {
            System.out.println("Invalid index.");
            return -1;
        }
    }

}
