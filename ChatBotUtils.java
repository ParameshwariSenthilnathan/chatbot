package websocket.chat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.json.JsonObject;
import javax.websocket.Session;

public class ChatBotUtils {

	List<Map<String, String>> studentData = DataReader
			.getTheDataFromCSV(this.getClass().getResource("/Data.csv").getPath());
	List<Map<String, String>> staffData = DataReader
			.getTheDataFromCSV(this.getClass().getResource("/StaffData.csv").getPath());
	List<Map<String, String>> eventsData = DataReader
			.getTheDataFromCSV(this.getClass().getResource("/EventsData.csv").getPath());
	List<Map<String, String>> leavesData = DataReader
			.getTheDataFromCSV(this.getClass().getResource("/LeavesData.csv").getPath());
	List<Map<String, String>> placementsData = DataReader
			.getTheDataFromCSV(this.getClass().getResource("/PlacementsData.csv").getPath());
	
	public String getTheResponseBasedOnRequest(Session session, JsonObject jsonMessage, String nickname) {
		
		System.out.println("REQUEST : " + jsonMessage.getString("message"));
		String s = "I didn't get what you mean";
		if (jsonMessage.getString("message").equalsIgnoreCase("hi")) {
			s = "Hello,How are you";
		} else if (jsonMessage.getString("message").contains("good")
				|| jsonMessage.getString("message").contains("fine")
				|| jsonMessage.getString("message").contains("bad")
				|| jsonMessage.getString("message").equals("0")) {
			s = "How can I help you ?";
			s = s + getAllOptions();
		} else if (jsonMessage.getString("message").equalsIgnoreCase("1")
				|| jsonMessage.getString("message").contains("About RMKCET")
				|| jsonMessage.getString("message").contains("RMKCET")) {
			s = "When uttered casually the three lettered word “RMK” touches and tingles every nerve with excitement of anybody who has heard about the working of the institutions under the banner."
					+ "The latest in the group is the RMK college of Engineering and Technology which establishes the eastern horizon of puduvoyal as seen from the golden Quadrilateral NH 5 – which moves north ease and branches off to the two capitals of the country one to Kolkata the old capital and the other the modern capital New Delhi.<br/>";

			s = s+ getAllOptionsWithAnythingElse();
		} else if (jsonMessage.getString("message").equals("2")
				|| jsonMessage.getString("message").contains("About Staff")) {
			s = "Choose the department?";
			s = s + getOptionsStringOverMap(getBranches());
			s=s+ getAllOptionsWithAnythingElse();
			// s = s + getAllOptions();
		} else if (Arrays.asList("CSE", "ECE", "EEE", "MECH").contains(jsonMessage.getString("message"))) {
			StringBuffer sb = new StringBuffer();
			staffData.stream().filter(map -> jsonMessage.getString("message").equals(map.get("Department")))
					.forEach(map -> {
						sb.append(getStringOverMap(map));
						sb.append("<br/><br/>");
					});
			sb.append("Choose another Dpet if you want?");
			sb.append(getOptionsStringOverMap(getBranches()));
			sb.append(getAllOptionsWithAnythingElse());
			s = sb.toString();
		} else if (Arrays.asList("2.1", "2.2", "2.3", "2.4").contains(jsonMessage.getString("message"))) {
			String dept = getBranches().get(jsonMessage.getString("message"));
			StringBuffer sb = new StringBuffer();
			staffData.stream().filter(map -> dept.equals(map.get("Department"))).forEach(map -> {
				sb.append(getStringOverMap(map));
			});
			s = sb.toString();
		}else if (jsonMessage.getString("message").equals("4")
				|| jsonMessage.getString("message").contains("Upcoming  programs")) {
			StringBuffer sb = new StringBuffer();
			eventsData.forEach(map -> {
				sb.append(getStringOverMap(map));
				sb.append("<br/><br/>");
			});
			sb.append(getAllOptionsWithAnythingElse());
			s = sb.toString();
		}else if (jsonMessage.getString("message").equals("5")
				|| jsonMessage.getString("message").contains("Scheduled leave")) {
			StringBuffer sb = new StringBuffer();
			leavesData.forEach(map -> {
				sb.append(getStringOverMap(map));
				sb.append("<br/><br/>");
			});
			sb.append(getAllOptionsWithAnythingElse());
			s = sb.toString();
		}else if (jsonMessage.getString("message").equals("6")
				|| jsonMessage.getString("message").contains("Updated Marks")) {
			StringBuffer sb = new StringBuffer();
				sb.append("<br/>Please enter the candidate id in the form of ID : 'candidateId'- for example Id:4384960");
				sb.append(getAllOptionsWithAnythingElse());
				sb.append("<br/><br/>");			
			s = sb.toString();
		}else if (jsonMessage.getString("message").contains("ID")
				|| jsonMessage.getString("message").contains("Id")
				||jsonMessage.getString("message").contains("id")) {
			String candidateId =  jsonMessage.getString("message").trim().split(":")[1];
			Optional<Map<String, String>> findFirst = studentData.stream().filter(map->map.get("candidateID").equals(candidateId.trim())).findFirst();
			StringBuffer sb = new StringBuffer();
			if(findFirst.isPresent()){
				sb.append(getStringOverMap(findFirst.get()));
				sb.append("<br/><br/>Please enter the candidate id in the form of ID : 'candidateId'- for example Id:4384960");
				sb.append(getAllOptionsWithAnythingElse());
				sb.append("<br/>");
			}else{
				sb.append("<br/>No details found for the scpecified candidate id : " +candidateId);
				sb.append("<br/>Please enter the candidate id in the form of ID : 'candidateId'- for example Id:4384960");
				sb.append(getAllOptionsWithAnythingElse());
				sb.append("<br/>");
			}
					
			s = sb.toString();
		}

		else if (jsonMessage.getString("message").equalsIgnoreCase("About Placements")
				|| jsonMessage.getString("message").equalsIgnoreCase("3")) {
			StringBuffer sb = new StringBuffer();
			placementsData.forEach(map -> {
				sb.append(getStringOverMap(map));
				sb.append("<br/><br/>");
			});
			sb.append(getAllOptionsWithAnythingElse());
			s = sb.toString();
		}else if (jsonMessage.getString("message").contains("No")
				|| jsonMessage.getString("message").equalsIgnoreCase("7")) {
					
			s = "Bye, Have a great Day";
		}/* 
			 * else if
			 * (jsonMessage.getString("message").contains("Students placed")) {
			 * s="CTS-120\nInfosys-77"+ System.lineSeparator()+"Visual BI-23"; }
			 else if (jsonMessage.getString("message").contains("Get All the Students from")) {
			String branchName = jsonMessage.getString("	").trim().split(":")[1];
			System.out.println(branchName.trim());
			StringBuffer sb = new StringBuffer();
			staffData.stream().filter(map -> branchName.trim().equalsIgnoreCase(map.get("Subject"))).forEach(map -> {
				sb.append("<br/>" + map.get("Candidate Name"));
			});
			s = sb.toString();
		} else if (jsonMessage.getString("message").contains("Get The Student Deatils")) {
			String name = jsonMessage.getString("message").trim().split(":")[1];
			System.out.println(name.trim());
			s = "no Student found";
			StringBuffer sb = new StringBuffer();
			staffData.stream().filter(map -> name.trim().equalsIgnoreCase(map.get("Candidate Name"))).forEach(map -> {
				map.forEach((k, v) -> {
					sb.append(",," + k + "=" + v);
				});

			});
			s = sb.toString();
		}*/

		System.out.println("RESPONSE : " + s);
		return s;
	}

	private String getStringOverMap(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		map.forEach((k, v) -> {
			sb.append("<br/>" + k + " = " + v);
		});
		return sb.toString();
	}

	private String getOptionsStringOverMap(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		map.forEach((k, v) -> {
			sb.append("<br/>" + k + ") " + v);
		});
		return sb.toString();
	}

	private String getAllOptions() {
		String s = "<br/>1)About RMKCET";
		s = s + "<br/>2)About Staffs";
		s = s + "<br/>3)About Placements";
		s = s + "<br/>4)Upcoming  programs";
		s = s + "<br/>5)Scheduled leave";
		s = s + "<br/>6)Updated marks";
		return s;
	}

	private String getAllOptionsWithAnythingElse() {
		String s = "<br/><br/>For more details press 0";
		//s = s + getAllOptions();
		return s;
	}

	private Map<String, String> getBranches() {
		Map<String, String> map = new HashMap<>();
		map.put("2.1", "CSE");
		map.put("2.2", "ECE");
		map.put("2.3", "EEE");
		map.put("2.4", "MECH");
		return map;
	}

}
