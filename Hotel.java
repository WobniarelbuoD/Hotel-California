import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Hotel {
	public String[] History = {};
	public static ArrayList<Room> Rooms = new ArrayList<Room>();
	public static Integer Price = 20;
	public static Double BusinessPrice = Price * 1.5;
	
	public static void main(String[] args) {
		setRooms("6");
		System.out.println("Welcome to the Hotel California!");  
		
		Scanner scanner = new Scanner(System.in);
        while (true) {
            if (!scanner.hasNextLine()) {
                break;
            }
            
            String input = scanner.nextLine();
            callChecker(input);
            
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
        }
        
        scanner.close();
        System.out.println("Scanner closed. Program terminated.");    
	}
	static void setRooms(String Nmb) {
		if(isInteger(Nmb)) {
			Integer Numb = Integer.parseInt(Nmb);
			if(Numb % 2 == 0) {
				if(Rooms.size() > 0) {
					if(Rooms.size() > Numb) {
						Integer siz = Rooms.size() - Numb;
						for (int i = 0; i < siz; i++) {
							Rooms.removeLast();
						}
					}else {
						addRooms(Rooms.size(), Numb);
					}
				}else {
					addRooms(0, Numb);
				}
			}else {
				System.out.println("Only an even number of rooms is allowed command example: rooms 8");
			}
		}else {
			System.out.println("Invalid argument for rooms command example: rooms 8");
		}
	}
	
	static void addRooms(Integer start, Integer end) {
		Room rm;
		for (int i = start; i < end; i++) {
			if(i % 2 > 0){
				rm = new Room();
				rm.setRoomNumber(i+1);
				rm.setRoomPrice(Price);
				Rooms.add(rm);
			}else {
				rm = new BusinessRoom();
				rm.setRoomNumber(i+1);
				rm.setRoomPrice(Price);
				Rooms.add(rm);
			}
			
		}
		System.out.println("Room number set to: " + Rooms.size());
	}
	
	static void callChecker(String Command) {
		String[] Comm = Command.split(" ");
		if(Comm[0].equalsIgnoreCase("register")) {
			if(Comm.length == 3) {
				register(Comm[1], Comm[2]);
			}else {
				System.out.println("Not a valid number of arguments for register command example: register Bob Ross.");
			}
		}else if(Comm[0].equalsIgnoreCase("checkout")) {
			if(Comm.length == 3) {
				checkout(Comm[1], Comm[2]);
			}else {
				System.out.println("Not a valid number of arguments for checkout command example: checkout Bob Ross.");
			}
		}else if(Comm[0].equalsIgnoreCase("vacancy")) {
			if(Comm.length == 2) {
				vacancy(Comm[1]);
			}else {
				vacancy("");
			}
		}else if(Comm[0].equalsIgnoreCase("status")) {
			if(Comm.length == 2) {
				status(Comm[1]);
			}else {
				status("");
			}
		}else if(Comm[0].equalsIgnoreCase("report")) {
			if(Comm.length == 2) {
				report(Comm[1]);
			}else {
				report("");
			}
		}else if(Comm[0].equalsIgnoreCase("prices")) {
			if(Comm.length == 2) {
				changePrices(Comm[1]);
			}else {
				System.out.println("Not a valid number of arguments for prices command example: prices 420.");
			}
		}else if(Comm[0].equalsIgnoreCase("rooms")) {
			if(Comm.length == 2) {
				setRooms(Comm[1]);
			}
		}else if(Comm[0].equalsIgnoreCase("help")) {
			System.out.println("List of available commands:");
			System.out.println("register 'name' 'surname' example: register Bob Ross.");
			System.out.println("checkout 'name' 'surname' example: checkout Bob Ross.");
			System.out.println("vacancy 'roomNumber'(optional) example1: vacancy 1. example2: vacancy.");
			System.out.println("status 'roomNumber'(optional) example1: status 1. example2: status.");
			System.out.println("report 'roomNumber'(optional) example1: report 1. example2: report.");
			System.out.println("prices 'price' example: price 420.");
			System.out.println("rooms 'number'(only even numbers) example: rooms 8.");
		}else {
			System.out.println("invalid command. Type 'help' for available commands.");
		}
	}
	
	static void register(String Nm, String Snm) {
		ArrayList<String> history;
		outerloop:{
			for(Room rm : Rooms) {
				if(rm.getOccupancy().contentEquals("free")) {
					rm.setName(Nm);
					rm.setSurname(Snm);
					rm.setOccupancy("occupied");
					history = rm.getOccupantHistory();
					history.add(Nm + " " + Snm);
					rm.setOccupantHistory(history);
					rm.setFundsAcquired(rm.getFundsAcquired() + rm.getRoomPrice());
					System.out.println("Guest: " + Nm + " " + Snm + " has checked into room: " + rm.getRoomNumber().toString()+".");
					break outerloop;
				}
			}
			System.out.println("No rooms available.");
		}
	}
	
	static void checkout(String Nm, String Snm) {
		for(Room rm : Rooms) {
			if(rm.getName().contentEquals(Nm) && rm.getSurname().contentEquals(Snm)) {
				rm.setName("");
				rm.setSurname("");
				rm.setOccupancy("free");
				System.out.println("Guest: " + Nm + " " + Snm + " has checked out of room: " + rm.getRoomNumber().toString()+".");
				break;
			}
		}
	}
	
	static void vacancy(String Numb) {
		if(Numb.equals("")) {
			for(Room rm : Rooms) {
				if(rm.getOccupancy().equals("occupied")) {
					System.out.println("Room: " + Integer.valueOf(rm.getRoomNumber()) + " is occupied by " + rm.getName() + " " + rm.getSurname() + ".");
				}else {
					System.out.println("Room: " + Integer.valueOf(rm.getRoomNumber()) + " is currently empty.");
				}
			}
		}else if(isInteger(Numb)) {
			outerloop:{
				for(Room rm : Rooms) {
					if(rm.getRoomNumber() == Integer.parseInt(Numb)) {
						if(rm.getOccupancy().equals("occupied")) {
							System.out.println("Room: " + Numb + " is occupied by " + rm.getName() + " " + rm.getSurname() + ".");
						}else {
							System.out.println("Room: " + Numb + " is currently empty.");
						}
						break outerloop;
					}
				}
				System.out.println("Room: " + Numb + " not found.");
			}
		}else {
			System.out.println("Invalid argument for vacancy command example1: vacancy 1. example2: vacancy");
			}
	}
	
	static void status(String Numb) {
		if(Numb.equals("")) {
			for(Room rm : Rooms) {
				System.out.println("Room is currently " + rm.getOccupancy() + ". Occupant history: " + occupancyMsg(rm.getOccupantHistory()));
			}
		}else if(isInteger(Numb)) {
			outerloop:{
				for(Room rm : Rooms) {
					if(rm.getRoomNumber() == Integer.parseInt(Numb)) {
						System.out.println("Room is currently " + rm.getOccupancy() + ". Occupant history: " + occupancyMsg(rm.getOccupantHistory()));
						break outerloop;
					}
					System.out.println("Room: " + Numb + " not found.");
				}
			}
		}else {
			System.out.println("Invalid argument for status command example1: status 1. example2: status");
		}
	}
	
	static void report(String Numb) {
		if(Numb.equals("")) {
			for(Room rm : Rooms) {
				System.out.println("Room: " + Integer.valueOf(rm.getRoomNumber()) + " has been occupied " + Integer.valueOf(rm.OccupantHistory.size()) + " times. Funds acquired: " + Integer.valueOf(rm.getFundsAcquired()));
			}
		}else if(isInteger(Numb)) {
			outerloop:{
				for(Room rm : Rooms) {
					if(rm.getRoomNumber() == Integer.parseInt(Numb)) {
						System.out.println("Room: " + Numb + " has been occupied " + Integer.valueOf(rm.OccupantHistory.size()) + " times. Funds acquired: " + Integer.valueOf(rm.getFundsAcquired()));
						break outerloop;
					}
				}
				System.out.println("Room: " + Numb + " not found.");
			}
		}else {
			System.out.println("Invalid argument for report command example1: report 1. example2: report");
		}
	}
	
	static void changePrices(String Numb) {
		if(isInteger(Numb)) {
			for(Room rm : Rooms) {
				rm.setRoomPrice(Integer.parseInt(Numb));
			}
			System.out.println("Prices updated!");
		}else {
			System.out.println("Invalid argument for prices command example: prices 420.");
		}
	}
	
	static String occupancyMsg(ArrayList<String> list) {
		String msg = "";
		for(String occ : list) {
			if(msg.equals("")) {
				msg = occ;
			}else {
				msg = msg + ", " + occ;
			}
		}
		return msg;
	}
	
	public static boolean isInteger(String s) {
	    return isInteger(s,10);
	}
	
	public static boolean isInteger(String s, int radix) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}
	
	static class Room{
		private String Name = "";
		private String Surname = "";
		private Integer FundsAcquired = 0;
		private String Occupancy = "free";
		private Integer RoomNumber;
		private Integer RoomPrice;
		private ArrayList<String> OccupantHistory = new ArrayList<String>();
		
		public String getName() {
			return Name;
		}
		public void setName(String name) {
			Name = name;
		}
		
		public String getSurname() {
			return Surname;
		}
		public void setSurname(String surname) {
			Surname = surname;
		}
		
		public Integer getFundsAcquired() {
			return FundsAcquired;
		}
		public void setFundsAcquired(Integer fundsAcquired) {
			FundsAcquired = fundsAcquired;
		}
		
		public String getOccupancy() {
			return Occupancy;
		}
		public void setOccupancy(String occupancy) {
			Occupancy = occupancy;
		}
		
		public Integer getRoomNumber() {
			return RoomNumber;
		}
		public void setRoomNumber(Integer roomNumber) {
			RoomNumber = roomNumber;
		}
		
		public Integer getRoomPrice() {
			return RoomPrice;
		}
		public void setRoomPrice(Integer roomPrice) {
			RoomPrice = roomPrice;
		}
		
		public ArrayList<String> getOccupantHistory() {
			return OccupantHistory;
		}
		public void setOccupantHistory(ArrayList<String> occupantHistory) {
			Collections.sort(occupantHistory);
			OccupantHistory = occupantHistory;
		}
		
	}
	
	static class BusinessRoom extends Room{
		private Integer RoomPrice;
		public Integer getRoomPrice() {
			return RoomPrice;
		}
		public void setRoomPrice(Integer roomPrice) {
			double dbl = roomPrice * 1.5;
			RoomPrice = (int)dbl;
		}
	}
}
