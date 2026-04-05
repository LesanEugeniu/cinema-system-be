package md.cineticket.cinemasystem.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import md.cineticket.cinemasystem.model.Booking;
import md.cineticket.cinemasystem.model.Screening;
import md.cineticket.cinemasystem.model.Seat;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TicketPdfGenerator {
    private final BookingService bookingService;

    private static final Color VIOLET_DARK   = new DeviceRgb(0x3D, 0x00, 0x6B);
    private static final Color VIOLET_MED    = new DeviceRgb(0x6A, 0x0D, 0xAD);
    private static final Color VIOLET_LIGHT  = new DeviceRgb(0xC8, 0xA8, 0xE9);
    private static final Color YELLOW_ACCENT = new DeviceRgb(0xFF, 0xD7, 0x00);
    private static final Color YELLOW_LIGHT  = new DeviceRgb(0xFF, 0xF3, 0x80);
    private static final Color WHITE         = new DeviceRgb(0xFF, 0xFF, 0xFF);
    private static final Color GRAY_SOFT     = new DeviceRgb(0xF5, 0xF0, 0xFF);

    @Transactional
    public void generateTicketsPdf(Booking booking, OutputStream outputStream) {
        try {
            Screening screening = booking.getScreening();

            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4);
            document.setMargins(0, 0, 0, 0);

            boolean firstPage = true;

            for (Seat seat : booking.getSeats()) {

                if (!firstPage) {
                    document.add(new AreaBreak());
                }
                firstPage = false;

                PdfPage page = pdf.getNumberOfPages() == 0
                        ? pdf.addNewPage()
                        : pdf.getLastPage();

                drawPageBackground(page, pdf);

                addHeader(document);
                addMovieInfo(document, screening);
                addDivider(document);
                addSeatInfo(document, seat, bookingId, screening);
                addQrCode(document, bookingId, seat);
                addFooter(document);
            }

            document.close();

        } catch (Exception e) {
            throw new RuntimeException("Eroare la generare PDF: " + e.getMessage(), e);
        }
    }

    private void drawPageBackground(PdfPage page, PdfDocument pdf) {
        PdfCanvas canvas = new PdfCanvas(page);
        Rectangle pageSize = page.getPageSize();

        // Fundal violet închis (header zone ~200pt)
        canvas.setFillColor(VIOLET_DARK)
                .rectangle(0, pageSize.getHeight() - 200, pageSize.getWidth(), 200)
                .fill();

        // Fundal alb-mov deschis (corp bilet)
        canvas.setFillColor(GRAY_SOFT)
                .rectangle(0, 0, pageSize.getWidth(), pageSize.getHeight() - 200)
                .fill();

        // Linie decorativă galbenă sub header
        canvas.setFillColor(YELLOW_ACCENT)
                .rectangle(0, pageSize.getHeight() - 205, pageSize.getWidth(), 5)
                .fill();

        canvas.setFillColor(WHITE);
        float y = pageSize.getHeight() - 205;
        for (int i = 0; i < 28; i++) {
            canvas.circle(20 + i * 20, y, 6).fill();
        }

        canvas.release();
    }

    private void addHeader(Document document) {
        Paragraph cinemaName = new Paragraph("🎬  CINEMA TICKETS")
                .setFontSize(28)
                .setBold()
                .setFontColor(YELLOW_ACCENT)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginTop(30)
                .setMarginBottom(4);
        document.add(cinemaName);

        Paragraph subtitle = new Paragraph("Enjoy the show!")
                .setFontSize(13)
                .setFontColor(VIOLET_LIGHT)
                .setItalic()
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(60);
        document.add(subtitle);
    }

    private void addMovieInfo(Document document, Screening screening) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMM yyyy  HH:mm");

        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2}))
                .setWidth(UnitValue.createPercentValue(85))
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setMarginTop(55)
                .setMarginBottom(10);

        addInfoRow(table, "Film",  screening.getMovie().getTitle());
        addInfoRow(table, "Data",  screening.getStartTime().format(fmt));
        addInfoRow(table, "Sala",  screening.getHall().getName());

        document.add(table);
    }

    private void addInfoRow(Table table, String label, String value) {
        Cell labelCell = new Cell()
                .add(new Paragraph(label).setBold().setFontColor(VIOLET_MED).setFontSize(13))
                .setBorder(null)
                .setPaddingBottom(8)
                .setPaddingTop(8)
                .setPaddingLeft(20);

        Cell valueCell = new Cell()
                .add(new Paragraph(value).setFontColor(new DeviceRgb(0x22, 0x00, 0x44)).setFontSize(14).setBold())
                .setBorder(null)
                .setPaddingBottom(8)
                .setPaddingTop(8);

        table.addCell(labelCell);
        table.addCell(valueCell);
    }

    private void addDivider(Document document) {
        Table divider = new Table(1)
                .setWidth(UnitValue.createPercentValue(85))
                .setHorizontalAlignment(HorizontalAlignment.CENTER);

        divider.addCell(new Cell()
                .setHeight(3)
                .setBackgroundColor(YELLOW_ACCENT)
                .setBorder(null));
        document.add(divider);
    }

    private void addSeatInfo(Document document, Seat seat, Long bookingId, Screening screening) {
        Table seatCard = new Table(UnitValue.createPercentArray(new float[]{1, 1, 1}))
                .setWidth(UnitValue.createPercentValue(85))
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setMarginTop(16)
                .setMarginBottom(16);

        addSeatCard(seatCard, "RAND",    String.valueOf(seat.getRowNumber()));
        addSeatCard(seatCard, "LOC",     String.valueOf(seat.getSeatNumber()));
        addSeatCard(seatCard, "BOOKING", "#" + bookingId);

        document.add(seatCard);
    }

    private void addSeatCard(Table table, String label, String value) {
        Cell cell = new Cell()
                .add(new Paragraph(label).setFontSize(11).setFontColor(VIOLET_LIGHT).setBold()
                        .setTextAlignment(TextAlignment.CENTER).setMarginBottom(2))
                .add(new Paragraph(value).setFontSize(22).setFontColor(YELLOW_ACCENT).setBold()
                        .setTextAlignment(TextAlignment.CENTER))
                .setBackgroundColor(VIOLET_DARK)
                .setBorder(null)
                .setPadding(14)
                .setMargin(4);
        table.addCell(cell);
    }

    private void addQrCode(Document document, Long bookingId, Seat seat) throws WriterException, IOException {
        String qrData = String.format(
                "CINEMA-TICKET|booking=%d|row=%d|seat=%d|token=%s",
                bookingId,
                seat.getRowNumber(),
                seat.getSeatNumber(),
                UUID.randomUUID().toString().substring(0, 8).toUpperCase()
        );

        QRCodeWriter qrWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrWriter.encode(qrData, BarcodeFormat.QR_CODE, 300, 300);

        ByteArrayOutputStream qrStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", qrStream);
        byte[] qrBytes = qrStream.toByteArray();

        Image qrImage = new Image(ImageDataFactory.create(qrBytes))
                .setWidth(250)
                .setHeight(250)
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setMarginTop(8);

        Paragraph qrLabel = new Paragraph("Scaneaza pentru verificare")
                .setFontSize(11)
                .setFontColor(VIOLET_MED)
                .setItalic()
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginTop(4);

        document.add(qrImage);
        document.add(qrLabel);
    }

    private void addFooter(Document document) {
        Paragraph footer = new Paragraph("Va multumim ca ati ales Cinema Tickets! • Biletul este valabil doar pentru data si ora indicate.")
                .setFontSize(10)
                .setFontColor(VIOLET_MED)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginTop(24);
        document.add(footer);
    }
}