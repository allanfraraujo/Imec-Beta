
package com.imec.v2.model;

import com.imec.v2.controller.RegrasIncidente;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Allan F. Araujo
 */
public class IncidenteDAO {
    Incidente incidente = new Incidente();
    Connection conn = null;
    Statement stm = null;
    private String usernameUsr = "";
    private String passwordUsr = "";
    private final String classForName = "com.mysql.jdbc.Driver";
    private final String urlSql = "jdbc:mysql://172.18.54.8:3306/imec_v2";
    
    public Connection conectar() {
        try{
            Class.forName(classForName);
            conn = DriverManager.getConnection(urlSql, usernameUsr, passwordUsr);
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
    
    public void criarTabelaIncidente(){
        conectar();
        String url = "CREATE TABLE IF NOT EXISTS `incidente` (\n" +
                        "  `idIncidente` int(25) DEFAULT NULL,\n" +
                        "  `numeroIncidente` int(25) DEFAULT NULL,\n" +
                        "  `descricaoIncidente` varchar(999) DEFAULT NULL,\n" +
                        "  `dataIncidente` varchar(50) DEFAULT NULL,\n" +
                        "  `operadorIncidente` varchar(50) DEFAULT NULL,\n" +
                        "  `tipoEquipamentoIncidente` varchar(50) DEFAULT NULL,\n" +
                        "  `itemIncidente` varchar(900) DEFAULT NULL,\n" +
                        "  `plantaIncidente` varchar(50) DEFAULT NULL,\n" +
                        "  `modificadorIncidente` int(30) DEFAULT NULL,\n" +
                        "  `statusIncidente` varchar(10) DEFAULT NULL,\n" +
                        "  `problemaIncidente` varchar(999) DEFAULT NULL,\n" +
                        "  `solucaoProblemaIncidente` varchar(999) DEFAULT NULL,\n" +
                        "  `impactoCausadoIncidente` varchar(800) DEFAULT NULL,\n" +
                        "  `responsavelIncidente` varchar(99) DEFAULT NULL,\n" +
                        "  `criticidadeIncidente` varchar(99) DEFAULT NULL,\n" +
                        "  `dataDiasIncidente` varchar(45) DEFAULT NULL,\n" +
                        "  `dataHorasIncidente` varchar(45) DEFAULT NULL,\n" +
                        "  `grupoResponsavelIncidente` varchar(50) DEFAULT NULL,\n" +
                        "  `numeroChamadoOperadoraIncidente` varchar(25) DEFAULT NULL,\n" +
                        "  `dataAnoIncidente` varchar(45) DEFAULT NULL\n" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
        try{
            stm = conn.createStatement();
            stm.execute(url);
            System.out.println("Criou a tabela Incidente.");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void adicionarTabelaIncidente(){
        conectar();
        String url = "INSERT INTO `incidente` (`idIncidente`, `numeroIncidente`,`descricaoIncidente`,`dataIncidente`,`operadorIncidente`,`tipoEquipamentoIncidente`,`itemIncidente`,`plantaIncidente`,`modificadorIncidente`,`statusIncidente`,`problemaIncidente`,`solucaoProblemaIncidente`,`impactoCausadoIncidente`,`responsavelIncidente`,`criticidadeIncidente`,`dataDiasIncidente`,`dataHorasIncidente`,`grupoResponsavelIncidente`,`numeroChamadoOperadoraIncidente`,`dataAnoIncidente`) VALUES ("+incidente.getIdIncidente()+",'"+incidente.getNumeroIncidente()+"','"+incidente.getDescricaoIncidente()+"','"+incidente.getDataIncidente()+"','"+incidente.getOperadorIncidente()+"','????','"+incidente.getItemIncidente()+"','????','"+incidente.getModificadorIncidente()+"','"+incidente.getStatusIncidente()+"','Em analise.','Em analise.','Em analise.','"+incidente.getResponsavelIncidente()+"','Em analise.','"+incidente.getDataDiasIncidente()+"','"+incidente.getDataHorasIncidente()+"','"+incidente.getGrupoResponsavelIncidente()+"','"+incidente.getNumeroChamadoOperadoraIncidente()+"','"+incidente.getDataAnoIncidente()+"')";
        try{
            stm = conn.createStatement();
            stm.execute(url);
            System.out.println("Adicionou tabela ao banco Incidente.");
            
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
        
    public void btnSalvarIncidente(JComboBox comboAnoIncidente, JTextField txtNumeroIncidente, JTextField txtOperadorIncidente, JTextField txtDataDiasIncidente, JTextField txtDataHorasIncidente, JComboBox comboDisponivel, JComboBox comboListaItemIncidente, JTextArea txtDescricaoIncidente, JComboBox comboGrupoResponsavelIncidente, JTextField txtResponsavelIncidente, JTextField txtChamadoOperadoraIncidente) {
        RegrasIncidente regInc = new RegrasIncidente();
        String urlValidacao = "SELECT * FROM incidente WHERE numeroIncidente = "+txtNumeroIncidente.getText()+" LIMIT 1;";
        int provisorio = 0;
        try {
            conectar();
            stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(urlValidacao);
            while(rs.next()) {
                provisorio = rs.getInt("numeroIncidente");
            }
            if(provisorio == Integer.parseInt(txtNumeroIncidente.getText())) {
                JOptionPane.showMessageDialog(null, "Número de incidente já existe.");  
            } else {
                System.out.println("Não.");
                incidente.setIdIncidente(1);
                incidente.setDataAnoIncidente((String) comboAnoIncidente.getSelectedItem());
                incidente.setNumeroIncidente(Integer.parseInt(txtNumeroIncidente.getText()));
                incidente.setOperadorIncidente(txtOperadorIncidente.getText());
                incidente.setDataDiasIncidente(txtDataDiasIncidente.getText());
                incidente.setDataHorasIncidente(txtDataHorasIncidente.getText());
                incidente.setDataIncidente(txtDataDiasIncidente.getText()+" - "+txtDataHorasIncidente.getText());
                incidente.setStatusIncidente((String) comboDisponivel.getSelectedItem());
                incidente.setModificadorIncidente(1);
                incidente.setItemIncidente((String) comboListaItemIncidente.getSelectedItem());
                incidente.setDescricaoIncidente(txtDescricaoIncidente.getText());
                incidente.setGrupoResponsavelIncidente((String) comboGrupoResponsavelIncidente.getSelectedItem());
                incidente.setResponsavelIncidente(txtResponsavelIncidente.getText());
                incidente.setNumeroChamadoOperadoraIncidente(txtChamadoOperadoraIncidente.getText());

                criarTabelaIncidente();
                adicionarTabelaIncidente();

                JOptionPane.showMessageDialog(null, "Incidente aberto com sucesso, em instantes chegará um e-mail na caixa de entrada do IOC");
                regInc.limparCampos(txtNumeroIncidente, txtOperadorIncidente, txtDataDiasIncidente, txtDataHorasIncidente, txtDescricaoIncidente, txtResponsavelIncidente, txtChamadoOperadoraIncidente);
            } 
        } catch(HeadlessException | NumberFormatException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao adicionar o incidente.");
        }
    }
    
    public void btnAtualizarIncidente(JComboBox comboAnoIncidente, JTextField txtNumeroIncidente, JTextField txtOperadorIncidente, JTextField txtDataDiasIncidente, JTextField txtDataHorasIncidente, JComboBox comboDisponivel, JComboBox comboListaItemIncidente, JTextArea txtDescricaoIncidente, JComboBox comboGrupoResponsavelIncidente, JTextField txtResponsavelIncidente, JTextField txtChamadoOperadoraIncidente) throws SQLException {
        RegrasIncidente regInc = new RegrasIncidente();
        String urlValidacao = "SELECT * FROM incidente WHERE numeroIncidente = "+txtNumeroIncidente.getText()+";";
        
        int provisorioId = 0;
        try {
            conectar();
            stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(urlValidacao);
            while(rs.next()) {
                provisorioId = rs.getInt("idIncidente");
            }
            provisorioId = provisorioId+1;
            
            incidente.setIdIncidente(provisorioId);
            incidente.setDataAnoIncidente((String) comboAnoIncidente.getSelectedItem());
            incidente.setNumeroIncidente(Integer.parseInt(txtNumeroIncidente.getText()));
            incidente.setOperadorIncidente(txtOperadorIncidente.getText());
            incidente.setDataDiasIncidente(txtDataDiasIncidente.getText());
            incidente.setDataHorasIncidente(txtDataHorasIncidente.getText());
            incidente.setDataIncidente(txtDataDiasIncidente.getText()+" - "+txtDataHorasIncidente.getText());
            incidente.setStatusIncidente((String) comboDisponivel.getSelectedItem());
            incidente.setModificadorIncidente(2);
            incidente.setItemIncidente((String) comboListaItemIncidente.getSelectedItem());
            incidente.setDescricaoIncidente(txtDescricaoIncidente.getText());
            incidente.setGrupoResponsavelIncidente((String) comboGrupoResponsavelIncidente.getSelectedItem());
            incidente.setResponsavelIncidente(txtResponsavelIncidente.getText());
            incidente.setNumeroChamadoOperadoraIncidente(txtChamadoOperadoraIncidente.getText());

            criarTabelaIncidente();
            adicionarTabelaIncidente();

            JOptionPane.showMessageDialog(null, "Incidente atualizado com sucesso.");
            regInc.limparCampos(txtNumeroIncidente, txtOperadorIncidente, txtDataDiasIncidente, txtDataHorasIncidente, txtDescricaoIncidente, txtResponsavelIncidente, txtChamadoOperadoraIncidente);
            
        } catch(HeadlessException | NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao atualizar o incidente.");
        }
    }
    
    public void btnEncerrarIncidente(JComboBox comboAnoIncidente, JTextField txtNumeroIncidente, JTextField txtOperadorIncidente, JTextField txtDataDiasIncidente, JTextField txtDataHorasIncidente, JComboBox comboDisponivel, JComboBox comboListaItemIncidente, JTextArea txtDescricaoIncidente, JComboBox comboGrupoResponsavelIncidente, JTextField txtResponsavelIncidente, JTextField txtChamadoOperadoraIncidente) throws SQLException {
        RegrasIncidente regInc = new RegrasIncidente();
        
        String urlValidacao = "UPDATE `imec_v2`.`incidente` SET modificadorIncidente = 3 WHERE numeroIncidente = "+txtNumeroIncidente.getText()+";";
        try {
            conectar(); 
            stm = conn.createStatement();
            
            incidente.setIdIncidente(9999);
            incidente.setDataAnoIncidente((String) comboAnoIncidente.getSelectedItem());
            incidente.setNumeroIncidente(Integer.parseInt(txtNumeroIncidente.getText()));
            incidente.setOperadorIncidente(txtOperadorIncidente.getText());
            incidente.setDataDiasIncidente(txtDataDiasIncidente.getText());
            incidente.setDataHorasIncidente(txtDataHorasIncidente.getText());
            incidente.setDataIncidente(txtDataDiasIncidente.getText()+" - "+txtDataHorasIncidente.getText());
            incidente.setStatusIncidente((String) comboDisponivel.getSelectedItem());
            incidente.setModificadorIncidente(3);
            incidente.setItemIncidente((String) comboListaItemIncidente.getSelectedItem());
            incidente.setDescricaoIncidente(txtDescricaoIncidente.getText());
            incidente.setGrupoResponsavelIncidente((String) comboGrupoResponsavelIncidente.getSelectedItem());
            incidente.setResponsavelIncidente(txtResponsavelIncidente.getText());
            incidente.setNumeroChamadoOperadoraIncidente(txtChamadoOperadoraIncidente.getText());

            criarTabelaIncidente();
            adicionarTabelaIncidente();
            
            stm.execute(urlValidacao);

            JOptionPane.showMessageDialog(null, "Incidente encerrado com sucesso, adicione causa e impacto ao incidente.");
            regInc.limparCampos(txtNumeroIncidente, txtOperadorIncidente, txtDataDiasIncidente, txtDataHorasIncidente, txtDescricaoIncidente, txtResponsavelIncidente, txtChamadoOperadoraIncidente);
            
        } catch(HeadlessException | NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao encerrar o incidente.");
        }
    }
    
    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
}
