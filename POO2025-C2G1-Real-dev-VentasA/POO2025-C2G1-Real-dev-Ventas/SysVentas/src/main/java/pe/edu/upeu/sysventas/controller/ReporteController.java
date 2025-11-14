package pe.edu.upeu.sysventas.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.StackPane;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.sysventas.service.IVentaService;

import java.time.format.DateTimeFormatter;

@Controller

public class ReporteController {

    @FXML
    DatePicker txtFechaI,FechaF;

    @FXML
    StackPane paneRepo;

    @Autowired
    IVentaService vDao;
    JasperPrint jasperPrint;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");



    @FXML
    public void reportar(){

        if(txtFechaI.getValue().isAfter(FechaF.getValue())&& txtFechaI.getValue()==null || txtFechaF.getValue()!=null){

            System.out.printf("Fecha Incorrecta");
            return;

        }

        String fechaI = txtFechaI.getValue().format(formatter);
        String fechaF = txtFechaF.getValue().format(formatter);

        try {
            jasperPrint = vDao.runReportVenta(fechaI,fechaF );

            JRViewer vf=new JRViewer(jasperPrint);
            paneRepo.getChildren().clear();
            paneRepo.getChildren().add(vf);
            StackPane.setAlignment(vf, Pos.CENTER);
        }catch ()

    }



}
