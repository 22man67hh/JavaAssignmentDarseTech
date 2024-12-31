/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import java.awt.print.Book;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Manish
 */
@WebServlet(name = "books", urlPatterns = {"/books/*"})
public class books extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet books</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet books at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	response.setContentType("text/html");
    	List<servlet.Book> books=new ArrayList<>();
    	try {
    		Connection connection=conn.getConnection();
    		PreparedStatement ps=connection.prepareStatement("select * from booktable");
    		ResultSet rs=ps.executeQuery();
    		while(rs.next()) {
//    			books.addAll(new servlet.Book(rs.getString("name"),rs.getString("isbn")));
    			 String name = rs.getString("name");
    			    String isbn = rs.getString("isbn");
    			    System.out.println("Fetched Book: " + name + ", " + isbn); 
    		books.add(new servlet.Book(name, isbn) );
    		}
			
		} catch (SQLException  e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);	
			 response.getWriter().println("Database error: " + e.getMessage());
	            return;
		}   catch (ClassNotFoundException e) {
			response.getWriter().print("Class not found");
		}
    	 PrintWriter out = response.getWriter();
    	 
    	    for (servlet.Book book : books) {
    	    	  out.println("<tr>");
    	          out.println("<td>" + book.getName() + "</td>");
    	          out.println("<td>" + book.getIsbn() + "</td>");
    	          out.println("<td><button class='delete-btn' data-isbn='" + book.getIsbn() + "'>Delete</button></td>");
					
	 out.println("<td><button class='update-btn' data-isbn='" + book.getIsbn() +
					 "'>Update</button></td>");
					 
    	          out.println("</tr>");    	    }
         
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String name=request.getParameter("name");
        String isbn=request.getParameter("isbn");
        PrintWriter out=response.getWriter();
        if(name==null || isbn==null){
           out.println("Cannot Be Empty");
           return;}
          
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection =DriverManager.getConnection("jdbc:mysql://localhost:3306/book","root","");
            PreparedStatement ps=connection.prepareStatement("insert into booktable(isbn,name) VALUES(?,?)");
            ps.setString(1, isbn);
            ps.setString(2, name);
            ps.executeUpdate();
         
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Error on database");
        }catch (ClassNotFoundException e) {
        	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("JDBC Driver not found: " + e.getMessage());
            e.printStackTrace();
		}
        response.setStatus(HttpServletResponse.SC_CREATED);
             response.getWriter().println("Book added successfully.");
        
    }
    

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.length() <= 1) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String isbn = pathInfo.substring(1);

        try (Connection connection = conn.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM booktable WHERE isbn = ?");
            ps.setString(1, isbn);
            int rowsAffected = ps.executeUpdate();

           
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String isbn = request.getPathInfo().substring(1);
        String name = request.getParameter("name"); 

        try {
        	Connection connection = conn.getConnection();
        
             PreparedStatement ps = connection.prepareStatement("UPDATE booktable SET name = ? WHERE isbn = ?");
             
            ps.setString(1, name);
            ps.setString(2, isbn);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
    
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
