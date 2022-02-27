package fa.training.quizsystem_fe.export_file.records;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import fa.training.quizsystem_fe.dtos.Quiz;
import fa.training.quizsystem_fe.utils.AbstractExporter;
import fa.training.quizsystem_fe.dtos.Record;
public class RecordExcelExporter extends AbstractExporter {

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;

	public RecordExcelExporter() {
		workbook = new XSSFWorkbook();
	}


	private void writeHeaderLine() {
		sheet = workbook.createSheet("Record");
		XSSFRow row = sheet.createRow(0);

		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		cellStyle.setFont(font);

		createCell(row, 0, "#", cellStyle);
		createCell(row, 1, "Name", cellStyle);
		createCell(row, 2, "Email", cellStyle);
		createCell(row, 3, "Education Level", cellStyle);
		createCell(row, 4, "Start Time", cellStyle);
		createCell(row, 5, "Submit Time", cellStyle);
		createCell(row, 6, "Score", cellStyle);
	}

	private void createCell(XSSFRow row, int columnIndex, Object value, CellStyle style) {
		XSSFCell cell = row.createCell(columnIndex);
		sheet.autoSizeColumn(columnIndex);

		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Long) {
			cell.setCellValue((Long) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else if (value instanceof Date) {
			Date date = Calendar.getInstance().getTime();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String strDate = dateFormat.format(value);

			cell.setCellValue(strDate);
		}
		else {
			cell.setCellValue(value.toString());
		}

		cell.setCellStyle(style);
	}

	public void export(List<Record> listRecords, HttpServletResponse response) throws IOException {
		super.setResponseHeader(response, "application/octet-stream", ".xlsx", "records_");

		writeHeaderLine();
		writeDataLines(listRecords);

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();

	}
	private void writeDataLines(List<Record> listRecords) {
		int rowIndex = 1;

		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		cellStyle.setFont(font);

		for (Record record : listRecords) {
			XSSFRow row = sheet.createRow(rowIndex++);
			int columnIndex = 0;

			createCell(row, columnIndex++, record.getId(), cellStyle);
			createCell(row, columnIndex++, record.getUser().getName(), cellStyle);
			createCell(row, columnIndex++, record.getUser().getEmail(), cellStyle);
			createCell(row, columnIndex++, record.getUser().getEducationLevel(), cellStyle);
			createCell(row, columnIndex++, record.getStartTime(), cellStyle);
			createCell(row, columnIndex++, record.getSubmitTime(), cellStyle);
			createCell(row, columnIndex++, record.getScore(), cellStyle);
		}
	}
}
