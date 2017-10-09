/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imec.v2.controller;

import com.imec.v2.model.EquipamentoDAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author aaraujo
 */
public class RegrasEquipamento {
    Connection conn = null;
    Statement stm = null;
    EquipamentoDAO dao = new EquipamentoDAO();
    
    public void completarItem(JTextField txtConsultaItemIncidente, JComboBox comboListaItemIncidente) {
        String url = "select * from equipamentos where equipamento like '%"+txtConsultaItemIncidente.getText()+"%' ORDER BY equipamento;";        
        try {
            comboListaItemIncidente.removeAllItems();
            dao.conectar();
            stm = dao.conn.createStatement();
            ResultSet rs = stm.executeQuery(url);
            while(rs.next()) {
                comboListaItemIncidente.addItem(rs.getString("equipamento"));
            }
            System.out.println("Lista de equipamentos completa.");
        } catch(SQLException e) {
            System.out.println("Não foi possível completar a lista de equipamentos.");
        }
    }
    public void completarTabela(JComboBox cmbConsultaEquipamento, JTextField txtPesquisaEquipamento, JTable tabelaEquipamento) {
        String url = "SELECT * FROM `software_pesquisa`.`equipamentos` WHERE "+cmbConsultaEquipamento.getSelectedItem()+" LIKE '%"+txtPesquisaEquipamento.getText()+"%' ORDER BY equipamento;";        
        int a = 0;
        try {
            limparTabela(tabelaEquipamento);
            dao.conectar();
            stm = dao.conn.createStatement();
            ResultSet rs = stm.executeQuery(url);            
            while(rs.next()) {
                tabelaEquipamento.setValueAt(rs.getString("ip"), a, 0);
                tabelaEquipamento.setValueAt(rs.getString("equipamento"), a, 1);
                tabelaEquipamento.setValueAt(rs.getString("nome_n1"), a, 2);
                tabelaEquipamento.setValueAt(rs.getString("nome_n2"), a, 3);
                tabelaEquipamento.setValueAt(rs.getString("nome_n3"), a, 4);
                a++;
            }
            rs.close();
            dao.conn.close();
            System.out.println("Lista de equipamentos completa.");
        } catch(SQLException e) {
            System.out.println("Não foi possível completar a lista de equipamentos."+e);
        }
    }
    
    public void completarDetalhesEquipamentos(JTable tabelaEquipamento, JTextField txtDescricao, JTextField txtImpacto, JTextField txtHostname, JTextField txtIp, JTextField txtAreaAfetada, JTextField txtInfraAfetada, JTextField txtAplicacaoAfetada, JTextField txtDesignacao, JTextArea AreaEmail, JTextArea AreaObservacao, JTextField txtNivel1Nome, JTextField txtNivel1Ramal, JTextField txtNivel1Telefone, JTextField txtNivel2Nome, JTextField txtNivel2Ramal, JTextField txtNivel2Telefone,JTextField txtNivel3Nome, JTextField txtNivel3Ramal, JTextField txtNivel3Telefone, JTextField txtNivel1NomeSoftware, JTextField txtNivel1RamalSoftware, JTextField txtNivel1TelefoneSoftware, JTextField txtNivel2NomeSoftware, JTextField txtNivel2RamalSoftware, JTextField txtNivel2TelefoneSoftware, JTextField txtNivel3NomeSoftware, JTextField txtNivel3RamalSoftware, JTextField txtNivel3TelefoneSoftware) {
        System.out.println("Clique tabel: "+tabelaEquipamento.getValueAt(tabelaEquipamento.getSelectedRow(), 0));
        String url = "SELECT * FROM EQUIPAMENTOS WHERE IP = '"+tabelaEquipamento.getValueAt(tabelaEquipamento.getSelectedRow(), 0)+"';";
        try {
            dao.conectar();
            stm = dao.conn.createStatement();
            ResultSet rs = stm.executeQuery(url);
            while(rs.next()) {
                txtDescricao.setText(rs.getString("descricao"));
                txtImpacto.setText(rs.getString("descricao_impacto"));
                txtHostname.setText(rs.getString("equipamento"));
                txtIp.setText(rs.getString("ip"));
                txtAreaAfetada.setText(rs.getString("area_afetada"));
                txtInfraAfetada.setText(rs.getString("infra_afetada"));
                txtAplicacaoAfetada.setText(rs.getString("aplicacao_afetada"));
                txtDesignacao.setText(rs.getString("designacao"));
                AreaEmail.setText(rs.getString("email"));
                AreaObservacao.setText(rs.getString("observacao"));
                txtNivel1Nome.setText(rs.getString("nome_n1"));
                txtNivel1Ramal.setText(rs.getString("ramal_n1"));
                txtNivel1Telefone.setText(rs.getString("telefone_n1"));
                txtNivel2Nome.setText(rs.getString("nome_n2"));
                txtNivel2Ramal.setText(rs.getString("ramal_n2"));
                txtNivel2Telefone.setText(rs.getString("telefone_n2"));
                txtNivel3Nome.setText(rs.getString("nome_n3"));
                txtNivel3Ramal.setText(rs.getString("ramal_n3"));
                txtNivel3Telefone.setText(rs.getString("telefone_n3"));
                txtNivel1NomeSoftware.setText(rs.getString("nome_n1_software"));
                txtNivel1RamalSoftware.setText(rs.getString("ramal_n1_software"));
                txtNivel1TelefoneSoftware.setText(rs.getString("telefone_n1_software"));
                txtNivel2NomeSoftware.setText(rs.getString("nome_n2_software"));
                txtNivel2RamalSoftware.setText(rs.getString("ramal_n2_software"));
                txtNivel2TelefoneSoftware.setText(rs.getString("telefone_n2_software"));
                txtNivel3NomeSoftware.setText(rs.getString("nome_n3_software"));
                txtNivel3RamalSoftware.setText(rs.getString("ramal_n3_software"));
                txtNivel3TelefoneSoftware.setText(rs.getString("telefone_n3_software"));                              
            }
        } catch(SQLException e) {
            
        }
    }
    
    public void realizarPing(JTextField txtIp) {
        String comando = "cmd /c start ping -t ";
        try {
            Runtime.getRuntime().exec(comando+txtIp.getText());
        } catch(Exception e) {
            System.out.println("Teste ping mal-suscedido.");
        }
    }
    
    public void realizarTelnet(JTextField txtIpTesteTelnet, JTextField txtPortaTesteTelnet) {
        String comando = "cmd.exe /K start telnet "+txtIpTesteTelnet.getText()+" "+txtPortaTesteTelnet.getText();
        try {
            Runtime.getRuntime().exec(comando);
            System.out.println("Telnet: "+txtIpTesteTelnet.getText()+" "+txtPortaTesteTelnet.getText());
        } catch(Exception e) {
            System.out.println("Teste telnet mal-suscedido.");
        }        
    }
    
    public void limparTabela(JTable tabelaEquipamento) {
        String url = "select * from equipamentos;";
        try {
            dao.conectar();
            stm = dao.conn.createStatement();
            ResultSet rs = stm.executeQuery(url);
            int a = 0;
            while(rs.next()) {
                tabelaEquipamento.setValueAt(null, a, 0);
                tabelaEquipamento.setValueAt(null, a, 1);
                tabelaEquipamento.setValueAt(null, a, 2);
                tabelaEquipamento.setValueAt(null, a, 3);
                tabelaEquipamento.setValueAt(null, a, 4);
                a++;
            }
        } catch(SQLException e) {
            
        }
    }
}
