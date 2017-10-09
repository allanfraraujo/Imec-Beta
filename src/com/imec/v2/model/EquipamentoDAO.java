/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imec.v2.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Allan F. Araujo
 */
public class EquipamentoDAO {
    public Connection conn = null;
    Statement stm = null;
    private String usernameEquip = "";
    private String passwordEquip = "";
    private final String classForName = "com.mysql.jdbc.Driver";
    private final String urlSql = "jdbc:mysql://172.18.54.8:3306/software_pesquisa;
    
    public Connection conectar() {
        try{
            Class.forName(classForName);
            conn = DriverManager.getConnection(urlSql, usernameEquip, passwordEquip);
            System.out.println("Conectou no banco de dados.");
        }catch(SQLException e){
            System.out.println("Não conectou no banco de dados." + e);
        }catch(ClassNotFoundException ex){
            System.out.println("Classe não encontrada." + ex);
        }
        return conn; 
    }
        
    public Connection desconectar() {
        try{
            if(stm!=null){
                stm.close();
            }
            System.out.println("Fechou o Statement.");
            if(conn!=null){
                conn.close();
            }  
            System.out.println("Fechou a conexão.");
        }catch(SQLException e){
            System.out.println("Não desconectou do banco de dados."+ e);
        }
        return conn;
    }
    
}
