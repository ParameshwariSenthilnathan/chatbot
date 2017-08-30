package websocket.chat;

public class Main {
	
	public static void main(String[] args) {
		Main main = new Main();
		main.getData();
	}
	
	public void getData(){
		System.out.println(DataReader.getTheDataFromCSV(this.getClass().getResource("/Data.csv").getPath()));
	}

}
