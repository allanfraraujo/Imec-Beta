/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imec.v2.controller;

import com.imec.v2.model.IncidenteDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author aaraujo
 */
public class RegrasIncidente {
    IncidenteDAO dao = new IncidenteDAO();
    private Statement stm;
    
    public void consultarPerdaFoco(JTextField txtNumeroIncidente, JTextArea areaHistoricoIncidente, JLabel lbNumeroIncidenteEstatistica, JLabel lblInicioEstatistica, JLabel lblFimEstatistica, JLabel lblIncidenteNumeroHistorico, JLabel lblTotalIncidenteItem, JTextArea txtEstatisticaHistoricoIncidente, JLabel lblEquipamentoRegistro) {
        String url = "SELECT * FROM incidente WHERE numeroIncidente = "+txtNumeroIncidente.getText()+";";
        try {
            limparCamposHistorico(areaHistoricoIncidente);
            dao.conectar();
            stm = dao.getConn().createStatement();
            ResultSet rs = stm.executeQuery(url);
            while(rs.next()) {
                areaHistoricoIncidente.append("Nº do Incidente: "+rs.getString("numeroIncidente")+" - "+"Operador: "+rs.getString("operadorIncidente")+"\n");
                areaHistoricoIncidente.append("Hostname: "+rs.getString("itemIncidente")+"\n");
                areaHistoricoIncidente.append("Horário: "+rs.getString("dataIncidente")+"\n");
                areaHistoricoIncidente.append("Descrição: "+rs.getString("descricaoIncidente")+"\n");
                areaHistoricoIncidente.append("Nº do Chamado Operadora: "+rs.getString("numeroChamadoOperadoraIncidente")+"\n");
                areaHistoricoIncidente.append("Equipamento Disponível: "+rs.getString("statusIncidente")+"\n"+"\n");
            }
            calculoEstatisticaTempoIndisponivel(lbNumeroIncidenteEstatistica, txtNumeroIncidente, lblInicioEstatistica, lblFimEstatistica);
            calculoEstatisticaHistoricoIndisponibilidade(lbNumeroIncidenteEstatistica, lblIncidenteNumeroHistorico, lblTotalIncidenteItem, txtEstatisticaHistoricoIncidente, lblEquipamentoRegistro);
        } catch(SQLException e) {
            
        }
    }
    public void contagemDeIncidentesAbertos(JLabel lblAbertosIncidente) {
        String urlAbertosIncidente = "SELECT * FROM incidente WHERE modificadorIncidente = 1 && idIncidente = 1 && dataAnoIncidente = '2017';";
        int contagemDeIncidente = 0;
        try {
            dao.conectar();
            stm = dao.getConn().createStatement();
            ResultSet rs = stm.executeQuery(urlAbertosIncidente);
            while(rs.next()) {
                contagemDeIncidente++;
                lblAbertosIncidente.setText(""+contagemDeIncidente);
                
            }
        } catch (SQLException e) {
            System.out.println("Erro ao realizar a contagem de Incidentes Abertos.");
        }
    }
    public void contagemDeIncidentesWAN(JLabel lblWanIncidente) {
        String urlWanIncidente = "SELECT * FROM incidente WHERE tipoEquipamentoIncidente = 'MPLS' && idIncidente = 1;";
        int contagemDeIncidente = 0;
        try {
            dao.conectar();
            stm = dao.getConn().createStatement();
            ResultSet rs = stm.executeQuery(urlWanIncidente);
            while(rs.next()) {
                contagemDeIncidente++;
                lblWanIncidente.setText(""+contagemDeIncidente);
                
            }
        } catch (SQLException e) {
            System.out.println("Erro ao realizar a contagem de Incidentes WAN.");
        }
    }
    public void contagemDosUltimosIncidentes(JLabel lblUltimoIncidente) {
        String urlUltimoIncidente = "SELECT * FROM incidente WHERE idIncidente = 1;";
        try {
            dao.conectar();
            stm = dao.getConn().createStatement();
            ResultSet rs = stm.executeQuery(urlUltimoIncidente);
            while(rs.next()) {
                lblUltimoIncidente.setText(rs.getString("numeroIncidente"));                
            }
        } catch (SQLException e) {
            System.out.println("Erro ao realizar a contagem dos Ultimos Incidentes.");
        }
    }
    public void contagemDoTotalIncidentes(JLabel lblTotalIncidente) {
        String urlTotalIncidente = "SELECT * FROM incidente WHERE idIncidente = 1;";
        int contagemDeIncidente = 0;
        try {
            dao.conectar();
            stm = dao.getConn().createStatement();
            ResultSet rs = stm.executeQuery(urlTotalIncidente);
            while(rs.next()) {
                contagemDeIncidente++;
                lblTotalIncidente.setText(""+contagemDeIncidente);
                
            }
        } catch (SQLException e) {
            System.out.println("Erro ao realizar a contagem de Total de Incidentes.");
        }
    }
    public void consultarHistoricoIncidentesAbertos(JTextArea areaAbertosIncidente) {
        String urlAbertosIncidente = "SELECT * FROM incidente WHERE modificadorIncidente = 1 && idIncidente = 1 && dataAnoIncidente = '2017';";
        try {
            areaAbertosIncidente.setText(null);
            dao.conectar();
            stm = dao.getConn().createStatement();
            ResultSet rs = stm.executeQuery(urlAbertosIncidente);
            while(rs.next()) {
                areaAbertosIncidente.append("Nº do Incidente: "+rs.getInt("numeroIncidente")+"\n");
                areaAbertosIncidente.append("Hostname: "+rs.getString("itemIncidente")+"\n");
                areaAbertosIncidente.append("Equipamento Disponível: "+rs.getString("statusIncidente")+"\n"+"\n");
                
            }
        } catch (SQLException e) {
            System.out.println("Erro ao completar o historico de incidentes abertos.");
        }
    }
    public void consultarHistoricoIncidentesFechados(JTextArea areaEncerradosIncidente) {
        String url = "SELECT * FROM INCIDENTE WHERE idIncidente = 9999 ORDER BY numeroIncidente DESC LIMIT 10";
        try {
            areaEncerradosIncidente.setText(null);
            dao.conectar();
            stm = dao.getConn().createStatement();
            ResultSet rs = stm.executeQuery(url);
            while(rs.next()) {
                areaEncerradosIncidente.append("Nº do Incidente: "+rs.getInt("numeroIncidente")+"\n");
                areaEncerradosIncidente.append("Hostname: "+rs.getString("itemIncidente")+" - ");
                areaEncerradosIncidente.append("Para mais informações, consulte o incidente separadamente."+"\n"+"\n");
                
            }
        } catch (SQLException e) {
            System.out.println("Erro ao completar o historico de incidentes fechados."+e);
        }
    }
    public void calculoEstatisticaTempoIndisponivel(JLabel lbNumeroIncidenteEstatistica, JTextField txtNumeroIncidente, JLabel lblInicioEstatistica, JLabel lblFimEstatistica) {
        lbNumeroIncidenteEstatistica.setText(txtNumeroIncidente.getText());
        Statement stm1;
        String urlDataInicio = "SELECT * FROM INCIDENTE WHERE numeroIncidente = "+lbNumeroIncidenteEstatistica.getText()+" && idIncidente = 1";
        String urlDataFim = "SELECT * FROM INCIDENTE WHERE numeroIncidente = "+lbNumeroIncidenteEstatistica.getText()+" && statusIncidente = 'Sim' LIMIT 1";
        try {
            dao.conectar();
            stm = dao.getConn().createStatement();
            stm1 = dao.getConn().createStatement();
            ResultSet rsInicio = stm.executeQuery(urlDataInicio);
            ResultSet rsFim = stm1.executeQuery(urlDataFim);
            while(rsInicio.next() && rsFim.next()) {
                lblInicioEstatistica.setText(rsInicio.getString("dataIncidente"));
                lblFimEstatistica.setText(rsFim.getString("dataIncidente"));
            }
            
        } catch(Exception e) {
            
        }
    }
    public void calculoEstatisticaHistoricoIndisponibilidade(JLabel lbNumeroIncidenteEstatistica, JLabel lblIncidenteNumeroHistorico, JLabel lblTotalIncidenteItem, JTextArea txtEstatisticaHistoricoIncidente, JLabel lblEquipamentoRegistro) {
        Statement stm1;
        String url = "SELECT * FROM INCIDENTE WHERE numeroIncidente = "+lbNumeroIncidenteEstatistica.getText()+" LIMIT 1";
        
        try {
            dao.conectar();
            stm = dao.getConn().createStatement();
            stm1 = dao.getConn().createStatement();
            ResultSet rs = stm.executeQuery(url);
            
            while(rs.next()) {
                lblEquipamentoRegistro.setText(rs.getString("itemIncidente"));
            }
            
            String urlItem = "SELECT * FROM INCIDENTE WHERE itemIncidente = '"+lblEquipamentoRegistro.getText()+"' && idIncidente = 1 ORDER BY numeroIncidente DESC";
            ResultSet rsItem = stm1.executeQuery(urlItem);
            int contagemIncidente = 0;
            while(rsItem.next()) {
                txtEstatisticaHistoricoIncidente.append("Nº Incidente: "+rsItem.getString("numeroIncidente")+"\n");
                txtEstatisticaHistoricoIncidente.append("Data de Abertura: "+rsItem.getString("dataIncidente")+"\n");
                txtEstatisticaHistoricoIncidente.append("Solução do Incidente: "+rsItem.getString("solucaoProblemaIncidente")+"\n");
                txtEstatisticaHistoricoIncidente.append("Impacto: "+rsItem.getString("impactoCausadoIncidente")+"\n"+"\n");
                contagemIncidente++;
            }
            lblTotalIncidenteItem.setText(""+contagemIncidente);
        } catch(SQLException e) {
            System.out.println("Não foi possível completar a aba Estatistica.");
        }
    }
    public void limparCampos(JTextField txtNumeroIncidente, JTextField txtOperadorIncidente, JTextField txtDataDiasIncidente, JTextField txtDataHorasIncidente, JTextArea txtDescricaoIncidente, JTextField txtResponsavelIncidente, JTextField txtChamadoOperadoraIncidente) {
        try {
            txtNumeroIncidente.setText(null);
            txtOperadorIncidente.setText(null);
            txtDataDiasIncidente.setText(null);
            txtDataHorasIncidente.setText(null);
            txtDescricaoIncidente.setText(null);
            txtResponsavelIncidente.setText(null);
            txtChamadoOperadoraIncidente.setText(null);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void limparCamposHistorico(JTextArea areaHistoricoIncidente) {
       areaHistoricoIncidente.setText(null);
    }    
}
