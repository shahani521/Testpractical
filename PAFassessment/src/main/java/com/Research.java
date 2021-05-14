package com;

	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;

	public class Research {
		public Connection connect()
		{ 
		 Connection con = null; 
		 
		 try 
		 { 
		 Class.forName("com.mysql.cj.jdbc.Driver"); 
		 con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/researchmanage", 
		 "root", ""); 
		 //For testing
		 System.out.print("Successfully connected"); 
		 } 
		 catch(Exception e) 
		 { 
		 e.printStackTrace(); 
		 } 
		 
		 return con; 
			}



		public String insertResearch(String name, String email, String number, String projname,String details) {
			
			 String output = "";
			 
			 try {
			
			Connection con = connect();
			if (con == null) 
			{ 
			return "Error while connecting to the database"; 
			}
			
			// create a prepared statement
			String query = "insert into research1 (rID, name, email, number, projname, details)"
					 + " values (?,?, ?, ?, ?, ?)"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
			// binding values
			preparedStmt.setInt(1, 0); 
			preparedStmt.setString(2, name); 
			preparedStmt.setString(3, email); 
			preparedStmt.setString(4, number); 
			preparedStmt.setString(5, projname);
			preparedStmt.setString(5, details);
			
			
			System.out.println(projname);
			 
			 preparedStmt.execute(); 
			 con.close(); 
			 
			 String newResearch = readResearch();
			 output =  "{\"status\":\"success\", \"data\": \"" + 
					 newResearch + "\"}"; 
			 } 

			catch (Exception e) 
			 { 
				output = "{\"status\":\"error\", \"data\": \"Error while inserting the research.\"}";  
			 System.err.println(e.getMessage()); 
			 } 
			return output; 
			}


		public String readResearch()
		{ 
				 String output = ""; 
				try
				 { 
				 Connection con = connect(); 
				 if (con == null) 
				 { 
				 return "Error while connecting to the database for reading."; 
				 } 
				 // Prepare the html table to be displayed
				 output = "<table border='1'><tr><th>Research ID</th>" 
				 +"<th>Research Name</th>"
				 + "<th>Email</th>"
				 + "<th>Number</th>" 
				 + "<th>Project Name</th>" 
				 + "<th>Project Details</th>" 
				 + "<th>Update</th><th>Remove</th></tr>"; 
				 String query = "select * from research1"; 
				 java.sql.Statement stmt = con.createStatement(); 
				 ResultSet rs = stmt.executeQuery(query); 
				 // iterate through the rows in the result set
				 while (rs.next()) 
				 { 
				 String rID = Integer.toString(rs.getInt("rID")); 
				 String name = rs.getString("name"); 
				 String email = rs.getString("email"); 
				 String number = rs.getString("number"); 
				 String projname = rs.getString("projname"); 
				 String details = rs.getString("details");
				 // Add a row into the html table
				 output += "<tr><td><input id='hidResearchUpdate' name='hidResearchIDUpdate' type='hidden' value='" + rID + "'>"
						 + name + "</td>";
				 output += "<td>" + email + "</td>"; 
				 output += "<td>" + number + "</td>"; 
				 
				 output += "<td>" + projname + "</td>";
				 output += "<td>" + details + "</td>";
				 // buttons
				 output += "<td><input name='btnUpdate' " 
				 + " type='button' value='Update' class =' btnUpdate btn btn-secondary'data-itemid='" + rID + "'></td>"
				 + "<td><form method='post' action='Item.jsp'>"
				 + "<input name='btnRemove' " 
				 + " type='button' value='Remove' class='btnRemove btn btn-danger' data-itemid='" + rID + "'>"
				 + "<input name='hidItemIDDelete' type='hidden' " 
				 + " value='" + rID + "'>" + "</form></td></tr>"; 
				 } 
				 con.close(); 
				 // Complete the html table
				 output += "</table>"; 
				 } 
				catch (Exception e) 
				 { 
				 output = "Error while reading the researches."; 
				 System.err.println(e.getMessage()); 
				 } 
				return output; 
			}

		public String deleteResearch(String rID)
		{ 
		 String output = ""; 
		try
		 { 
		 Connection con = connect(); 
		 if (con == null) 
		 { 
		 return "Error while connecting to the database for deleting."; 
		 } 
		 // create a prepared statement
		 String query = "delete from research1 where rID=?"; 
		 PreparedStatement preparedStmt = con.prepareStatement(query); 
		 // binding values
		 preparedStmt.setInt(1, Integer.parseInt(rID)); 
		 
		 // execute the statement
		 preparedStmt.execute(); 
		 con.close(); 
		 String newResearch = readResearch();
		 output =  "{\"status\":\"success\", \"data\": \"" + 
				 newResearch + "\"}"; 
		 } 

		catch (Exception e) 
		 { 
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}";  
		 System.err.println(e.getMessage()); 
		 } 
		return output; 
			}

		public String updateResearch(int rID, String name, String email, String number, String projname, String details)
		//4
		{
		String output = "";
		try {
		Connection conn = connect();
		if (conn == null) {
		return "Error while connecting to the database for updating.";
		}

		// create a prepared statement
		String query = "UPDATE research1 SET rID=?,name=?,email=?,number=?,projname=?,details=?WHERE rID=?";
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		//binding values
		preparedStmt.setString(1, name);
		preparedStmt.setString(2, email);
		preparedStmt.setString(3, number);
		preparedStmt.setString(4, projname);
		preparedStmt.setString(5, details );
		preparedStmt.setInt(5,rID);
		//execute the statement
		preparedStmt.execute();
		conn.close();
		String newResearch = readResearch();
		 output =  "{\"status\":\"success\", \"data\": \"" + 
				 newResearch + "\"}"; 
		 } 

		catch (Exception e) 
		 { 
			output = "{\"status\":\"error\", \"data\": \"Error while Updating the Research.\"}";  
		
		System.err.println(e.getMessage());
		}
		return output;
		}


	}


