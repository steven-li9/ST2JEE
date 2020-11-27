/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import middleware.auth;
import middleware.parser;
import model.EvalSheet;
import model.Intern;
import model.Mission;
import model.Teacher;
import model.VisitSheet;
import static utils.CONSTANT.*;
import utils.DataServices;
import utils.QuerryManager;
/**
 *
 * @author steve
 */
public class myInterns extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet myInterns</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet myInterns at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        auth.isConnected(request,response);
        int id = parser.getIdFromUrl(request, response);
        Intern intern = getInternFromId(request, id);
        if (intern == null){
            request.getRequestDispatcher(ERROR_PATH).forward(request, response);
        }
        request.setAttribute("intern",intern);
        request.getRequestDispatcher(INTERN_VIEW_PATH).forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        auth.isConnected(request,response);
        int id = Integer.parseInt(request.getParameter("id_student"));
        Intern intern = getInternFromId(request, id);
        if (intern == null){
            request.getRequestDispatcher(ERROR_PATH).forward(request, response);
        }
        UpdateInternFromRequest(intern,request);
        HttpSession session = request.getSession();
        Teacher User = (Teacher) session.getAttribute("User");
        DataServices ds = new DataServices(User.getUser(), User.getPwd());
        ds.selectQuery(QuerryManager.updateIntern(intern));
        request.setAttribute("intern",intern);
        request.getRequestDispatcher(INTERN_VIEW_PATH).forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private Intern getInternFromId(HttpServletRequest request, int id){
        HttpSession session = request.getSession();
        ArrayList<Intern> internList = (ArrayList<Intern>) session.getAttribute("internsList");
        for(Intern intern : internList){
            if (intern.getId() == id){
                return intern;
            }
        }
        return null;
    }

    private void UpdateInternFromRequest(Intern intern, HttpServletRequest request) {
        intern.setGroup(request.getParameter("GroupStudent"));
        intern.setLast_name(request.getParameter("LastNameStudent"));
//      in.setFirst_name(request.getParameter("FirstNameStudent
        intern.setAddress(request.getParameter("Adresse"));
//        in.setSkills(request.getParameter("Skills"));
//        in.setLinkedin(request.getParameter("Linkedin"));
//        in.setBirthday(stringToSqlDate(request.getParameter("Birthday"),"yyyy-mm-dd"));
        Mission mi = intern.getMission();
//        mi.setId(Integer.parseInt(request.getParameter("id_mission")));
//        mi.setYear(Integer.parseInt(request.getParameter("Year")));
        mi.setStartDate(stringToSqlDate(request.getParameter("Debut"), "yyyy-mm-dd"));
        mi.setEndDate(stringToSqlDate(request.getParameter("Fin"), "yyyy-mm-dd"));
//        mi.setReport_title(request.getParameter("Report_title"));
//        mi.setComment(request.getParameter("CommentMission"));
//        mi.setMeetingInfo(request.getParameter("MettingInfo"));
        mi.setSoutenance(null!=request.getParameter("soutenance" + intern.getId()));
        System.out.println("mi soutenance:" + mi.isSoutenance() + "request " + request.getParameter("soutenance"));
        EvalSheet es = mi.getEvalS();
        System.out.println("es :" + es);
//        es.setId(Integer.parseInt(request.getParameter("id_evalS")));
//        es.setComment(request.getParameter("CommentEvalSheet"));
        es.setGradeTech(Integer.parseInt(request.getParameter("NoteTech")));
        es.setGradeCom(Integer.parseInt(request.getParameter("NoteCom")));
//        es.setDone(Boolean.parseBoolean(request.getParameter("DoneEval")));
        VisitSheet vs = mi.getVisitS();
        System.out.println("vs :" + vs);
//        vs.setId(Integer.parseInt(request.getParameter("id_visitS")));
        vs.setPlanned(null!=request.getParameter("plannif"));
        vs.setDone(null!=request.getParameter("faite"));
        //intern.ShowConsole();
        System.out.println("vs planned :" + vs.isPlanned());
        System.out.println("vs done:" + vs.isDone());
    }
    
    public java.sql.Date stringToSqlDate(String date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return new java.sql.Date(dateFormat.parse(date).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(internController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Unformattable date");
        };
        return null;
    }
}
