package fa.training.quizsystem_fe.export_file.quizzes;

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

import fa.training.quizsystem_fe.dtos.Quiz;
import fa.training.quizsystem_fe.dtos.User;
import fa.training.quizsystem_fe.utils.AbstractExporter;

public class QuizPdfExporter extends AbstractExporter {

	public void export(List<Quiz> listQuizzes, HttpServletResponse response) throws IOException {

		super.setResponseHeader(response, "application/pdf", ".pdf", "quizzes_");

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();

		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);

		Paragraph paragraph = new Paragraph("List of Quiz", font);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(paragraph);

		PdfPTable table = new PdfPTable(8);
		table.setWidthPercentage(100f);
		table.setSpacingBefore(10);
//		table.setWidths(new float[] {1.2f, 3.5f, 3.0f, 3.0f, 3.0f, 1.7f});

		writeTableHeader(table);
		writeTableData(table, listQuizzes);

		document.add(table);

		document.close();
	}

	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);

		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);

		cell.setPhrase(new Phrase("Quiz ID", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Title", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Subjects", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Time Limit", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Education Level ", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Created Time", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Update Time", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Status", font));
		table.addCell(cell);
	}

	private void writeTableData(PdfPTable table, List<Quiz> listQuizzes) {
		for (Quiz quiz : listQuizzes) {
			Date date = Calendar.getInstance().getTime();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			table.addCell(String.valueOf(quiz.getId()));
			table.addCell(quiz.getTitle());
			table.addCell(quiz.getSubjects().toString());
			if (quiz.getTimeLimit() != null)
				table.addCell(quiz.getTimeLimit().toString());
			table.addCell(quiz.getEducationLevel());
			if (quiz.getCreatedTime() != null)
				table.addCell(dateFormat.format(quiz.getCreatedTime()));
			if (quiz.getUpdateTime() != null)
				table.addCell(dateFormat.format(quiz.getUpdateTime()));
	
			table.addCell(quiz.isStatus() ? "T" : "F");
		}
	}
}
