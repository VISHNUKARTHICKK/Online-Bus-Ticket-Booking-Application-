package com.bbs.gobus.helper;

import com.bbs.gobus.entity.Booking;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

import org.springframework.stereotype.Service;



import java.awt.Color;
import java.io.ByteArrayOutputStream;


import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Service
public class TicketService {

    public byte[] generateTicketPdf(Booking booking) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Document document = new Document(PageSize.A4, 40, 40, 50, 50);
        PdfWriter.getInstance(document, out);
        document.open();

        // Title / Header
        Paragraph title = new Paragraph("GoBus eTICKET",
                new Font(Font.HELVETICA, 18, Font.BOLD, new Color(0, 0, 255)));
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(new Paragraph(" ")); // spacing

        // Journey details
        PdfPTable journeyTable = new PdfPTable(2);
        journeyTable.setWidthPercentage(100);
        journeyTable.addCell(getCell("From: " + booking.getBoardingPoint(), PdfPCell.ALIGN_LEFT));
        journeyTable.addCell(getCell("To: " + booking.getDropPoint(), PdfPCell.ALIGN_RIGHT));
        journeyTable.addCell(getCell("Date: " + booking.getDate().formatted(DateTimeFormatter.ofPattern("dd MMM yyyy")), PdfPCell.ALIGN_LEFT));
        journeyTable.addCell(getCell("Bus: " + booking.getBusName(), PdfPCell.ALIGN_RIGHT));
        
        document.add(journeyTable);

        document.add(new Paragraph(" "));

        // Passenger details
        Paragraph pHeader = new Paragraph("Passenger Details", new Font(Font.HELVETICA, 14, Font.BOLD));
        document.add(pHeader);

        PdfPTable passengerTable = new PdfPTable(3);
        passengerTable.setWidthPercentage(100);
        passengerTable.addCell("Name");
        passengerTable.addCell("Age");
        passengerTable.addCell("Gender");

        booking.getPassengerList().forEach(passenger -> {
            passengerTable.addCell(passenger.getName());
            passengerTable.addCell(String.valueOf(passenger.getAge()));
            passengerTable.addCell(passenger.getGender());
        });

        document.add(passengerTable);

        document.add(new Paragraph(" "));

        // Fare info
        // Paragraph fare = new Paragraph("Total Fare: Rs. " + booking.getPrice(),
        //         new Font(Font.HELVETICA, 12, Font.BOLD, Color.BLACK));
        // fare.setAlignment(Element.ALIGN_RIGHT);
        // document.add(fare);

        document.close();
        return out.toByteArray();
    }

    private PdfPCell getCell(String text, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setHorizontalAlignment(alignment);
        return cell;
    }
}
