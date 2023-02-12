package application.naive.client;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MainClient {
	private Scanner input = new Scanner(System.in);
	private NaiveApplicationController naiveApplicationController;
	private String alias;
	private String menu = "\nSelect:\n 1.Apply configured filters to file and show table\n 2.Save filtered file\n 3.Show line chart\n 4.Show board chart\n 5.Configure/Change filters\n 6.Register another file\n 7.Exit\n \n Entering: ";
	private HashMap<String, List<String>> atomicFilters;
	private List<String[]> result;
	
	
	
	public void handleRegistration() {
		naiveApplicationController = new NaiveApplicationController();
		System.out.print("Please enter the path of the file you want to register: ");
		String path = input.nextLine();
		File file = new File(path);	
		while(path.isEmpty()==true || file.exists()==false) {
			System.out.print("\n(?) Please re-enter a valid path: ");
			path = input.nextLine();
			file = new File(path);				
		}
		
		System.out.print("Please enter the field separator: ");
		String separator = input.nextLine();
		while(separator.isEmpty()) {
			System.out.print("\n(?) Please re-enter a valid field separator: ");
			separator = input.nextLine();
		}
		
		System.out.print("Please enter an alias: ");
		String alias = input.nextLine();		
		while(alias.isEmpty()) {
			System.out.print("\n(?) Please re-enter a valid alias: ");
			alias = input.nextLine();
		}
		
		naiveApplicationController.registerFile(alias, path, separator);
		this.alias = alias;
		System.out.println("\nFile is registered successfuly!");
		handleFilters();
		handleSelection();
	}
	
	public void handleFilters() {
		System.out.println("\nAvailable Fields:");
		atomicFilters = new HashMap<String, List<String>>();
		String[] fields = naiveApplicationController.getColumnNames(alias);
		for(String field:fields) {
			System.out.println(field);
		}
		System.out.print("\nSelect the fields that you want to apply filters on (type the fields separated with '/'): ");
		String selection = input.nextLine();
		System.out.println("\nSelect the filters(type the filters separated with '/': ");
		String[] selectionArray=selection.split("/");
		for(String selectedField:selectionArray) {
			System.out.print("-"+selectedField+"-: " );
			List<String> filters;
			selection = input.nextLine();
			selectionArray=selection.split("/");
			filters = Arrays.asList(selectionArray);
			atomicFilters.put(selectedField, filters);
		}
		System.out.println("\n Filters are successfuly setup!");
		return;		
	}
	
	public void handleBoardChart() {
		if(result == null) {
			System.out.print("\n(?) You have not applied your filters to the input file yet! \n");
			return;
		}
		System.out.print("Available fields:\n");
		String[] fields = naiveApplicationController.getColumnNames(alias);
		for(String field:fields) {
			System.out.println(field);
		}
		System.out.print("\nEnter the field that you want to place in the x axis(varying fields are suggested): ");
		String xAxis = input.nextLine(); 
		System.out.print("Enter the field that you want to place in the y axis(varying fields are suggested): ");
		String yAxis = input.nextLine(); 
		System.out.print("Enter the name of the output png: ");
		String pngFileName = input.nextLine(); 
		try {
			naiveApplicationController.showSingleSeriesBarChart(alias, result, xAxis, yAxis, pngFileName);
		}catch(Exception e) {
			System.out.println("\n(?) Something went wrong. Try again");
			return;
		}	
		System.out.print("\nDo you want to also generate line chart with the same x/y axis(Y/N)?: ");
		String question = input.nextLine();
		while(question.equals("Y")==false && question.equals("N")==false) {
			System.out.print("\n(?) Do you want to also generate line chart with the same x/y axis(Y/N)? Please answer with Y/N: ");
			question = input.nextLine();					
		}
		if(question.equals("Y")) {
			try {
			naiveApplicationController.showSingleSeriesLineChart(alias, result, xAxis, yAxis, pngFileName+"Line");
			}catch(Exception e) {
				System.out.println("\n(?) Something went wrong. Try again");
				return;
			}
		}else if(question.equals("N")) {
			return;
		}	
	}
	
	public void handleLineChart() {
		if(result == null) {
			System.out.print("\n(?) You have not applied your filters to the input file yet! \n");
			return;
		}
		System.out.print("Available fields:\n");
		String[] fields = naiveApplicationController.getColumnNames(alias);
		for(String field:fields) {
			System.out.println(field);
		}
		System.out.print("\nEnter the field that you want to place in the x axis(varying fields are suggested): ");
		String xAxis = input.nextLine(); 
		System.out.print("Enter the field that you want to place in the y axis(varying fields are suggested): ");
		String yAxis = input.nextLine(); 
		System.out.print("Enter the name of the output png: ");
		String pngFileName = input.nextLine(); 
		try {
			naiveApplicationController.showSingleSeriesLineChart(alias, result, xAxis, yAxis, pngFileName);
		}catch(Exception e) {
			System.out.println("\n(?) Something went wrong. Try again");
			return;
		}
		System.out.print("\nDo you want to also generate board chart with the same x/y axis(Y/N)?: ");
		String question = input.nextLine();
		while(question.equals("Y")==false && question.equals("N")==false) {
			System.out.print("\n(?) Do you want to also generate board chart with the same x/y axis(Y/N)? Please answer with Y/N: ");
			question = input.nextLine();					
		}
		if(question.equals("Y")) {
			try {
				naiveApplicationController.showSingleSeriesBarChart(alias, result, xAxis, yAxis, pngFileName+"Board");
				}catch(Exception e) {
					System.out.println("\n(?) Something went wrong. Try again");
					return;
				}
		}else if(question.equals("N")) {
			return;
		}
		
	}
	
	public void handleSelection() {	
		while(true) {
			System.out.print(menu);
			int selection = input.nextInt();
			input.nextLine();
			while(selection<1 || selection>7) {
				System.out.print("\n(?) Please select a valid option\n"+menu);
				selection = input.nextInt();
				input.nextLine();
			}
			if(selection == 1) {
				handleJTable();
			}else if(selection == 2) {
				handleSavingResultToFile();
			}else if(selection == 3) {
				handleLineChart();
			}else if(selection == 4) {
				handleBoardChart();
			}else if(selection == 5) {
				result = null;
				handleFilters();
			}else if(selection == 6) {
				System.out.print("\nAre you sure you want to to register another file?(Y/N): ");
				String question = input.nextLine();
				while(question.equals("Y")==false && question.equals("N")==false) {
					System.out.print("\n(?) Are you sure you want to to register another file? Please answer with Y/N: ");
					question = input.nextLine();					
				}
				System.out.print("\n");
				if(question.equals("Y")) {
					handleRegistration();
				}else if(question.equals("N")) {
					handleSelection();
				}
			}else if(selection == 7) {
				System.exit(0);
			}
		}
	}
	
	public void handleJTable() {
		result = new ArrayList<String[]>();
		
		try {
			result = naiveApplicationController.executeFilterAndShowJTable(alias, atomicFilters);
		}catch(NullPointerException e) {
			result = null;
			System.out.println("\n(?) Something went wrong with the filters you tried to apply.\n    Configure/Change your filters and try again!");
		}
	}
	
	public void handleSavingResultToFile() {
		if(result == null) {
			System.out.print("\n(?) You have not applied your filters to the input file yet! \n");
			return;
		}else {
			System.out.print("\n Enter the path including the name of the output file: ");
			String path = input.next();
			try {
				naiveApplicationController.saveToResultTextFile(path, result);
			}catch(Exception e) {
				System.out.println("\n(?) Something went wrong with the path you entered.\n    Try saving filtered file again.");
				return;
			}
			System.out.print("File Saved!\n");
			return;
		}
	}
	
	public static void main(String[] args) {
		MainClient phaseHandler = new MainClient();
		System.out.println("Welcome!\n");
		phaseHandler.handleRegistration();				
	}
}

