package fa.training.quizsystem_fe.export_file.records;

import java.awt.Color;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import fa.training.quizsystem_fe.dtos.User;
import fa.training.quizsystem_fe.utils.AbstractExporter;
import fa.training.quizsystem_fe.dtos.Record;

public class RecordPdfExporter extends AbstractExporter {

	public void export(List<Record> listRecords, HttpServletResponse response) throws IOException {

		super.setResponseHeader(response, "application/pdf", ".pdf", "records_");

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();

		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);

		Paragraph paragraph = new Paragraph("List of Record", font);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(paragraph);

		PdfPTable table = new PdfPTable(7);
		table.setWidthPercentage(100f);
		table.setSpacingBefore(10);
//		table.setWidths(new float[] {1.2f, 3.5f, 3.0f, 3.0f, 3.0f, 1.7f});

		writeTableHeader(table);
		writeTableData(table, listRecords);

		document.add(table);

		document.close();
	}

	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);

		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);

		cell.setPhrase(new Phrase("#", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Name", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Email", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Education Level", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Start Time ", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Submit Time", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Score", font));
		table.addCell(cell);
	}

	private void writeTableData(PdfPTable table, List<Record> listRecords) {
		for (Record record : listRecords) {
			Date date = Calendar.getInstance().getTime();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			table.addCell(String.valueOf(record.getId()));
			table.addCell(record.getUser().getName());
			table.addCell(record.getUser().getEmail());
			table.addCell(record.getUser().getEducationLevel());
		
			if (record.getStartTime() != null)
				table.addCell(dateFormat.format(record.getStartTime()));
			else
				table.addCell("");
			if (record.getSubmitTime() != null)
				table.addCell(dateFormat.format(record.getSubmitTime()));
			else
				table.addCell("");
			
			table.addCell(record.getScore().toString());
		}
	}
}
