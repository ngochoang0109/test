package fa.training.quizsystem_fe.export_file.records;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.lowagie.text.Phrase;

import fa.training.quizsystem_fe.dtos.User;
import fa.training.quizsystem_fe.utils.AbstractExporter;
import fa.training.quizsystem_fe.dtos.Record;
public class RecordCsvExporter extends AbstractExporter {

	public void export(List<Record> listRecords, HttpServletResponse response) throws IOException {

		super.setResponseHeader(response, "text/csv", ".csv", "records_");

		Writer writer = new OutputStreamWriter(response.getOutputStream(), "utf-8");
		writer.write('\uFEFF');

		ICsvBeanWriter csvWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE);


		String[] csvHeader = { "#","Name", "Email", "Education Level", "Start Time ",
				"Submit Time", "Score" };
		String[] fieldMapping = { "id", "name", "email","educationLevel", "startTime", "submitTime", "score" };

		csvWriter.writeHeader(csvHeader);

		for (Record record : listRecords) {
			csvWriter.write(record, fieldMapping);
		}

		csvWriter.close();
	}
}
