package fa.training.quizsystem_fe.export_file.quizzes;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import fa.training.quizsystem_fe.dtos.Quiz;
import fa.training.quizsystem_fe.dtos.User;
import fa.training.quizsystem_fe.utils.AbstractExporter;

public class QuizCsvExporter extends AbstractExporter {

	public void export(List<Quiz> listQuizzes, HttpServletResponse response) throws IOException {

		super.setResponseHeader(response, "text/csv", ".csv", "quizzes_");

		Writer writer = new OutputStreamWriter(response.getOutputStream(), "utf-8");
		writer.write('\uFEFF');

		ICsvBeanWriter csvWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE);

		String[] csvHeader = { "Quiz ID", "Title", "Subject", "Time Limit", "Education Level", "Created Time",
				"Update Time","Enabled" };
		String[] fieldMapping = { "id", "title", "subjects", "timeLimit", "educationLevel", "createdTime", "updateTime","status" };

		csvWriter.writeHeader(csvHeader);

		for (Quiz quiz : listQuizzes) {

			csvWriter.write(quiz, fieldMapping);
		}

		csvWriter.close();
	}
}