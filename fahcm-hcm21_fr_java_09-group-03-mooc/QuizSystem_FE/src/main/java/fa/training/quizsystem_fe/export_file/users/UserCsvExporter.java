package fa.training.quizsystem_fe.export_file.users;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import fa.training.quizsystem_fe.dtos.User;
import fa.training.quizsystem_fe.utils.AbstractExporter;

public class UserCsvExporter extends AbstractExporter {

	public void export(List<User> listUsers, HttpServletResponse response) throws IOException {

		super.setResponseHeader(response, "text/csv", ".csv", "users_");

		Writer writer = new OutputStreamWriter(response.getOutputStream(), "utf-8");
		writer.write('\uFEFF');

		ICsvBeanWriter csvWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE);

		String[] csvHeader = { "User ID", "E-mail", "Name", "Birth Date", "Education Level", "Created Time",
				"Update Time", "Avatar", "Roles", "Enabled" };
		String[] fieldMapping = { "id", "email", "name", "birthdate", "educationLevel", "createdTime", "updateTime",
				"avatar", "role", "status" };

		csvWriter.writeHeader(csvHeader);

		for (User user : listUsers) {

			csvWriter.write(user, fieldMapping);
		}

		csvWriter.close();
	}
}
